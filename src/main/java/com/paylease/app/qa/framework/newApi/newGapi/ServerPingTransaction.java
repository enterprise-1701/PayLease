package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Transaction")
public class ServerPingTransaction extends Transaction {

  public static final String SERVER_PING_TRANSACTION = "ServerPing";

}
