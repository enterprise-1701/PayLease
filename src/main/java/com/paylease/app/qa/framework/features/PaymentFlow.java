package com.paylease.app.qa.framework.features;

import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_MONTHLY;

import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.paymentflow.SelectResidentPage;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

public class PaymentFlow extends PageBase {

  public static final String UI_PM = "pm";
  public static final String UI_RES = "res";
  public static final String UI_GUEST = "guest";

  public static final String SCHEDULE_ONE_TIME = "One Time Payment";
  public static final String SCHEDULE_FIXED_AUTO = "Fixed Auto Pay";
  public static final String SCHEDULE_EDIT_FIXED_AUTO = "Edit Fixed Auto Pay";
  public static final String SCHEDULE_VARIABLE_AUTO = "Variable Auto Pay";

  public static final int STEP_SELECT_RESIDENT = 10;
  public static final int STEP_AMOUNT = 20;
  public static final int STEP_SCHEDULE = 30;
  public static final int STEP_METHOD = 40;
  public static final int STEP_NEW_BANK = 51;
  public static final int STEP_NEW_CREDIT = 52;
  public static final int STEP_NEW_DEBIT = 53;
  public static final int STEP_REVIEW = 60;
  public static final int STEP_RECEIPT = 70;
  public static final int STEP_COMPLETE = 100;

  private static final String STEP_KEY_SELECT_RESIDENT = "select_resident";
  private static final String STEP_KEY_AMOUNT = "payment_amount";
  private static final String STEP_KEY_SCHEDULE = "payment_schedule";
  private static final String STEP_KEY_METHOD = "payment_type";
  private static final String STEP_KEY_NEW_BANK = "payment_type_new_bank";
  private static final String STEP_KEY_NEW_CREDIT = "payment_type_new_credit";
  private static final String STEP_KEY_NEW_DEBIT = "payment_type_new_credit";
  private static final String STEP_KEY_REVIEW = "payment_review_and_submit";
  private static final String STEP_KEY_RECEIPT = "payment_receipt";

  private String ui;
  private String schedule;
  private String paymentId;
  private int currentStep;
  private String residentId;
  private HashMap<String, String> amounts;
  private String paymentMethod;
  private String cardType;
  private String bankType;
  private String accountNumber;

  @FindBy(id = "payment_for_label")
  private WebElement paymentForLabel;

  @FindBy(id = "payment_amount_label")
  private WebElement paymentAmountLabel;

  public PaymentFlow() {
    this(null, null, null);

    parseUrl();
  }

  public PaymentFlow(String ui, String schedule) {
    this(ui, schedule, "");
  }

  /**
   * Create a PaymentFlow object. The UI, schedule and paymentId determine which URLs to load
   * as clients work through the payment flow.
   *  @param ui PM, Res, or Guest
   * @param schedule One Time, Fixed or Variable
   * @param paymentId Specific transaction or autopay template ID
   */
  public PaymentFlow(String ui, String schedule, String paymentId) {
    super();
    this.ui = ui;
    this.schedule = schedule;
    this.paymentId = paymentId;

    this.residentId = "";
    this.amounts = new HashMap<>();
    this.paymentMethod = "";
    this.cardType = "";
    this.bankType = "";
    this.accountNumber = "";
  }

  public void setResidentId(String residentId) {
    this.residentId = residentId;
  }

  public void addAmount(String label, String amount) {
    amounts.put(label, amount);
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public void setBankType(String bankType) {
    this.bankType = bankType;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setCurrentStep(int step) {
    this.currentStep = step;
  }

  public String getUi() {
    return ui;
  }

  public String getSchedule() {
    return schedule;
  }
  public String getPaymentId() {
    return paymentId;
  }

  /**
   * Open the payment flow at a particular step.
   *
   * @param step Step to open
   */
  public void openStep(int step) {
    String url = buildUrl(step);
    openAndWait(url);
    currentStep = step;
  }

  /**
   * From the current step, fill forms and submit until reaching the desired step.
   *
   * @param step Step to reach
   */
  public void advanceToStep(int step) {
    if (0 == currentStep) {
      currentStep = getCurrentStep();
    }

    if (step < currentStep) {
      throw new IllegalArgumentException();
    }

    if (step == currentStep) {
      return;
    }

    if (currentStep == STEP_SELECT_RESIDENT) {
      SelectResidentPage selectResidentPage = new SelectResidentPage();
      selectResidentPage.clickToBeginPayment(residentId);

      if (schedule.equals(SCHEDULE_VARIABLE_AUTO)) {
        currentStep = STEP_SCHEDULE;
      } else {
        currentStep = STEP_AMOUNT;
      }

      // populate the generated transaction id
      parseUrl();
    }

    if (step == currentStep) {
      return;
    }

    if (currentStep == STEP_AMOUNT) {
      PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

      for (Map.Entry<String, String> entry : amounts.entrySet()) {
        String label = entry.getKey();
        String amount = entry.getValue();
        paymentAmountPage.setPaymentFieldAmount(label, amount);
      }

      paymentAmountPage.clickContinueButton();

      if (schedule.equals(SCHEDULE_ONE_TIME)) {
        currentStep = STEP_METHOD;
      } else {
        currentStep = STEP_SCHEDULE;
      }

      // populate the generated transaction id
      parseUrl();
    }

    if (currentStep >= step) {
      return;
    }

    if (currentStep == STEP_SCHEDULE) {
      SchedulePage schedulePage = new SchedulePage();
      schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, SELECT_MONTHLY);
      schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, true);
      schedulePage.fillAndSubmitPaymentScheduleDetails();

      currentStep = STEP_METHOD;
    }

    if (currentStep >= step) {
      return;
    }

    if (currentStep == STEP_METHOD) {
      PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

      paymentId = paymentMethodPage.getTransactionId();

      paymentMethodPage.selectPaymentMethod(paymentMethod);
      paymentMethodPage.clickContinueButton();

      switch (paymentMethod) {
        case PaymentMethodPage.NEW_BANK:
          currentStep = STEP_NEW_BANK;
          break;
        case PaymentMethodPage.NEW_CREDIT:
          currentStep = STEP_NEW_CREDIT;
          break;
        case PaymentMethodPage.NEW_DEBIT:
          currentStep = STEP_NEW_DEBIT;
          break;
        default:
          currentStep = STEP_REVIEW;
          break;
      }
    }

    if (currentStep >= step) {
      return;
    }

    if (currentStep == STEP_NEW_BANK) {
      BankAccountDetailsPage bankAccountDetailsPage = new BankAccountDetailsPage();

      bankAccountDetailsPage.prepAccountNumber(accountNumber);
      bankAccountDetailsPage.prepAccountType(bankType);

      bankAccountDetailsPage.fillBankDetailsAndSubmit();
      currentStep = STEP_REVIEW;
    }
    if (currentStep == STEP_NEW_CREDIT || currentStep == STEP_NEW_DEBIT) {
      CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();

      cardAccountDetailsPage.prepCardType(cardType);
      cardAccountDetailsPage.prepCardNumber(accountNumber);

      cardAccountDetailsPage.fillAndSubmitCardDetails();
      currentStep = STEP_REVIEW;
    }

    if (currentStep >= step) {
      return;
    }

    if (currentStep == STEP_REVIEW) {
      ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();
      reviewAndSubmitPage.clickSubmitButton();

      if (schedule.equals(SCHEDULE_ONE_TIME)) {
        ReceiptPage receiptPage = new ReceiptPage();

        ExpectedCondition<Boolean> pageLoad = new
            ExpectedCondition<Boolean>() {
              public Boolean apply(WebDriver driver) {
                return receiptPage.pageIsLoaded();
              }
            };

        try {
          wait.until(pageLoad);
          currentStep = STEP_RECEIPT;
        } catch (Throwable pageLoadWaitError) {
          Assert.assertFalse(true, "Timeout during page load");
        }
      } else {
        currentStep = STEP_COMPLETE;
      }
    }

    if (currentStep >= step) {
      return;
    }
  }

  /**
   * Determine which page we are on given the current URL.
   *
   * @return int matching the current step
   */
  public int getCurrentStep() {
    String currentUrl = driver.getCurrentUrl();
    /* SelectResidentPage has no URL comparison because it was observed that in some cases
     * select_resident is left off the URL*/
    SelectResidentPage pageSelect = new SelectResidentPage();
    if (pageSelect.pageIsLoaded()) {
      return STEP_SELECT_RESIDENT;
    }
    if (currentUrl.contains(STEP_KEY_AMOUNT)) {
      PaymentAmountPage page = new PaymentAmountPage();
      if (page.pageIsLoaded()) {
        return STEP_AMOUNT;
      }
    }
    if (currentUrl.contains(STEP_KEY_SCHEDULE)) {
      SchedulePage page = new SchedulePage();
      if (page.pageIsLoaded()) {
        return STEP_SCHEDULE;
      }
    }

    if (currentUrl.contains(STEP_KEY_METHOD)) {
      PaymentMethodPage page = new PaymentMethodPage();
      if (page.pageIsLoaded()) {
        return STEP_METHOD;
      }
    }
    if (currentUrl.contains(STEP_KEY_NEW_BANK)) {
      BankAccountDetailsPage page = new BankAccountDetailsPage();
      if (page.pageIsLoaded()) {
        return STEP_NEW_BANK;
      }
    }
    if (currentUrl.contains(STEP_KEY_NEW_CREDIT)) {
      CardAccountDetailsPage page = new CardAccountDetailsPage();
      if (page.pageIsLoaded()) {
        return STEP_NEW_CREDIT;
      }
    }
    if (currentUrl.contains(STEP_KEY_REVIEW)) {
      ReviewAndSubmitPage page = new ReviewAndSubmitPage();
      if (page.pageIsLoaded()) {
        return STEP_REVIEW;
      }
    }
    if (currentUrl.contains(STEP_KEY_RECEIPT)) {
      ReceiptPage page = new ReceiptPage();
      if (page.pageIsLoaded()) {
        return STEP_RECEIPT;
      }
    }
    return 0;
  }

  private String buildUrl(int step) {
    String url = ResourceFactory.getInstance().getProperty(ResourceFactory.APP_URL_KEY);
    switch (ui) {
      case UI_PM:
        url += "pm";
        break;
      case UI_RES:
      case UI_GUEST:
        url += "resident";
        break;
      default:
        throw new IllegalArgumentException("UI " + ui + " unknown");
    }

    url += "/";
    switch (schedule) {
      case SCHEDULE_ONE_TIME:
        url += "make_payment";
        break;
      case SCHEDULE_FIXED_AUTO:
        url += "create_fixed_autopay";
        break;
      case SCHEDULE_VARIABLE_AUTO:
        url += "create_variable_autopay";
        break;
      default:
        throw new IllegalArgumentException("Schedule " + schedule + " unknown");
    }

    url += "/";

    switch (step) {
      case STEP_SELECT_RESIDENT:
        url += STEP_KEY_SELECT_RESIDENT;
        break;
      case STEP_AMOUNT:
        url += STEP_KEY_AMOUNT;
        break;
      case STEP_SCHEDULE:
        url += STEP_KEY_SCHEDULE;
        break;
      case STEP_METHOD:
        url += STEP_KEY_METHOD;
        break;
      case STEP_NEW_BANK:
        url += STEP_KEY_NEW_BANK;
        break;
      case STEP_NEW_CREDIT:
        url += STEP_KEY_NEW_CREDIT;
        break;
      case STEP_NEW_DEBIT:
        url += STEP_KEY_NEW_DEBIT;
        break;
      case STEP_REVIEW:
        url += STEP_KEY_REVIEW;
        break;
      case STEP_RECEIPT:
        url += STEP_KEY_RECEIPT;
        break;
      default:
        throw new IllegalArgumentException("Step " + step + " unknown");
    }

    url += "/" + paymentId;

    if (step == STEP_NEW_DEBIT) {
      url += "/dc";
    }

    return url;
  }

  /**
   * parse the current url to set the ui, payment type, and payment id values
   */
  private void parseUrl() {
    String url = driver.getCurrentUrl();

    url = url.replace(BASE_URL, "");

    String[] urlParts = url.split("\\/");

    switch (urlParts[0]) {
      case UI_PM:
        this.ui = UI_PM;
        break;
      case UI_RES:
        this.ui = UI_RES;
        break;
      case UI_GUEST:
        this.ui = UI_GUEST;
        break;
    }

    switch (urlParts[1]) {
      case "make_payment":
        this.schedule = SCHEDULE_ONE_TIME;
        break;
      case "create_fixed_autopay":
        this.schedule = SCHEDULE_FIXED_AUTO;
        break;
      case "create_variable_autopay":
        this.schedule = SCHEDULE_VARIABLE_AUTO;
        break;
      case "edit_fixed_autopay":
        this.schedule = SCHEDULE_EDIT_FIXED_AUTO;
        break;
    }

    if (urlParts.length == 4) {
      this.paymentId = urlParts[3];
    }
  }

  /**
   * Get the text for "Payment for:" field.
   *
   * @return found payment for text value
   */
  public String getPaymentFor() {
    return paymentForLabel.getText();
  }

  /**
   * Get the text for "Payment Amount:" field.
   *
   * @return found amount text value
   */
  public String getPaymentAmount() {
    return paymentAmountLabel.getText();
  }
}
