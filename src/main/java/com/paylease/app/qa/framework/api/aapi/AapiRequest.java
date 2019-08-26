package com.paylease.app.qa.framework.api.aapi;

import static com.paylease.app.qa.framework.pages.PageBase.BASE_URL;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Request;
import javax.xml.parsers.ParserConfigurationException;

public class AapiRequest extends Request {

  private static final String URL = BASE_URL + "api/{endpoint}.php";

  private static final String PAYLEASE_REQUEST = "PayLeaseRequest";
  private static final String CREDENTIALS = "Credentials";
  private static final String USER_ID = "UserID";
  private static final String PASSWORD = "Password";
  private static final String API_KEY = "ApiKey";

  private static final String MODE = "Mode";
  private static final String ACTION = "Action";

  public static final String ASSOCIA = "associa";
  public static final String CINC = "cinc";
  public static final String COLLIER = "collier";
  public static final String FISERV = "fiserv";
  public static final String ONSITE = "onsite";
  public static final String TOPS = "tops";
  public static final String OAKWOOD = "oakwood";
  public static final String RESMAN = "resman";

  private static final String ASSOCIA_API_KEY = "iedahy2ohcie7nieWoo1";
  private static final String CINC_API_KEY = "hovee3uv0ooShoi1Such";
  private static final String COLLIER_API_KEY = "rohn3shaiquaesh1xoSu";
  private static final String FISERV_API_KEY = "YXqWB4MfTZPNwgb4f7Zh";
  private static final String ONSITE_API_KEY = "qua1aiPhul2chohz5aer";
  private static final String TOPS_API_KEY = "EinaGh1oe4eihu1gohci";
  private static final String OAKWOOD_API_KEY = "IeteoCiojao2Jahshahr";
  private static final String RESMAN_API_KEY = "yu0qqnkwq33FBEUBEDrA";

  private String url;

  /**
   * Generate base AAPI request.
   *
   * @param credentials credentials to create a valid request
   * @param mode request type
   * @param endpoint endpoint where request needs to be sent
   */
  public AapiRequest(Credentials credentials, String mode, String endpoint)
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

    request = XMLBuilder.create(PAYLEASE_REQUEST)
        .e(CREDENTIALS);
    if (credentials.getGatewayId() != null) {
      request = request.e(USER_ID).t(credentials.getGatewayId()).up();
    }

    if (credentials.getPassword() != null) {
      request = request.e(PASSWORD).t(credentials.getPassword()).up();
    }

    if (credentials.getApiKey() != null) {
      request = request.e(API_KEY).t(credentials.getApiKey()).up();
    }

    request = request.up()
        .e(MODE).t(mode).up()
        .up();
  }

  /**
   * Send the AAPI request.
   *
   * @return AapiResponse from AAPI
   * @throws Exception in case of trouble with the request
   */
  public AapiResponse sendRequest() throws Exception {

    Logger.debug("Making Request to " + url);

    try {
      String xmlString = getRequestString();

      String response = submitRequest(url, xmlString);

      return new AapiResponse(XMLBuilder.parse(response));
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Get the API key for provided endpoint.
   *
   * @param endpoint endpoint for which apiKey is needed
   * @return apiKey
   */
  public static String getApiKey(String endpoint) {

    String apiKey = "";

    switch (endpoint) {
      case ASSOCIA:
        apiKey = ASSOCIA_API_KEY;
        break;
      case CINC:
        apiKey = CINC_API_KEY;
        break;
      case COLLIER:
        apiKey = COLLIER_API_KEY;
        break;
      case FISERV:
        apiKey = FISERV_API_KEY;
        break;
      case ONSITE:
        apiKey = ONSITE_API_KEY;
        break;
      case TOPS:
        apiKey = TOPS_API_KEY;
        break;
      case OAKWOOD:
        apiKey = OAKWOOD_API_KEY;
        break;
      case RESMAN:
        apiKey = RESMAN_API_KEY;
        break;
      default:
        // not supported
    }

    return apiKey;
  }
}
