package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import org.testng.annotations.Test;

public class VariableAutopayTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "variableAutopay";

  //---------------------------------------------VAP TESTS------------------------------------------

  @Test
  public void showDisclosureWithDebitCardTest() {
    Logger.info("Verify we see disclosure messages for Variable Autopay with debit card");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "tc13", PaymentFlow.SCHEDULE_VARIABLE_AUTO, PaymentFlow.STEP_NEW_DEBIT
    );
  }

  @Test
  public void showDisclosureWithCreditCardTest() {
    Logger.info("Verify we see disclosure messages for Variable Autopay with credit card");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "tc13", PaymentFlow.SCHEDULE_VARIABLE_AUTO, PaymentFlow.STEP_NEW_CREDIT
    );
  }

  @Test
  public void showDisclosureTest() {
    Logger.info("Verify we see disclosure messages for Variable Autopay");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "tc15", PaymentFlow.SCHEDULE_VARIABLE_AUTO, PaymentFlow.STEP_NEW_CREDIT
    );
  }
}
