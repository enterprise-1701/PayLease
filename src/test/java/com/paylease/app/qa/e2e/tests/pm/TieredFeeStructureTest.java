package com.paylease.app.qa.e2e.tests.pm;

import com.paylease.app.qa.e2e.tests.TieredFeeStructureBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.TieredFeeStructureDataProvider;
import org.testng.annotations.Test;

public class TieredFeeStructureTest extends TieredFeeStructureBase {

  public static final String REGION = "pm";
  public static final String FEATURE = "tieredFeeStructure";

  @Test(
      dataProvider = "providePmDataSample",
      dataProviderClass = TieredFeeStructureDataProvider.class,
      groups = {"litle", "flexibleFeeE2E"},
      retryAnalyzer = Retry.class
  )
  public void handlesPmFeeSample(
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
        false
    );
  }

  @Test(
      dataProvider = "providePmDataAcc",
      dataProviderClass = TieredFeeStructureDataProvider.class,
      groups = {"e2e", "litle"},
      retryAnalyzer = Retry.class
  )
  public void handlesPmFeeAcc(
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
        false
    );
  }

  @Test(
      dataProvider = "providePmPaymentMethod",
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
        false
    );
  }

  @Override
  protected PaymentFlow beginPayment(
      String paymentType, String residentId, String paymentFieldLabel, String formattedAmount
  ) {
    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, paymentType);

    paymentFlow.openStep(PaymentFlow.STEP_SELECT_RESIDENT);
    paymentFlow.setResidentId(residentId);
    paymentFlow.addAmount(paymentFieldLabel, formattedAmount);

    return paymentFlow;
  }
}
