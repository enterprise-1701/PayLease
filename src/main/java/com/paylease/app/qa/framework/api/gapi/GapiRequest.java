package com.paylease.app.qa.framework.api.gapi;

import static com.paylease.app.qa.framework.pages.PageBase.BASE_URL;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Request;
import java.net.URLEncoder;
import javax.xml.parsers.ParserConfigurationException;

public class GapiRequest extends Request {

  private static final String URL = BASE_URL + "gapi/request.php";

  private static final String URL_PARAMETERS = "XML";

  private static final String PAYLEASE_GATEWAY = "PayLeaseGatewayRequest";
  private static final String CREDENTIALS = "Credentials";
  private static final String GATEWAY_ID = "GatewayId";
  private static final String USERNAME = "Username";
  private static final String PASSWORD = "Password";
  private static final String API_KEY = "ApiKey";

  private static final String MODE = "Mode";
  private static final String TRANSACTIONS = "Transactions";
  private static final String TRANSACTION = "Transaction";

  public GapiRequest(Credentials credentials) throws ParserConfigurationException {
    this(credentials, "Test");
  }

  /**
   * Generate base GAPI request.
   *
   * @param credentials credentials to create a valid request
   * @param mode request type
   */
  public GapiRequest(Credentials credentials, String mode)
      throws ParserConfigurationException {
    super(credentials, mode);

    generateBaseRequest();
  }

  @Override
  protected String getActionRoot() {
    return TRANSACTION;
  }

  private void generateBaseRequest()
      throws ParserConfigurationException {

    request = XMLBuilder.create(PAYLEASE_GATEWAY)
        .e(CREDENTIALS)
          .e(GATEWAY_ID).t(credentials.getGatewayId()).up()
          .e(USERNAME).t(credentials.getUsername()).up()
          .e(PASSWORD).t(credentials.getPassword()).up();

    if (credentials.getApiKey() != null) {
      request = request.e(API_KEY).t(credentials.getApiKey()).up();
    }

    request = request.up() // back to root
        .e(MODE).t(mode).up()
          .up() // back to root
        .e(TRANSACTIONS);
  }

  /**
   * Send the GAPI request.
   *
   * @return GapiResponse from GAPI
   * @throws Exception thrown in case of trouble
   */
  @Override
  public GapiResponse sendRequest() throws Exception {
    try {
      String xmlString = getRequestString();

      xmlString = URLEncoder.encode(xmlString, "UTF-8");
      String content = URL_PARAMETERS + "=" + xmlString;

      String response = submitRequest(URL, content);

      return new GapiResponse(XMLBuilder.parse(response));
    } catch (Exception e) {
      throw e;
    }
  }
}
