package com.paylease.app.qa.framework.newApi;


import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil.HttpMethods;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * PayLeaseGatewayRequest Entity
 *
 * @author Jeffrey Walker
 */
@XmlType(propOrder = {"credentials", "mode", "transactions"})
@XmlRootElement(name = "PayLeaseGatewayRequest")
public class PayLeaseGatewayRequest extends PageBase {

  public static final String TEST_MODE = "Test";

  public static final String URL = BASE_URL + "gapi/request.php";

  private Credentials credentials;
  private String mode;
  private Transactions transactions;

  //Setter Methods
  @XmlElement(name = "Credentials")
  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  @XmlElement(name = "Mode")
  public void setMode(String mode) {
    this.mode = mode;
  }

  @XmlElement(name = "Transactions")
  public void setTransactions(Transactions transactions) {
    this.transactions = transactions;
  }

  //Getter Methods
  public Credentials getCredentials() {
    return credentials;
  }

  public String getMode() {
    return mode;
  }

  public Transactions getTransactions() {
    return transactions;
  }

}

