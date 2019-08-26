package com.paylease.app.qa.functional.tests.backend;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.InvoiceDao;
import com.paylease.app.qa.framework.utility.database.client.dao.InvoiceItemsNewDao;
import com.paylease.app.qa.framework.utility.database.client.dto.Invoice;
import com.paylease.app.qa.framework.utility.database.client.dto.InvoiceItemsNew;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import java.sql.Connection;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InvoicesTest extends ScriptBase {

  public static final String REGION = "backend";
  public static final String FEATURE = "invoices";

  private static final int INVOICE_TYPE_NSF_FEE = 9;

  @Test(groups = {"invoices", "manual"}, dataProvider = "nsfFeeProvider", retryAnalyzer =
      Retry.class)
  public void nsfFeeTest(String testCase) {
    Logger.info("To verify NSF fee on the invoice matches the NSF fee multiplied by the number of "
        + "returned transactions.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String numberOfTrans = testSetupPage.getString("numberOfTrans");
    final String nsfCheckFee = testSetupPage.getString("nsfFee");
    final String invoiceConfigId = testSetupPage.getString("invoiceConfigId");

    int numberOfTransAsInt = Integer.parseInt(numberOfTrans);

    final double expectedAmount = numberOfTransAsInt * Double.parseDouble(nsfCheckFee);

    SshUtil sshUtil = new SshUtil();
    sshUtil.runBatchScript("process_invoices");

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    InvoiceDao invoiceDao = new InvoiceDao();

    Invoice invoice = invoiceDao
        .findLatestByInvoiceConfigId(connection, Long.parseLong(invoiceConfigId));
    Assert.assertNotNull(invoice, "There should be one invoice");

    InvoiceItemsNewDao invoiceItemsNewDao = new InvoiceItemsNewDao();
    ArrayList<InvoiceItemsNew> items =
        invoiceItemsNewDao
            .findByInvoiceIdAndType(connection, invoice.getInvoiceId(), INVOICE_TYPE_NSF_FEE);

    Assert.assertEquals(items.size(), 1, "There should be one nsf fee item on the invoice");

    InvoiceItemsNew item = items.get(0);
    Assert.assertEquals(item.getItemAmount(), expectedAmount,
        "NSF fee amount should be equal to the number of transactions multiplied by the fee");
    Assert.assertEquals(item.getItemQuantity(), numberOfTransAsInt,
        "The item quantity should be equal to the number of transactions");
  }

  @DataProvider(name = "nsfFeeProvider", parallel = false)
  private Object[][] nsfFeeProvider() {
    return new Object[][]{
        {"tc4090"},
        {"tc4108"},
        {"tc4109"},
    };
  }
}
