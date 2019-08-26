package com.paylease.app.qa.functional.tests.backend;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.AmsiTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.MriTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.OnesiteTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ResmanTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.YavoTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dto.AmsiTransactions;
import com.paylease.app.qa.framework.utility.database.client.dto.MriTransactionsDto;
import com.paylease.app.qa.framework.utility.database.client.dto.OnesiteTransactionsDto;
import com.paylease.app.qa.framework.utility.database.client.dto.ResmanTransactionsDto;
import com.paylease.app.qa.framework.utility.database.client.dto.YavoTransactionsDto;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class IpnSlowIpnTest extends ScriptBase {

  private static final String REGION = "backend";
  private static final String FEATURE = "ipnSlowIpn";

  private static final String FAILED_TO_OBTAIN_LOCK = "This transaction failed to acquire the lock.";

  @Test(groups = {"manual", "queue"})
  public void mriIpn() throws Exception {
    ArrayList<String> testData = setUpTest("mriIpn");
    String[] transactionList = testData.get(0).split(", ");

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnAndSlowIpnUntilBothComplete();
    sshUtil.runIpnAndSlowIpnUntilBothComplete();

    SoftAssert softAssert = new SoftAssert();
    boolean unableToGetLock = false;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    MriTransactionsDao mriTransactionsDao = new MriTransactionsDao();
    for (String transId : transactionList) {
      List<MriTransactionsDto> transaction = mriTransactionsDao.findByTransId(connection, Integer.valueOf(transId), 1);
      String requestMessage = transaction.get(0).getRequestMsg();
      if (requestMessage.equals(FAILED_TO_OBTAIN_LOCK)) {
        unableToGetLock = true;
      }
    }

    MriTransactionsDto diffResMriTrans = mriTransactionsDao.findByTransId(connection, Integer.valueOf(testData.get(1)), 1).get(0);
    softAssert.assertNotEquals(diffResMriTrans.getRequestMsg(), FAILED_TO_OBTAIN_LOCK, "Transaction from different Resident should integrate");
    softAssert.assertTrue(unableToGetLock, "Should have at least 1 transaction that is not able to get lock and bails");

    softAssert.assertAll();
  }

  @Test(groups = {"manual", "queue"})
  public void amsiIpn() throws Exception {
    ArrayList<String> testData = setUpTest("amsiIpn");
    String[] transactionList = testData.get(0).split(", ");

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnAndSlowIpnUntilBothComplete();
    sshUtil.runIpnAndSlowIpnUntilBothComplete();

    SoftAssert softAssert = new SoftAssert();
    boolean unableToGetLock = false;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    AmsiTransactionsDao amsiTransactionsDao = new AmsiTransactionsDao();
    for (String transId : transactionList) {
      List<AmsiTransactions> transaction = amsiTransactionsDao.findByTransId(connection, Integer.valueOf(transId), 1);
      String requestMessage = transaction.get(0).getAddPaymentResult();
      if (requestMessage.equals(FAILED_TO_OBTAIN_LOCK)) {
        unableToGetLock = true;
      }
    }

    AmsiTransactions diffResMriTrans = amsiTransactionsDao.findByTransId(connection, Integer.valueOf(testData.get(1)), 1).get(0);

    softAssert.assertEquals(diffResMriTrans.getProcessingStatus(), "COMPLETED", FAILED_TO_OBTAIN_LOCK);
    softAssert.assertTrue(unableToGetLock, "Should have at least 1 transaction that is not able to get lock and bails");

    softAssert.assertAll();
  }

  @Test(groups = {"manual", "queue"})
  public void resmanIpn() throws Exception {
    ArrayList<String> testData = setUpTest("resmanIpn");
    String[] transactionList = testData.get(0).split(", ");

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnAndSlowIpnUntilBothComplete();
    sshUtil.runIpnAndSlowIpnUntilBothComplete();

    SoftAssert softAssert = new SoftAssert();
    boolean unableToGetLock = false;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    ResmanTransactionsDao resmanTransactionsDao = new ResmanTransactionsDao();

    for (String transId : transactionList) {
      List<ResmanTransactionsDto> transaction = resmanTransactionsDao.findByTransId(connection,
          Integer.valueOf(transId), 1);
      String requestMessage = transaction.get(0).getRequestMsg();
      if (requestMessage.equals(FAILED_TO_OBTAIN_LOCK)) {
        unableToGetLock = true;
      }
    }

    ResmanTransactionsDto diffResTrans = resmanTransactionsDao.findByTransId(connection,
        Integer.valueOf(testData.get(1)), 1).get(0);

    softAssert.assertNotEquals(diffResTrans.getRequestMsg(), FAILED_TO_OBTAIN_LOCK, "Transaction from different Resident should integrate");
    softAssert.assertTrue(unableToGetLock, "Should have at least 1 transaction that is not able to get lock and bails");

    softAssert.assertAll();
  }

  @Test(groups = {"manual", "queue"})
  public void yavoIpn() throws Exception {
    ArrayList<String> testData = setUpTest("yavoIpn");
    String[] transactionList = testData.get(0).split(", ");

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnAndSlowIpnUntilBothComplete();
    sshUtil.runIpnAndSlowIpnUntilBothComplete();

    SoftAssert softAssert = new SoftAssert();
    boolean unableToGetLock = false;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    YavoTransactionsDao yavoTransactionsDao = new YavoTransactionsDao();
    for (String transId : transactionList) {
      List<YavoTransactionsDto> transaction = yavoTransactionsDao.findByTransId(connection, Integer.valueOf(transId), 1);
      String requestMessage = transaction.get(0).getRequestMsg();
      if (requestMessage.equals(FAILED_TO_OBTAIN_LOCK)) {
        unableToGetLock = true;
      }
    }

    YavoTransactionsDto diffResTrans = yavoTransactionsDao.findByTransId(connection, Integer.valueOf(testData.get(1)), 1).get(0);

    softAssert.assertNotEquals(diffResTrans.getRequestMsg(), FAILED_TO_OBTAIN_LOCK, "Transaction from different Resident should integrate");
    softAssert.assertTrue(unableToGetLock, "Should have at least 1 transaction that is not able to get lock and bails");

    softAssert.assertAll();
  }

  @Test(groups = {"manual", "queue"})
  public void onesiteIpn() throws Exception {
    ArrayList<String> testData = setUpTest("onesiteIpn");
    String[] transactionList = testData.get(0).split(", ");

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnAndSlowIpnUntilBothComplete();
    sshUtil.runIpnAndSlowIpnUntilBothComplete();

    SoftAssert softAssert = new SoftAssert();
    boolean unableToGetLock = false;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    OnesiteTransactionsDao onesiteTransactionsDao = new OnesiteTransactionsDao();
    for (String transId : transactionList) {
      List<OnesiteTransactionsDto> transaction = onesiteTransactionsDao.findByTransId(connection, Integer.valueOf(transId), 1);
      String requestMessage = transaction.get(0).getRequest();
      if (requestMessage != null && requestMessage.equals(FAILED_TO_OBTAIN_LOCK)) {
        unableToGetLock = true;
      }
    }


    OnesiteTransactionsDto diffResTrans = onesiteTransactionsDao.findByTransId(connection, Integer.valueOf(testData.get(1)), 1).get(0);

    softAssert.assertNotEquals(diffResTrans.getRequest(), FAILED_TO_OBTAIN_LOCK, "Transaction from different Resident should integrate");
    softAssert.assertTrue(unableToGetLock, "Should have at least 1 transaction that is not able to get lock and bails");

    softAssert.assertAll();
  }

  private ArrayList<String> setUpTest(String testCase) {
    ArrayList<String> testData = new ArrayList<>();
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    testData.add(testSetupPage.getString("transactionList"));
    testData.add(testSetupPage.getString("differentResTransId"));

    return testData;
  }
}
