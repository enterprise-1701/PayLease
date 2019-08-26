package com.paylease.app.qa.e2e.tests.resident;

import com.paylease.app.qa.e2e.tests.TieredFeeStructureBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.TieredFeeStructureDataProvider;
import org.testng.annotations.Test;

public class TieredFeeStructureTest extends TieredFeeStructureBase {

  public static final String REGION = "resident";
  public static final String FEATURE = "tieredFeeStructure";

  @Test(
      dataProvider = "provideResidentDataSample",
      dataProviderClass = TieredFeeStructureDataProvider.class,
      groups = {"litle", "flexibleFeeE2E"},
      retryAnalyzer = Retry.class
  )
  public void handlesResidentFeeSample(
      String testCaseId,
      String paymentMethod,
      boolean includeBaseFeeForResident,
      boolean includePhoneFee,
      boolean logAsFromAdmin,
      String paymentType,
      boolean baseFeeRounded,
      boolean useExpressPay,
      boolean includeAch,
      boolean choosePmIncur
  ) {
    handlesFees(
        REGION,
        FEATURE,
        testCaseId,
        paymentMethod,
        includeBaseFeeForResident,
        includePhoneFee,
        logAsFromAdmin,
        paymentType,
        baseFeeRounded,
        useExpressPay,
        includeAch,
        choosePmIncur,
        true
    );
  }

  @Test(
      dataProvider = "provideResidentDataAcc",
      dataProviderClass = TieredFeeStructureDataProvider.class,
      groups = {"e2e", "litle"},
      retryAnalyzer = Retry.class
  )
  public void handlesResidentFeeAcc(
      String testCaseId,
      String paymentMethod,
      boolean includeBaseFeeForResident,
      boolean includePhoneFee,
      boolean logAsFromAdmin,
      String paymentType,
      boolean baseFeeRounded,
      boolean useExpressPay,
      boolean includeAch,
      boolean choosePmIncur
  ) {
    handlesFees(
        REGION,
        FEATURE,
        testCaseId,
        paymentMethod,
        includeBaseFeeForResident,
        includePhoneFee,
        logAsFromAdmin,
        paymentType,
        baseFeeRounded,
        useExpressPay,
        includeAch,
        choosePmIncur,
        true
    );
  }

  @Test(
      dataProvider = "provideResidentPaymentMethod",
      dataProviderClass = TieredFeeStructureDataProvider.class,
      groups = {"e2e", "litle"},
      retryAnalyzer = Retry.class
  )
  public void handlesPropertySpecificVapFee(String testCaseId, String paymentMethod) {
    handlesFees(
        REGION,
        FEATURE,
        testCaseId,
        paymentMethod,
        true,
        false,
        false,
        PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        false,
        false,
        false,
        false,
        true
    );
  }

  @Override
  protected PaymentFlow beginPayment(
      String paymentType, String residentId, String paymentFieldLabel, String formattedAmount
  ) {
    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, paymentType);

    if (paymentType.equals(PaymentFlow.SCHEDULE_VARIABLE_AUTO)) {
      paymentFlow.openStep(PaymentFlow.STEP_SCHEDULE);
    } else {
      paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);
      paymentFlow.addAmount(paymentFieldLabel, formattedAmount);
    }

    return paymentFlow;
  }
}
