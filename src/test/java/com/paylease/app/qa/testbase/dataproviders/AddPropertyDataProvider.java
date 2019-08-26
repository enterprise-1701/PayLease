package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_1;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_126;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_127;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_128;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_129;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_130;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import com.paylease.app.qa.framework.newApi.Ledger;
import com.paylease.app.qa.framework.newApi.Payee;
import com.paylease.app.qa.framework.newApi.PaymentField;
import com.paylease.app.qa.framework.newApi.Property;
import org.testng.annotations.DataProvider;

public class AddPropertyDataProvider {

  @DataProvider(name = "addPropertyDataProvider", parallel = true)
  public Object[][] addPropertyData() {

    // Re-usable test values

    Property testProperty = createTestProperty();
    Ledger leaseLedgerEmptyCharge = createLedger(Ledger.LEASE, "");
    Ledger leaseLedgerSecCharge = createLedger(Ledger.LEASE, Ledger.SEC);
    Ledger securityLedgerEmptyCharge = createLedger(Ledger.SECURITY_DEPOSIT, null);
    Ledger securityLedgerSecCharge = createLedger(Ledger.SECURITY_DEPOSIT, Ledger.SEC);
    Ledger emptyLedger = createLedger(null, null);
    Ledger badLedger = createLedger("bad", "terrible");
    Ledger chargeCodeTooLong = createLedger(Ledger.SECURITY_DEPOSIT, Ledger.CHARGE_CODE_TOO_LONG);

    // Set up tests
    // ledger type, charge code

    // lease, empty charge code
    PaymentField pfLeaseLedgerEmptyCharge = createTestPaymentField("Payment Amount", "payment_amount");
    pfLeaseLedgerEmptyCharge.setLedger(leaseLedgerEmptyCharge);

    // lease, SEC
    PaymentField pfLeaseLedgerSecCharge = createTestPaymentField("Payment Amount", "payment_amount");
    pfLeaseLedgerSecCharge.setLedger(leaseLedgerSecCharge);

    // security_deposit, SEC
    PaymentField pfSecurityDepositSecCharge = createTestPaymentField("Security Deposit", "security_deposit");
    pfSecurityDepositSecCharge.setLedger(securityLedgerSecCharge);

    // security_deposit, empty charge code
    PaymentField pfSecurityDepositNoCharge = createTestPaymentField("Security Deposit", "security_deposit");
    pfSecurityDepositNoCharge.setLedger(securityLedgerEmptyCharge);

    // empty ledger type, empty charge code
    PaymentField pfNoLedgerNoCharge = createTestPaymentField("", "");
    pfNoLedgerNoCharge.setLedger(emptyLedger);

    // bad ledger type, bad charge code
    PaymentField pfBadLedgerBadCharge = createTestPaymentField("", "");
    pfBadLedgerBadCharge.setLedger(badLedger);

    // charge code too long
    PaymentField pfChargeCodeTooLong = createTestPaymentField("", "");
    pfChargeCodeTooLong.setLedger(chargeCodeTooLong);

    //no ledger at all
    PaymentField pfNoLedger = createTestPaymentField(null, null);

    // expected response variables
    Payee payeeLeaseNoCharge = createPayee("Payment Amount", "payment_amount","lease", "");
    Payee payeeSecurityDepositSecCharge = createPayee("Security Deposit", "security_deposit","security_deposit", "SEC");
    Payee payeeSecurityDepositNoCharge = createPayee("Security Deposit", "security_deposit","security_deposit", null);
    Payee payeeNoLedger = createPayee(null, null, null, null);

    return new Object[][]{
        //MRI
        {"tc1", "tc6526", testProperty, pfLeaseLedgerEmptyCharge, null, RESPONSE_CODE_1,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_1), payeeLeaseNoCharge, null},
        {"tc2", "tc6526", testProperty, pfLeaseLedgerSecCharge, null, RESPONSE_CODE_129,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_129), null, null},
        {"tc3", "tc6526", testProperty, pfSecurityDepositSecCharge, pfLeaseLedgerEmptyCharge,
            RESPONSE_CODE_1,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_1), payeeSecurityDepositSecCharge,
            payeeLeaseNoCharge},
        {"tc4", "tc6526", testProperty, pfSecurityDepositNoCharge, pfLeaseLedgerSecCharge,
            RESPONSE_CODE_128,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_128), null, null},
        {"tc5", "tc6526", testProperty, pfNoLedgerNoCharge, null, RESPONSE_CODE_130,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_130), null, null},
        {"tc6", "tc6526", testProperty, pfBadLedgerBadCharge, null, RESPONSE_CODE_126,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_126), null, null},
        {"tc7", "tc6526", testProperty, pfNoLedger, null, RESPONSE_CODE_1,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_1), payeeNoLedger, null},
        {"tc8", "tc6526", testProperty, pfChargeCodeTooLong, null, RESPONSE_CODE_127,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_127), null, null},

        //ResMan
        {"tc9", "tc6527", testProperty, pfLeaseLedgerEmptyCharge, null, RESPONSE_CODE_1,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_1), payeeLeaseNoCharge, null},
        {"tc10", "tc6527", testProperty, pfLeaseLedgerSecCharge, null, RESPONSE_CODE_129,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_129), null, null},
        {"tc11", "tc6527", testProperty, pfSecurityDepositSecCharge, pfLeaseLedgerSecCharge,
            RESPONSE_CODE_129,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_129), null, null},
        {"tc12", "tc6527", testProperty, pfSecurityDepositNoCharge, pfLeaseLedgerEmptyCharge,
            RESPONSE_CODE_1,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_1), payeeSecurityDepositNoCharge,
            payeeLeaseNoCharge},
        {"tc13", "tc6527", testProperty, pfNoLedgerNoCharge, null, RESPONSE_CODE_130,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_130), null, null},
        {"tc14", "tc6527", testProperty, pfBadLedgerBadCharge, null, RESPONSE_CODE_126,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_126), null, null},
        {"tc15", "tc6527", testProperty, pfNoLedger, null, RESPONSE_CODE_1,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_1), payeeNoLedger, null},
    };
  }

  private Property createTestProperty() {
    Property testProperty = new Property();
    testProperty.setPropertyId("124908");
    testProperty.setPropertyName("Test-Prop-API");
    testProperty.setStreetAddress("123 Main Street");
    testProperty.setCity("Anytown");
    testProperty.setState("California");
    testProperty.setPostalCode("90002");
    testProperty.setUnitCount("100");
    testProperty.setPhone("619-444-5555");
    testProperty.setFax("619-444-6666");
    testProperty.setEmail("testemail@property.com");
    testProperty.setFreqId("3");
    testProperty.setLogoUrl("http://www.site.com/path/to/image.jpg");

    return testProperty;
  }

  private PaymentField createTestPaymentField(String fieldName, String varName) {
    PaymentField testPaymentField = new PaymentField();
    testPaymentField.setFieldName(fieldName);
    testPaymentField.setVarName(varName);
    testPaymentField.setBankName("Bank of America");
    testPaymentField.setBankAccountType("Checking");
    testPaymentField.setBankAccountRouting("490000018");
    testPaymentField.setBankAccountNumber("9002672278539");

    return testPaymentField;
  }

  private Payee createPayee(String fieldName, String varName, String ledgerType, String chargeCode) {
    Payee testPayee = new Payee();
    testPayee.setPayeeIdFieldName(fieldName);
    testPayee.setPayeeIdVarName(varName);
    testPayee.setPayeeIdLedgerType(ledgerType);
    testPayee.setPayeeIdChargeCode(chargeCode);
    testPayee.setBankAccountNumber("9002672278539");

    return testPayee;
  }

  private Ledger createLedger(String ledgerType, String chargeCode) {
    Ledger testLedger = new Ledger();

    if (ledgerType != null) {
      testLedger.setLedgerType(ledgerType);
    } else {
      testLedger.setLedgerType("");
    }

    if (chargeCode != null) {
      testLedger.setChargeCode(chargeCode);
    } else {
      testLedger.setChargeCode("");
    }

    return testLedger;
  }
}

