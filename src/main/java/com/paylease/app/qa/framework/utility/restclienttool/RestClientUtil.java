package com.paylease.app.qa.framework.utility.restclienttool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paylease.app.qa.framework.utility.sshtool.SslTrustModifier;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Rest Client Utility that supports all common HTTP Methods (GET, POST, PUT, and DELETE). This
 * client also supports parsing of JSON and XMl responses.
 *
 * @author Jeffrey Walker
 */
public class RestClientUtil {

  private String responseString;
  private Object responseObject;

  public enum HttpMethods {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private String value;

    HttpMethods(String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }

  public enum ResponseFormat {
    JSON("JSON"), XML("XML");

    private String value;

    ResponseFormat(String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }

  public HttpClient getHttpClientInstance() {
    return HttpClientBuilder.create().build();
  }

  /**
   * Get the http request instance.
   *
   * @param httpMethod http method
   * @param url url
   * @return http request
   */
  public HttpRequestBase getHttpRequestInstance(HttpMethods httpMethod, String url) {
    HttpRequestBase httpRequest = null;

    switch (httpMethod.value) {
      case "GET":
        httpRequest = new HttpGet(url);

        break;

      case "POST":
        httpRequest = new HttpPost(url);

        break;

      case "PUT":
        httpRequest = new HttpPut(url);

        break;

      case "DELETE":
        httpRequest = new HttpDelete(url);

        break;

      default:
        break;
    }

    return httpRequest;
  }

  /**
   * Set headers for request.
   *
   * @param httpRequest http request
   * @param headersMap headers map
   * @return http request
   */
  public HttpRequestBase setHeaders(HttpRequestBase httpRequest,
      HashMap<String, String> headersMap) {
    for (String headerName : headersMap.keySet()) {
      String headerValue = headersMap.get(headerName);
      httpRequest.setHeader(headerName, headerValue);
    }

    return httpRequest;
  }

  /**
   * Send request.
   *
   * @param httpMethod httpMethod
   * @param httpClient httpClient
   * @param httpRequest httpRequest
   * @param entity entity
   * @return response
   */
  public HttpResponse sendRequest(HttpMethods httpMethod, HttpClient httpClient,
      HttpRequestBase httpRequest, HttpEntity entity) {
    HttpResponse response = null;

    try {

      switch (httpMethod.value) {
        case "GET":
          response = httpClient.execute(httpRequest);

          break;

        case "POST":
          HttpPost httpPost = (HttpPost) httpRequest;
          httpPost.setEntity(entity);
          response = SslTrustModifier.relaxRequestChecking().execute(httpRequest);

          break;

        case "PUT":
          HttpPut httpPut = (HttpPut) httpRequest;
          httpPut.setEntity(entity);
          response = httpClient.execute(httpRequest);

          break;

        case "DELETE":
          response = httpClient.execute(httpRequest);

          break;

        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;
  }

  /**
   * Parse response body.
   *
   * @param httpResponse httpResponse
   * @param responseFormat responseFormat
   */
  public void parseResponseBody(HttpResponse httpResponse, ResponseFormat responseFormat) {
    try {
      String responseString;
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      switch (responseFormat.value) {
        case "JSON":

          JSONParser parser = new JSONParser();
          responseString = EntityUtils.toString(httpResponse.getEntity());

          Object responseObj = parser.parse(responseString);
          setResponseBodyObject(responseObj);
          setResponseBodyString(gson.toJson(responseObj));

          break;

        case "XML":

          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilder builder;
          InputSource is;

          builder = factory.newDocumentBuilder();
          is = new InputSource(new StringReader(EntityUtils.toString(httpResponse.getEntity())));

          Document document = builder.parse(is);
          setResponseBodyObject(document);

          Transformer tf = TransformerFactory.newInstance().newTransformer();
          tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
          tf.setOutputProperty(OutputKeys.INDENT, "yes");

          Writer out = new StringWriter();
          tf.transform(new DOMSource(document), new StreamResult(out));
          setResponseBodyString(out.toString());

          break;

        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setResponseBodyString(String responseString) {
    this.responseString = responseString;
  }

  public String getResponseString() {
    return this.responseString;
  }

  private void setResponseBodyObject(Object responseObject) {
    this.responseObject = responseObject;
  }

  public Object getResponseBodyObject() {
    return this.responseObject;
  }

  /**
   * Get response headers map.
   *
   * @param httpResponse httpResponse
   * @return responseHeaderMap
   */
  public HashMap<String, String> getResponseHeadersMap(HttpResponse httpResponse) {
    HashMap<String, String> responseHeaderMap = new HashMap<String, String>();

    for (Header header : httpResponse.getAllHeaders()) {
      responseHeaderMap.put(header.getName(), header.getValue());
    }

    return responseHeaderMap;
  }

  public int getResponseStatusCode(HttpResponse httpResponse) {
    return httpResponse.getStatusLine().getStatusCode();
  }

  public String getResponseReasonPhrase(HttpResponse httpResponse) {
    return httpResponse.getStatusLine().getReasonPhrase();
  }
}
