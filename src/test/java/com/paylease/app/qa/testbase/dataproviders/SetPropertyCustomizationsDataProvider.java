package com.paylease.app.qa.testbase.dataproviders;

import com.paylease.app.qa.framework.newApi.CodeMessage;
import com.paylease.app.qa.framework.newApi.Customization;
import com.paylease.app.qa.framework.newApi.Customizations;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;

public class SetPropertyCustomizationsDataProvider {

  public static final String ENABLE_ACH = "EnableACH";
  public static final String ENABLE_CC = "EnableCC";
  public static final String ENABLE_CASH_PAY = "EnableCashPay";
  public static final String LOCK_BALANCES = "LockBalances";
  public static final String DISALLOW_PRE_PAYMENTS = "DisallowPrePayments";
  private static final String BAD_CUSTOMIZATION = "ThisIsNotARealCustomizationAndWeShouldHandleItCorrectly";

  private static final String ENABLE_CUSTOMIZATION = "YES";
  private static final String DISABLE_CUSTOMIZATION = "NO";

  private static final String SUCCESS_YES = "YES";
  private static final String SUCCESS_NO = "NO";
  private static final String SUCCESS_PARTIAL = "PARTIAL";

  private static final String CODE_1 = "1";
  private static final String CODE_99 = "99";
  private static final String CODE_131 = "131";
  private static final String CODE_132 = "132";
  private static final String CODE_133 = "133";

  private static final String CODE_1_MESSAGE = "The request completed successfully.";
  private static final String SEE_INDIVIDUAL_PROP_MESSAGE = "See individual properties for error details.";
  private static final String SEE_INDIVIDUAL_CUST_MESSAGE = "See individual customizations for error details.";
  public static final String CUSTOMIZATION_NOT_RECOGNIZED = "Customization ThisIsNotARealCustomizationAndWeShouldHandleItCorrectly for property {{property}} was not recognized";
  private static final String CODE_132_MESSAGE_CASHPAY = "PM setting for customization EnableCashPay was off, so Property value could not be turned on.";
  private static final String CODE_132_MESSAGE_LOCKBALANCES = "PM setting for customization LockBalances was off, so Property value could not be turned on.";
  private static final String CODE_132_MESSAGE_DISALLOWPREPAYMENTS = "PM setting for customization DisallowPrePayments was off, so Property value could not be turned on.";
  private static final String CODE_133_MESSAGE = "Batch Request:  Some errors were encountered.";


  private Customization enableAch = createCustomization(ENABLE_ACH, ENABLE_CUSTOMIZATION);
  private Customization disableAch = createCustomization(ENABLE_ACH, DISABLE_CUSTOMIZATION);
  private Customization enableCC = createCustomization(ENABLE_CC, ENABLE_CUSTOMIZATION);
  private Customization disableCC = createCustomization(ENABLE_CC, DISABLE_CUSTOMIZATION);
  private Customization enableCashPay = createCustomization(ENABLE_CASH_PAY, ENABLE_CUSTOMIZATION);
  private Customization disableCashPay = createCustomization(ENABLE_CASH_PAY,
      DISABLE_CUSTOMIZATION);
  private Customization enableLockBalances = createCustomization(LOCK_BALANCES,
      ENABLE_CUSTOMIZATION);
  private Customization disableLockBalances = createCustomization(LOCK_BALANCES,
      DISABLE_CUSTOMIZATION);
  private Customization enableDisallowPrePayments = createCustomization(DISALLOW_PRE_PAYMENTS,
      ENABLE_CUSTOMIZATION);
  private Customization disableDisallowPrePayments = createCustomization(DISALLOW_PRE_PAYMENTS,
      DISABLE_CUSTOMIZATION);
  private Customization badCustomization = createCustomization(BAD_CUSTOMIZATION,
      ENABLE_CUSTOMIZATION);

  private Customization enableAchSuccessful = createCustomization(ENABLE_ACH, ENABLE_CUSTOMIZATION,
      SUCCESS_YES);
  private Customization enableCCSuccessful = createCustomization(ENABLE_CC, ENABLE_CUSTOMIZATION,
      SUCCESS_YES);
  private Customization disableAchSuccessful = createCustomization(ENABLE_ACH,
      DISABLE_CUSTOMIZATION,
      SUCCESS_YES);
  private Customization disableCCSuccessful = createCustomization(ENABLE_CC, DISABLE_CUSTOMIZATION,
      SUCCESS_YES);
  private Customization enableCashPaySuccessful = createCustomization(ENABLE_CASH_PAY,
      ENABLE_CUSTOMIZATION,
      SUCCESS_YES);
  private Customization enableCashPayUnsuccessful = createCustomization(ENABLE_CASH_PAY,
      ENABLE_CUSTOMIZATION, SUCCESS_NO);
  private Customization disableCashPaySuccessful = createCustomization(ENABLE_CASH_PAY,
      DISABLE_CUSTOMIZATION, SUCCESS_YES);
  private Customization enableLockBalancesSuccessful = createCustomization(LOCK_BALANCES,
      ENABLE_CUSTOMIZATION, SUCCESS_YES);
  private Customization enableLockBalancesUnsuccessful = createCustomization(LOCK_BALANCES,
      ENABLE_CUSTOMIZATION, SUCCESS_NO);
  private Customization disableLockBalancesSuccessful = createCustomization(LOCK_BALANCES,
      DISABLE_CUSTOMIZATION, SUCCESS_YES);
  private Customization enableDisallowPrePaymentsSuccessful = createCustomization(
      DISALLOW_PRE_PAYMENTS,
      ENABLE_CUSTOMIZATION, SUCCESS_YES);
  private Customization enableDisallowPrePaymentsUnsuccessful = createCustomization(
      DISALLOW_PRE_PAYMENTS,
      ENABLE_CUSTOMIZATION, SUCCESS_NO);
  private Customization disableDisallowPrePaymentsSuccessful = createCustomization(
      DISALLOW_PRE_PAYMENTS,
      DISABLE_CUSTOMIZATION, SUCCESS_YES);
  private Customization badCustomizationUnsuccessful = createCustomization(BAD_CUSTOMIZATION,
      ENABLE_CUSTOMIZATION, SUCCESS_NO);

  private CodeMessage code1Message = createCodeMessage(CODE_1, CODE_1_MESSAGE);
  private CodeMessage errorCode99MessageProp = createCodeMessage(CODE_99,
      SEE_INDIVIDUAL_PROP_MESSAGE);
  private CodeMessage errorCode99MessageCust = createCodeMessage(CODE_99,
      SEE_INDIVIDUAL_CUST_MESSAGE);
  private CodeMessage errorCode131Message = createCodeMessage(CODE_131,
      CUSTOMIZATION_NOT_RECOGNIZED);
  private CodeMessage errorCode132MessageCashPay = createCodeMessage(CODE_132,
      CODE_132_MESSAGE_CASHPAY);
  private CodeMessage errorCode132MessageLockBalances = createCodeMessage(CODE_132,
      CODE_132_MESSAGE_LOCKBALANCES);
  private CodeMessage errorCode132MessageDisallowPrePayments = createCodeMessage(CODE_132,
      CODE_132_MESSAGE_DISALLOWPREPAYMENTS);
  private CodeMessage errorCode133Message = createCodeMessage(CODE_133, CODE_133_MESSAGE);
  private CodeMessage errorCode133MessageProp = createCodeMessage(CODE_133,
      SEE_INDIVIDUAL_PROP_MESSAGE);
  private CodeMessage errorCode133MessageCust = createCodeMessage(CODE_133,
      SEE_INDIVIDUAL_CUST_MESSAGE);

  private CodeMessage createCodeMessage(String code, String message) {
    CodeMessage codeMessage = new CodeMessage();
    codeMessage.setCode(code);
    codeMessage.setMessage(message);
    return codeMessage;
  }

  private Customization createCustomization(String name, String value) {
    return createCustomization(name, value, null);
  }

  private Customization createCustomization(String name, String value, String success) {
    Customization customization = new Customization();
    customization.setName(name);
    customization.setValue(value);
    customization.setSuccess(success);
    return customization;
  }

  private Customizations groupCustomizations(Customization customization1,
      Customization customization2) {
    Customizations customizations = new Customizations();
    List<Customization> customizationList = new ArrayList<>();
    customizationList.add(customization1);
    customizationList.add(customization2);
    customizations.setCustomizationList(customizationList);
    return customizations;
  }

  @DataProvider(name = "setPropertyCustomizationsDataProvider", parallel = true)
  public Object[][] setPropertyCustomizationsData() {

    return new Object[][]{
        {"tc6796", "tc6796", groupCustomizations(enableAch, null), null,
            groupCustomizations(enableAchSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6803", "tc6796", groupCustomizations(enableCC, null), null,
            groupCustomizations(enableCCSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6804", "tc6796", groupCustomizations(disableAch, null), null,
            groupCustomizations(disableAchSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6805", "tc6796", groupCustomizations(disableCC, null), null,
            groupCustomizations(disableCCSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6806", "tc6796", groupCustomizations(enableCashPay, null), null,
            groupCustomizations(enableCashPayUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust,
                errorCode132MessageCashPay, errorCode133Message}, SUCCESS_NO, null},
        {"tc6807", "tc6807", groupCustomizations(enableCashPay, null), null,
            groupCustomizations(enableCashPaySuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6808", "tc6796", groupCustomizations(disableCashPay, null), null,
            groupCustomizations(disableCashPaySuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6809", "tc6807", groupCustomizations(disableCashPay, null), null,
            groupCustomizations(disableCashPaySuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6810", "tc6810", groupCustomizations(enableLockBalances, null), null,
            groupCustomizations(enableLockBalancesSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6811", "tc6796", groupCustomizations(enableLockBalances, null), null,
            groupCustomizations(enableLockBalancesUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust,
                errorCode132MessageLockBalances, errorCode133Message}, SUCCESS_NO, null},
        {"tc6812", "tc6796", groupCustomizations(disableLockBalances, null), null,
            groupCustomizations(disableLockBalancesSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6813", "tc6810", groupCustomizations(disableLockBalances, null), null,
            groupCustomizations(disableLockBalancesSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6814", "tc6814", groupCustomizations(enableDisallowPrePayments, null), null,
            groupCustomizations(enableDisallowPrePaymentsSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6815", "tc6796", groupCustomizations(enableDisallowPrePayments, null), null,
            groupCustomizations(enableDisallowPrePaymentsUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust,
                errorCode132MessageDisallowPrePayments, errorCode133Message}, SUCCESS_NO, null},
        {"tc6816", "tc6796", groupCustomizations(disableDisallowPrePayments, null), null,
            groupCustomizations(disableDisallowPrePaymentsSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6817", "tc6814", groupCustomizations(disableDisallowPrePayments, null), null,
            groupCustomizations(disableDisallowPrePaymentsSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6818", "tc6796", groupCustomizations(enableAch, enableCC), null,
            groupCustomizations(enableAchSuccessful, enableCCSuccessful), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6819", "tc6796", groupCustomizations(enableCC, enableCashPay), null,
            groupCustomizations(enableCCSuccessful, enableCashPayUnsuccessful), null,
            SUCCESS_PARTIAL, new CodeMessage[]{errorCode133MessageProp, errorCode133MessageCust,
            errorCode132MessageCashPay, errorCode133Message}, SUCCESS_PARTIAL, null},
        {"tc6820", "tc6796", groupCustomizations(disableCC, null),
            groupCustomizations(enableDisallowPrePayments, null),
            groupCustomizations(disableCCSuccessful, null),
            groupCustomizations(enableDisallowPrePaymentsUnsuccessful, null), SUCCESS_PARTIAL,
            new CodeMessage[]{errorCode133MessageProp, errorCode99MessageCust,
                errorCode132MessageDisallowPrePayments, errorCode133Message}, SUCCESS_YES,
            SUCCESS_NO},
        {"tc6821", "tc6796", groupCustomizations(badCustomization, null), null,
            groupCustomizations(badCustomizationUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust, errorCode131Message,
                errorCode133Message}, SUCCESS_NO, null},

        {"tc6822", "tc6822", groupCustomizations(enableAch, null), null,
            groupCustomizations(enableAchSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6823", "tc6822", groupCustomizations(enableCC, null), null,
            groupCustomizations(enableCCSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6824", "tc6822", groupCustomizations(disableAch, null), null,
            groupCustomizations(disableAchSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6825", "tc6822", groupCustomizations(disableCC, null), null,
            groupCustomizations(disableCCSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6826", "tc6822", groupCustomizations(enableCashPay, null), null,
            groupCustomizations(enableCashPayUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust,
                errorCode132MessageCashPay, errorCode133Message}, SUCCESS_NO, null},
        {"tc6827", "tc6827", groupCustomizations(enableCashPay, null), null,
            groupCustomizations(enableCashPaySuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6828", "tc6822", groupCustomizations(disableCashPay, null), null,
            groupCustomizations(disableCashPaySuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6829", "tc6827", groupCustomizations(disableCashPay, null), null,
            groupCustomizations(disableCashPaySuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6830", "tc6830", groupCustomizations(enableLockBalances, null), null,
            groupCustomizations(enableLockBalancesSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6831", "tc6822", groupCustomizations(enableLockBalances, null), null,
            groupCustomizations(enableLockBalancesUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust,
                errorCode132MessageLockBalances, errorCode133Message}, SUCCESS_NO, null},
        {"tc6832", "tc6822", groupCustomizations(disableLockBalances, null), null,
            groupCustomizations(disableLockBalancesSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6833", "tc6830", groupCustomizations(disableLockBalances, null), null,
            groupCustomizations(disableLockBalancesSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6834", "tc6834", groupCustomizations(enableDisallowPrePayments, null), null,
            groupCustomizations(enableDisallowPrePaymentsSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6835", "tc6822", groupCustomizations(enableDisallowPrePayments, null), null,
            groupCustomizations(enableDisallowPrePaymentsUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust,
                errorCode132MessageDisallowPrePayments, errorCode133Message}, SUCCESS_NO, null},
        {"tc6836", "tc6822", groupCustomizations(disableDisallowPrePayments, null), null,
            groupCustomizations(disableDisallowPrePaymentsSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6837", "tc6834", groupCustomizations(disableDisallowPrePayments, null), null,
            groupCustomizations(disableDisallowPrePaymentsSuccessful, null), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6838", "tc6822", groupCustomizations(enableAch, enableCC), null,
            groupCustomizations(enableAchSuccessful, enableCCSuccessful), null, SUCCESS_YES,
            new CodeMessage[]{code1Message}, SUCCESS_YES, null},
        {"tc6839", "tc6822", groupCustomizations(enableCC, enableCashPay), null,
            groupCustomizations(enableCCSuccessful, enableCashPayUnsuccessful), null,
            SUCCESS_PARTIAL, new CodeMessage[]{errorCode133MessageProp, errorCode133MessageCust,
            errorCode132MessageCashPay, errorCode133Message}, SUCCESS_PARTIAL, null},
        {"tc6840", "tc6822", groupCustomizations(disableCC, null),
            groupCustomizations(enableDisallowPrePayments, null),
            groupCustomizations(disableCCSuccessful, null),
            groupCustomizations(enableDisallowPrePaymentsUnsuccessful, null), SUCCESS_PARTIAL,
            new CodeMessage[]{errorCode133MessageProp, errorCode99MessageCust,
                errorCode132MessageDisallowPrePayments, errorCode133Message}, SUCCESS_YES,
            SUCCESS_NO},
        {"tc6841", "tc6822", groupCustomizations(badCustomization, null), null,
            groupCustomizations(badCustomizationUnsuccessful, null), null, SUCCESS_NO,
            new CodeMessage[]{errorCode99MessageProp, errorCode99MessageCust, errorCode131Message,
                errorCode133Message}, SUCCESS_NO, null},
    };
  }

}
