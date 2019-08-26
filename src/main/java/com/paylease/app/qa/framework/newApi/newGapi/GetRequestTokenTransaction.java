package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Transaction")
public class GetRequestTokenTransaction extends Transaction {

  public static final String GET_REQUEST_TOKEN_TRANSACTION = "GetRequestToken";

}
