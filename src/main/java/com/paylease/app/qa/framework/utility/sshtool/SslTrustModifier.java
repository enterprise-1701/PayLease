package com.paylease.app.qa.framework.utility.sshtool;

import com.paylease.app.qa.framework.Logger;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

public class SslTrustModifier {

  private static final TrustingHostnameVerifier
      TRUSTING_HOSTNAME_VERIFIER = new TrustingHostnameVerifier();
  private static SSLSocketFactory factory;

  /**
   * Call this with any HttpURLConnection, and it will modify the trust settings if it is a HTTPS
   * connection.
   */
  public static void relaxHostChecking(HttpURLConnection conn)
      throws KeyManagementException, NoSuchAlgorithmException {

    if (conn instanceof HttpsURLConnection) {
      HttpsURLConnection httpsConnection = (HttpsURLConnection) conn;
      SSLSocketFactory factory = prepFactory(httpsConnection);
      httpsConnection.setSSLSocketFactory(factory);
      httpsConnection.setHostnameVerifier(TRUSTING_HOSTNAME_VERIFIER);
    }
  }

  private static synchronized SSLSocketFactory
  prepFactory(HttpsURLConnection httpsConnection)
      throws NoSuchAlgorithmException, KeyManagementException {

    if (factory == null) {
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(null, new TrustManager[]{new AlwaysTrustManager()}, null);
      factory = ctx.getSocketFactory();
    }
    return factory;
  }

  private static final class TrustingHostnameVerifier implements HostnameVerifier {

    public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  }

  private static class AlwaysTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
    }

    public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
    }

    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }
  }

  /**
   * Call this with any HttpURLConnection, and it will modify the trust settings if it is a HTTPS
   * connection.
   */
  public static HttpClient relaxRequestChecking() {
    HttpClient httpClient = null;
    try {
      httpClient = HttpClients
          .custom()
          .setSSLContext(
              new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
          .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
          .build();
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return httpClient;
  }
}