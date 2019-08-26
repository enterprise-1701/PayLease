package com.paylease.app.qa.framework.api.aapi;

import static com.paylease.app.qa.framework.pages.PageBase.BASE_URL;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import javax.xml.parsers.ParserConfigurationException;

public class RdcRequest extends Request {

  private static final String URL = BASE_URL + "api/{endpoint}.php";

  private static final String PAYLEASE_RDC_REQUEST = "PayLeaseRDCRequest";
  private static final String CREDENTIALS = "Credentials";
  private static final String GATEWAY_ID = "GatewayId";
  private static final String USERNAME = "Username";
  private static final String MODE = "Mode";

  private static final String ACTION = "Action";

  private String url;

  /**
   * RDC Request class.
   *
   * @param credentials credentials for submitting request
   * @param mode Mode to use in request
   * @param endpoint endpoint to send to
   * @throws ParserConfigurationException in case of XML parsing error
   */
  public RdcRequest(Credentials credentials, String mode, String endpoint)
      throws ParserConfigurationException {
    super(credentials, mode);
    this.url = URL.replace("{endpoint}", endpoint);

    generateBaseRequest();
  }

  @Override
  protected String getActionRoot() {
    return ACTION;
  }

  private void generateBaseRequest()
      throws ParserConfigurationException {

    request = XMLBuilder.create(PAYLEASE_RDC_REQUEST)
        .e(CREDENTIALS);
    if (credentials.getGatewayId() != null) {
      request = request.e(GATEWAY_ID).t(credentials.getGatewayId()).up();
    }
    if (credentials.getUsername() != null) {
      request = request.e(USERNAME).t(credentials.getUsername()).up();
    }

    request = request.up()
        .e(MODE).t(mode).up()
        .up();
  }

  @Override
  public Response sendRequest() throws Exception {

    Logger.debug("Making Request to " + url);

    try {
      String xmlString = getRequestString();
      String toBeHashed = xmlString + credentials.getPassword();
      String hash = Hashing.sha1().hashString(toBeHashed, Charsets.UTF_8).toString();
      Logger.debug("RDC Hash: " + hash);
      xmlString = hash + xmlString;

      String response = submitRequest(url, xmlString);

      // RDC response is signed with a 40-character hash that precedes the XML content
      String responseHash = response.substring(0, 40);
      Logger.debug("RDC Response Hash: " + responseHash);
      String responseXml = response.substring(40);

      return new RdcResponse(XMLBuilder.parse(responseXml));
    } catch (Exception e) {
      throw e;
    }
  }
}
