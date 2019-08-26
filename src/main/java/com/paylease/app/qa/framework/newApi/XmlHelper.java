package com.paylease.app.qa.framework.newApi;

import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil.HttpMethods;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public final class XmlHelper {

  /**
   * Marshal request into XML String before sending request.
   *
   * @param request AAPI or GAPI request
   * @return XML string
   */
  public static StringEntity marshalRequestObjectToXmlString(PageBase request) {
    StringEntity requestEntity = null;

    try {
      StringWriter writer = new StringWriter();
      JAXBContext jaxbContext = JAXBContext.newInstance(request.getClass());
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      jaxbMarshaller.marshal(request, writer);
      requestEntity = new StringEntity(writer.getBuffer().toString());

    } catch (Exception e) {
      e.printStackTrace();
    }

    return requestEntity;
  }

  /**
   * Send XML request.
   *
   * @param restClientUtil RestClientUtil
   * @param requestEntity XML request
   * @param url URL that request is sent to
   * @return Http Response
   */
  public static HttpResponse sendGapiRequest(RestClientUtil restClientUtil, StringEntity requestEntity,
      String url) {

    String finalUrl = addPhpDebugParamIfDebugModeIsRunning(url);

    HttpResponse httpResponse = null;

    try {
      //Build the UrlEncodedFormEntity parameter
      List<NameValuePair> urlParameters = new ArrayList<>();
      urlParameters
          .add(new BasicNameValuePair("XML", EntityUtils.toString(requestEntity)));
      UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(urlParameters);

      //Use the RestClient to send the xml message
      HttpClient httpClient = restClientUtil.getHttpClientInstance();
      HttpRequestBase httpPOSTRequest = restClientUtil
          .getHttpRequestInstance(HttpMethods.POST, finalUrl);
      httpResponse = restClientUtil
          .sendRequest(HttpMethods.POST, httpClient, httpPOSTRequest, urlEncodedFormEntity);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return httpResponse;
  }

  /**
   * Send XML request.
   *
   * @param restClientUtil RestClientUtil
   * @param requestEntity XML request
   * @param url URL that request is sent to
   * @return Http Response
   */
  public static HttpResponse sendAapiRequest(RestClientUtil restClientUtil,
      StringEntity requestEntity,
      String url) {

    String finalUrl = addPhpDebugParamIfDebugModeIsRunning(url);

    HttpResponse httpResponse = null;

    try {
      //Use the RestClient to send the xml message
      HttpClient httpClient = restClientUtil.getHttpClientInstance();
      HttpRequestBase httpPOSTRequest = restClientUtil
          .getHttpRequestInstance(HttpMethods.POST, finalUrl);
      httpResponse = restClientUtil
          .sendRequest(HttpMethods.POST, httpClient, httpPOSTRequest, requestEntity);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return httpResponse;
  }

  /**
   * This function serves to add the PHP debug param to the url if IntelliJ
   * is running in debug mode.
   *
   * @param url Initial url
   * @return String the url with the debug param if running debug mode
   */
  private static String addPhpDebugParamIfDebugModeIsRunning(String url) {

    try {
      if (System.getProperty("intellij.debug.agent") != null
          && System.getProperty("intellij.debug.agent").equals("true")) {
        url += (((url.contains("?")) ? "&" : "?") + PageBase.PHP_DEBUG_PARAM);
      }
    } catch (Exception exception) {
      System.out.println("Your version of IntelliJ does not support PHP Debugging\n"
          + "The minimum version that supports PHP Debugging is 2018.2.0");
    }

    return url;
  }
}
