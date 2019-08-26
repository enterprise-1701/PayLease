package com.paylease.app.qa.framework.utility.demos;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil.HttpMethods;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil.ResponseFormat;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Demo class for RestClientUtil.java
 *
 * @author Jeffrey Walker
 */
@SuppressWarnings("unused")
public class RestClientDemo {

  private static String basicAuthUserName = "";
  private static String basicAuthPassword = "";
  private static String getURL = "https://reqres.in/api/users?page=2";

  //REST URLs that return back XML
  private static String postURL = "https://reqres.in/api/users";
  private static String putURL = "https://reqres.in/api/users/2";
  private static String deleteURL = "https://reqres.in/api/users/2";

  //REST URLs that return back XML
  private static String getURL2 = "https://samples.openweathermap.org/data/2.5/weather?q=London&mode=xml&appid=b6907d289e10d714a6e88b30761fae22";
  private static String postURL2 = "";
  private static String putURL2 = "";

  public static void main(String[] args) {
    RestClientUtil restClientUtil = new RestClientUtil();
    HttpClient httpClient = restClientUtil.getHttpClientInstance();

    Logger.info("************************************");
    Logger.info("GET REQUEST Example");
    Logger.info("************************************");

    HttpRequestBase httpGETRequest = restClientUtil.getHttpRequestInstance(HttpMethods.GET, getURL);

    //Set Request Headers
    HashMap<String, String> headersMap = new HashMap<String, String>();
    headersMap.put(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encodeBase64(
        (basicAuthUserName + ":" + basicAuthPassword).getBytes(StandardCharsets.ISO_8859_1))));

    httpGETRequest = restClientUtil.setHeaders(httpGETRequest, headersMap);

    //Send the request
    HttpResponse httpResponse = restClientUtil
        .sendRequest(HttpMethods.GET, httpClient, httpGETRequest, null);

    //Process the response - HTTP Response Code
    Logger.info("\n==================================");
    Logger.info("Response Status Line");
    Logger.info("==================================\n");
    Logger.info("Response Status Code: " + restClientUtil.getResponseStatusCode(httpResponse));
    Logger.info("Response Reason Phrase: " + restClientUtil.getResponseReasonPhrase(httpResponse));

    //Process the response - Response Body Object
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.JSON);
    JSONObject responseJsonObject = (JSONObject) restClientUtil.getResponseBodyObject();
    Logger.info("\n==================================");
    Logger.info("Response Body Values (Retrieved via the JSON Object)");
    Logger.info("==================================\n");
    Logger.info("Per_Page = " + responseJsonObject.get("per_page").toString());
    Logger.info("Total = " + responseJsonObject.get("total").toString());
    Logger.info("Page = " + responseJsonObject.get("page").toString());
    Logger.info("Total Pages = " + responseJsonObject.get("total_pages").toString());

    JSONArray jsonArray = (JSONArray) responseJsonObject.get("data");
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = (JSONObject) jsonArray.get(i);
      Logger.info("data[" + i + "]= " + "first_name : " + jsonObject.get("first_name")
          + ", last_name : " + jsonObject.get("last_name") + ", id : " + jsonObject.get("id")
          + ", avatar : " + jsonObject.get("avatar"));
    }

    //Process the response - Response Body String
    Logger.info("\n==================================");
    Logger.info("Response Body (Retrieved as a String)");
    Logger.info("==================================\n");

    String responseString = restClientUtil.getResponseString();
    Logger.info(responseString);

    //Process the response headers
    Logger.info("\n==================================");
    Logger.info("Response Headers");
    Logger.info("==================================\n");

    HashMap<String, String> responseHeadersMap = restClientUtil.getResponseHeadersMap(httpResponse);
    for (String responseHeaderName : responseHeadersMap.keySet()) {
      Logger.info(responseHeaderName + " : " + responseHeadersMap.get(responseHeaderName));
    }

    Logger.info("\n************************************");
    Logger.info("POST REQUEST Example");
    Logger.info("************************************");

    HttpRequestBase httpPOSTRequest = restClientUtil
        .getHttpRequestInstance(HttpMethods.POST, postURL);

    //Send the request
    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    urlParameters.add(new BasicNameValuePair("name", "morpheus"));
    urlParameters.add(new BasicNameValuePair("job", "leader"));
    try {
      httpResponse = restClientUtil.sendRequest(HttpMethods.POST, httpClient, httpPOSTRequest,
          new UrlEncodedFormEntity(urlParameters));

    } catch (UnsupportedEncodingException e) {
      Logger.error(e.toString());
    }

    //Process the response - HTTP Response Code
    Logger.info("\n==================================");
    Logger.info("Response Status Line");
    Logger.info("==================================");
    Logger.info("Response Status Code: " + restClientUtil.getResponseStatusCode(httpResponse));
    Logger.info("Response Reason Phrase: " + restClientUtil.getResponseReasonPhrase(httpResponse));

    //Process the response - Response Body Object
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.JSON);
    responseJsonObject = (JSONObject) restClientUtil.getResponseBodyObject();
    Logger.info("\n==================================");
    Logger.info("Response Body Values (Retrieved via the JSON Object)");
    Logger.info("==================================\n");
    Logger.info("createdAt = " + responseJsonObject.get("createdAt").toString());
    Logger.info("name = " + responseJsonObject.get("name").toString());
    Logger.info("job = " + responseJsonObject.get("job").toString());
    Logger.info("id = " + responseJsonObject.get("id").toString());

    //Process the response - Response Body String
    Logger.info("\n==================================");
    Logger.info("Response Body (Retrieved as a String)");
    Logger.info("==================================\n");
    responseString = restClientUtil.getResponseString();
    Logger.info(responseString);

    //Process the response headers
    Logger.info("\n==================================");
    Logger.info("Response Headers");
    Logger.info("==================================\n");

    responseHeadersMap = restClientUtil.getResponseHeadersMap(httpResponse);
    for (String responseHeaderName : responseHeadersMap.keySet()) {
      Logger.info(responseHeaderName + " : " + responseHeadersMap.get(responseHeaderName));
    }

    Logger.info("\n************************************");
    Logger.info("PUT REQUEST Example");
    Logger.info("************************************");

    HttpRequestBase httpPUTRequest = restClientUtil.getHttpRequestInstance(HttpMethods.PUT, putURL);

    //Send the request
    urlParameters = new ArrayList<NameValuePair>();
    urlParameters.add(new BasicNameValuePair("name", "morpheus"));
    urlParameters.add(new BasicNameValuePair("job", "zion resident"));
    try {
      httpResponse = restClientUtil.sendRequest(HttpMethods.PUT, httpClient, httpPUTRequest,
          new UrlEncodedFormEntity(urlParameters));

    } catch (UnsupportedEncodingException e) {
      Logger.error(e.toString());
    }

    //Process the response - HTTP Response Code
    Logger.info("\n==================================");
    Logger.info("Response Status Line");
    Logger.info("==================================\n");
    Logger.info("Response Status Code: " + restClientUtil.getResponseStatusCode(httpResponse));
    Logger.info("Response Reason Phrase: " + restClientUtil.getResponseReasonPhrase(httpResponse));

    //Process the response - Response Body Object
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.JSON);
    responseJsonObject = (JSONObject) restClientUtil.getResponseBodyObject();
    Logger.info("\n==================================");
    Logger.info("Response Body Values (Retrieved via the JSON Object)");
    Logger.info("==================================\n");
    Logger.info("updatedAt = " + responseJsonObject.get("updatedAt").toString());
    Logger.info("name = " + responseJsonObject.get("name").toString());
    Logger.info("job = " + responseJsonObject.get("job").toString());

    //Process the response - Response Body String
    Logger.info("\n==================================");
    Logger.info("Response Body (Retrieved as a String)");
    Logger.info("==================================\n");
    responseString = restClientUtil.getResponseString();
    Logger.info(responseString);

    //Process the response headers
    Logger.info("\n==================================");
    Logger.info("Response Headers");
    Logger.info("==================================\n");

    responseHeadersMap = restClientUtil.getResponseHeadersMap(httpResponse);
    for (String responseHeaderName : responseHeadersMap.keySet()) {
      Logger.info(responseHeaderName + " : " + responseHeadersMap.get(responseHeaderName));
    }

    Logger.info("\n************************************");
    Logger.info("DELETE REQUEST Example");
    Logger.info("************************************");

    HttpRequestBase httpDELETERequest = restClientUtil
        .getHttpRequestInstance(HttpMethods.DELETE, deleteURL);

    //Send the request
    httpResponse = restClientUtil
        .sendRequest(HttpMethods.DELETE, httpClient, httpDELETERequest, null);

    //Process the response - HTTP Response Code
    Logger.info("\n==================================");
    Logger.info("Response Status Line");
    Logger.info("==================================\n");
    Logger.info("Response Status Code: " + restClientUtil.getResponseStatusCode(httpResponse));
    Logger.info("Response Reason Phrase: " + restClientUtil.getResponseReasonPhrase(httpResponse));

    //Process the response headers
    Logger.info("\n==================================");
    Logger.info("Response Headers");
    Logger.info("==================================\n");

    responseHeadersMap = restClientUtil.getResponseHeadersMap(httpResponse);
    for (String responseHeaderName : responseHeadersMap.keySet()) {
      Logger.info(responseHeaderName + " : " + responseHeadersMap.get(responseHeaderName));
    }

    Logger.info("\n************************************");
    Logger.info("CHAINING REST CALLS Example");
    Logger.info("************************************");

    Logger.info("\n***Making REST Call #1 (POST call to create Batman)...");

    httpPOSTRequest = restClientUtil.getHttpRequestInstance(HttpMethods.POST, postURL);

    //Send the request
    urlParameters = new ArrayList<NameValuePair>();
    urlParameters.add(new BasicNameValuePair("name", "batman"));
    urlParameters.add(new BasicNameValuePair("job", "super hero"));
    try {
      httpResponse = restClientUtil.sendRequest(HttpMethods.POST, httpClient, httpPOSTRequest,
          new UrlEncodedFormEntity(urlParameters));

    } catch (UnsupportedEncodingException e) {
      Logger.error(e.toString());
    }

    //Process the response - Response Body Object
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.JSON);
    responseJsonObject = (JSONObject) restClientUtil.getResponseBodyObject();
    Logger.info("\n==================================");
    Logger.info("Response Body Values (Retrieved via the JSON Object)");
    Logger.info("==================================\n");
    String name = responseJsonObject.get("name").toString();
    String job = responseJsonObject.get("job").toString();
    Logger.info("name = " + name);
    Logger.info("job = " + job);

    Logger.info("\n***Making REST Call #2 (PUT call to change Batman's job)...");

    httpPUTRequest = restClientUtil.getHttpRequestInstance(HttpMethods.PUT, putURL);

    //Send the request
    urlParameters = new ArrayList<NameValuePair>();
    urlParameters.add(new BasicNameValuePair("name", name));
    urlParameters.add(new BasicNameValuePair("job", "dark knight"));
    try {
      httpResponse = restClientUtil.sendRequest(HttpMethods.PUT, httpClient, httpPUTRequest,
          new UrlEncodedFormEntity(urlParameters));

    } catch (UnsupportedEncodingException e) {
      Logger.error(e.toString());
    }

    //Process the response - Response Body Object
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.JSON);
    responseJsonObject = (JSONObject) restClientUtil.getResponseBodyObject();
    Logger.info("\n==================================");
    Logger.info("Response Body Values (Retrieved via the JSON Object)");
    Logger.info("==================================\n");
    Logger.info("name = " + responseJsonObject.get("name").toString());
    Logger.info("job = " + responseJsonObject.get("job").toString());

    Logger.info("************************************");
    Logger.info("GET REQUEST Example (XML)");
    Logger.info("************************************");

    httpGETRequest = restClientUtil.getHttpRequestInstance(HttpMethods.GET, getURL2);

    //Send the request
    httpResponse = restClientUtil.sendRequest(HttpMethods.GET, httpClient, httpGETRequest, null);

    //Process the response - HTTP Response Code
    Logger.info("\n==================================");
    Logger.info("Response Status Line");
    Logger.info("==================================\n");
    Logger.info("Response Status Code: " + restClientUtil.getResponseStatusCode(httpResponse));
    Logger.info("Response Reason Phrase: " + restClientUtil.getResponseReasonPhrase(httpResponse));

    //Process the response - Response Body Object
    Logger.info("\n==================================");
    Logger.info("Response Body Values (Retrieved via the XML Object)");
    Logger.info("==================================\n");
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.XML);
    Document responseXmlDocumentObject = (Document) restClientUtil.getResponseBodyObject();
    NodeList currentList = responseXmlDocumentObject.getElementsByTagName("current");
    for (int i = 0; i < currentList.getLength(); i++) {
      Node node = currentList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element elementCurrent = (Element) node;

        //process the "city" element
        Element elementCity = (Element) (elementCurrent.getElementsByTagName("city").item(0));
        Logger.info("city: [attributes] " + "id = " + elementCity.getAttribute("id") + ", name = "
            + elementCity.getAttribute("name"));

        Element elementCoord = (Element) (elementCity.getElementsByTagName("coord").item(0));
        Logger.info("	coord: [attributes] " + "latitude = " + elementCoord.getAttribute("lat")
            + ", longitude = " + elementCoord.getAttribute("lon"));

        Element elementCountry = (Element) (elementCity.getElementsByTagName("country").item(0));
        Logger.info("	country: [value] = " + elementCountry.getTextContent());

        Element elementSun = (Element) (elementCity.getElementsByTagName("sun").item(0));
        Logger.info(
            "	coord: [attributes] " + "rise = " + elementSun.getAttribute("rise") + ", set = "
                + elementSun.getAttribute("set"));

        //process the "pressure" element
        Element elementPressure = (Element) (elementCurrent.getElementsByTagName("pressure")
            .item(0));
        Logger.info("pressure: [attributes] " + "unit = " + elementPressure.getAttribute("unit")
            + ", value = " + elementCity.getAttribute("value"));

        //process the "wind" element
        Element elementWind = (Element) (elementCurrent.getElementsByTagName("wind").item(0));
        Element elementSpeed = (Element) (elementWind.getElementsByTagName("speed").item(0));
        Logger.info(
            "speed: [attributes] " + "name = " + elementSpeed.getAttribute("name") + ", value = "
                + elementSpeed.getAttribute("value"));


      }
    }

    //Process the response - Response Body String
    Logger.info("\n==================================");
    Logger.info("Response Body (Retrieved as a String)");
    Logger.info("==================================\n");

    responseString = restClientUtil.getResponseString();
    Logger.info(responseString);

    //Process the response headers
    Logger.info("\n==================================");
    Logger.info("Response Headers");
    Logger.info("==================================\n");

    responseHeadersMap = restClientUtil.getResponseHeadersMap(httpResponse);
    for (String responseHeaderName : responseHeadersMap.keySet()) {
      Logger.info(responseHeaderName + " : " + responseHeadersMap.get(responseHeaderName));
    }

    Logger.info("\n************************************");
    Logger.info("POST REQUEST Example (XML)");
    Logger.info("************************************\n");

    Logger.info("Commented code shows how to send a XML POST Request");

//		httpPOSTRequest = restClientUtil.getHttpRequestInstance(HttpMethods.POST, postURL2);
//		
//		String xml = "<xml>xxxx</xml>";
//        
//      try 
//		{
//			httpResponse = restClientUtil.sendGapiRequest(HttpMethods.POST, httpClient, httpPOSTRequest, new ByteArrayEntity(xml.getBytes("UTF-8")));
//			
//		} 
//		catch (UnsupportedEncodingException e) 
//		{
//			Logger.error(e.toString());
//		}

    Logger.info("\n************************************");
    Logger.info("PUT REQUEST Example (XML)");
    Logger.info("************************************\n");

    Logger.info("Commented code shows how to send a XML PUT Request");

//		httpPUTRequest = restClientUtil.getHttpRequestInstance(HttpMethods.PUT, putURL2);
//		
//		xml = "<xml>xxxx</xml>";
//        
//      try 
//		{
//			httpResponse = restClientUtil.sendGapiRequest(HttpMethods.PUT, httpClient, httpPUTRequest, new ByteArrayEntity(xml.getBytes("UTF-8")));
//			
//		} 
//		catch (UnsupportedEncodingException e) 
//		{
//			Logger.error(e.toString());
//		}

  }
}
