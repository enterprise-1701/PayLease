package com.paylease.app.qa.framework.api;

import com.paylease.app.qa.framework.utility.sshtool.SslTrustModifier;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class PostRequest {

  private HttpURLConnection connection;

  /**
   * Make a POST request using provided xml string.
   */
  public String submit(String url, String urlParameters)
      throws IOException, NoSuchAlgorithmException, KeyManagementException {

    byte[] postData = urlParameters.getBytes();

    try {

      URL newUrl = new URL(url);

      connection = (HttpURLConnection) newUrl.openConnection();

      SslTrustModifier.relaxHostChecking(connection);

      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("User-Agent", "Java client");
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

      try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
        wr.write(postData);
      }

      StringBuilder content;

      try (BufferedReader in = new BufferedReader(
          new InputStreamReader(connection.getInputStream()))) {

        String line;
        content = new StringBuilder();

        while ((line = in.readLine()) != null) {
          content.append(line);
          content.append(System.lineSeparator());
        }
      }

      return content.toString();

    } finally {

      connection.disconnect();
    }
  }
}