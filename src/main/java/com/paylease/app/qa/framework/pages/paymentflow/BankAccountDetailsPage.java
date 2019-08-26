package com.paylease.app.qa.framework.pages.paymentflow;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class BankAccountDetailsPage extends PageBase {

  public static final String CHECKING_ACCOUNT = "Checking";
  public static final String SAVINGS_ACCOUNT = "Savings";

  public static final String NAME_ON_ACCOUNT_BOX = "Name On Account";
  public static final String BANK_NAME_BOX = "Bank Name";
  public static final String ACCOUNT_TYPE_SELECT = "Account Type";
  public static final String ROUTING_NUMBER_BOX = "Routing Number";
  public static final String CONFIRM_ACCOUNT_NUMBER_BOX = "Confirm Account Number";

  private static final String ROUTING_NUMBER = "011000028";
  private static final String INVALID_ROUTING_NUMBER = "911000028";
  private static final String BANK_NAME = "J.P Morgan Chase";

  private Faker faker;

  @FindBy(id = "name")
  private WebElement nameOnAccount;

  @FindBy(id = "bank_name")
  private WebElement bankName;

  @FindBy(id = "account_type")
  private WebElement accountType;

  @FindBy(id = "routing_number")
  private WebElement routingNumber;

  @FindBy(id = "account_number")
  private WebElement accountNumber;

  @FindBy(id = "account_number_confirm")
  private WebElement confirmAccountNumber;

  @FindBy(id = "submit_bank_acct")
  private WebElement continueButton;

  @FindBy(id = "routing_number_help_icon")
  private WebElement routingNumberHelpIcon;

  @FindBy(id = "account_number_help_icon")
  private WebElement accountNumberHelpIcon;

  @FindBy(css = "#pop_rout > img")
  private WebElement routingNumberImage;

  @FindBy(css = "#pop_acc > img")
  private WebElement accountNumberImage;

  @FindBy(className = "help_btn")
  private WebElement faqLink;

  @FindBy(className = "confirm_edit_btn")
  private WebElement yesButtonConfirmNavigation;

  @FindBy(name = "submit_amount")
  private WebElement previousButton;

  private String accountTypePrep;
  private String accountNumberPrep;

  /**
   * Bank Account Details Page object.
   */
  public BankAccountDetailsPage() {
    super();
    faker = new Faker();
    accountTypePrep = "";
    accountNumberPrep = "";
  }

  // ********************************************Action*********************************************

  public void prepAccountType(String accountType) {
    accountTypePrep = accountType;
  }

  public void prepAccountNumber(String accountNumber) {
    accountNumberPrep = accountNumber;
  }

  /**
   * Fill bank details and submit.
   *
   */
  public void fillBankDetailsAndSubmit() {
    fillBankDetails();
    clickContinueButton();
  }

  private void fillBankDetails() {
    setNameOnAccount();
    setBankName(BANK_NAME);
    if (accountTypePrep.isEmpty()) {
      selectAccountType();
    } else {
      selectAccountType(accountTypePrep);
    }
    setRoutingNumber(ROUTING_NUMBER);
    if (accountNumberPrep.isEmpty()) {
      setAndConfirmBankAccountNumber();
    } else {
      setAndConfirmBankAccountNumber(accountNumberPrep);
    }
  }

  /**
   * Fill bank details (excluding account number and confirm account number) and submit.
   *
   */
  public void fillAndSubmitWithoutAccAndConfAccNo() {
    setNameOnAccount();
    setBankName(BANK_NAME);
    selectAccountType();
    setRoutingNumber(ROUTING_NUMBER);

    continueButton.click();

    Logger.trace("Fill and submit excluding account number and confirm account number fields");
  }

  private void setNameOnAccount() {
    highlight(nameOnAccount);

    String fullName = faker.name().fullName();

    nameOnAccount.clear();
    nameOnAccount.sendKeys(fullName);

    Logger.trace("Full Name entered as: " + fullName);
  }

  private void setBankName(String bank) {
    highlight(bankName);
    bankName.clear();
    bankName.sendKeys(bank);
  }

  private void selectAccountType() {
    List<String> accountTypes = new ArrayList<>();
    accountTypes.add(CHECKING_ACCOUNT);
    accountTypes.add(SAVINGS_ACCOUNT);

    String randomAccountType = accountTypes.get(new Random().nextInt(accountTypes.size()));
    selectAccountType(randomAccountType);
  }

  private void selectAccountType(String accType) {
    Logger.trace("Select account type " + accType);

    highlight(accountType);

    Select selectType = new Select(accountType);

    switch (accType) {
      case CHECKING_ACCOUNT:
        selectType.selectByValue("1");
        break;
      case SAVINGS_ACCOUNT:
        selectType.selectByValue("2");
        break;
      default:
        // not supported
    }
  }

  private void setRoutingNumber(String routing) {
    highlight(routingNumber);
    routingNumber.clear();
    routingNumber.sendKeys(routing);
  }

  /**
   * Enter the given bank account number in both the number and confirm fields.
   *
   * @param newAccountNo number to enter
   */
  private void setAndConfirmBankAccountNumber(String newAccountNo) {
    setAccountNumber(newAccountNo);

    setConfirmBankAccountNumber(newAccountNo);
  }

  private void setAccountNumber(String newAccountNo) {
    Logger.trace("Set account number as " + newAccountNo);

    highlight(accountNumber);
    accountNumber.clear();
    accountNumber.sendKeys(newAccountNo);
  }

  private void setConfirmBankAccountNumber(String newAccountNo) {
    Logger.trace("Set confirm account number as " + newAccountNo);

    highlight(confirmAccountNumber);
    confirmAccountNumber.clear();
    confirmAccountNumber.sendKeys(newAccountNo);
  }

  /**
   * Fill Account & Confirm Account fields with faker generated valid information for given method.
   *
   */
  private void setAndConfirmBankAccountNumber() {
    setAndConfirmBankAccountNumber(generateAccountNumber());
  }

  private String generateAccountNumber() {
    DataHelper dataHelper = new DataHelper();

    return Integer.toString(dataHelper.getAccountNumber());
  }

  /**
   * Click on continue button and wait for next page to load.
   *
   */
  protected void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);

    Logger.trace("Clicked Continue button on Bank Account Details page");
  }

  /**
   * Determine if page is loaded.
   *
   * @return true if the routing number field is present
   */
  public boolean pageIsLoaded() {
    return !driver.findElements(By.id("routing_number")).isEmpty();
  }

  /**
   * Fill bank details page excluding the given box name. Uses WebElement click since page is not
   * reloaded.
   *
   * @param exclude box to be excluded when filling bank details page.
   */
  public void fillExcludeAndSubmitBank(String exclude) {

    if (exclude.equals(NAME_ON_ACCOUNT_BOX)) {
      Logger.trace("Name on account excluded");
    } else {
      setNameOnAccount();
    }

    if (exclude.equals(BANK_NAME_BOX)) {
      Logger.trace("Bank name excluded");
    } else {
      setBankName(BANK_NAME);
    }

    if (exclude.equals(ACCOUNT_TYPE_SELECT)) {
      Logger.trace("Account type excluded");
    } else {
      selectAccountType();
    }

    if (exclude.equals(ROUTING_NUMBER_BOX)) {
      Logger.trace("Routing number excluded");
    } else {
      setRoutingNumber(ROUTING_NUMBER);
    }

    String accountNumber = generateAccountNumber();

    setAccountNumber(accountNumber);

    if (exclude.equals(CONFIRM_ACCOUNT_NUMBER_BOX)) {
      Logger.trace("Confirm account number excluded");
    } else {
      setConfirmBankAccountNumber(accountNumber);
    }

    continueButton.click();
  }

  /**
   * Get error message from name on account box.
   *
   * @return nameOnAccountErrorMessage
   */
  public String getErrorNameOnAccount() {
    return getTextBySelector(By.cssSelector("label.error[for='name']"));
  }

  /**
   * Get error message from bank name box.
   *
   * @return bankNameErrorMessage
   */
  public String getErrorBankName() {
    return getTextBySelector(By.cssSelector("label.error[for='bank_name']"));
  }

  /**
   * Get error message from account type box.
   *
   * @return accountTypeErrorMessage
   */
  public String getErrorAccountType() {
    return getTextBySelector(By.cssSelector("label.error[for='account_type']"));
  }

  /**
   * Get error message from routing number box.
   *
   * @return routingNumberErrorMessage
   */
  public String getErrorRoutingNumber() {
    return getTextBySelector(By.cssSelector("label.error[for='routing_number']"));
  }

  /**
   * Get error message from account number box.
   *
   * @return accountNumberErrorMessage
   */
  public String getErrorAccountNumber() {
    return getTextBySelector(By.cssSelector("label.error[for='account_number']"));
  }

  /**
   * Get error message from confirmAccountNumber box.
   *
   * @return confirmAccountNumberErrorMessage
   */
  public String getErrorConfirmAccountNumber() {
    return getTextBySelector(By.cssSelector("label.error[for='account_number_confirm']"));
  }

  public void mouseHoverRoutingHelp() {
    mouseHover(routingNumberHelpIcon);
  }

  public void mouseHoverAccountHelp() {
    mouseHover(accountNumberHelpIcon);
  }

  /**
   * Find if routing number image is displayed and url is present.
   *
   * @return boolean value
   */
  public Boolean isRoutingNumberImageDisplayed() {
    String routingNumberImageUrl = routingNumberImage.getAttribute("src");

    return routingNumberImage.isDisplayed() && routingNumberImageUrl
        .equals(BASE_URL + "assets/images/shared/routingPop.gif");
  }

  /**
   * Find if account number image is displayed and url is present.
   *
   * @return boolean value
   */
  public Boolean isAccountNumberImageDisplayed() {
    String accountNumberImageUrl = accountNumberImage.getAttribute("src");

    return accountNumberImage.isDisplayed() && accountNumberImageUrl
        .equals(BASE_URL + "assets/images/shared/accountPop.gif");
  }

  /**
   * Fill bank details with selected fields having less than the minimum number of characters. Uses
   * clickContinueButton since page is reloaded
   *
   * @param tooShortField field to set with less than minimum number of characters
   * @param minChars minimum number of characters ex. if field must have 2 to 60 chars then the
   * value of minChars is 2
   */
  public void fillAndSubmitLessThanMinimumCharacters(String tooShortField, int minChars) {
    DataHelper helper = new DataHelper();
    String randomStr = helper.generateAlphanumericString(minChars - 1);

    if (tooShortField.equals(NAME_ON_ACCOUNT_BOX)) {
      highlight(nameOnAccount);

      nameOnAccount.clear();
      nameOnAccount.sendKeys(randomStr);

      Logger.trace("Set Name on Account with one character");
    } else {
      setNameOnAccount();
    }

    if (tooShortField.equals(BANK_NAME_BOX)) {
      highlight(bankName);

      bankName.clear();
      bankName.sendKeys(randomStr);
      Logger.trace("Set Bank Name with one character");
    } else {
      setBankName(BANK_NAME);
    }

    selectAccountType();

    setRoutingNumber(ROUTING_NUMBER);

    setAndConfirmBankAccountNumber();

    clickContinueButton();
  }

  /**
   * Fill bank details with invalid routing number. Uses WebElement click since page is not
   * reloaded.
   *
   */
  public void fillAndSubmitWithInvalidRoutingNumber() {
    fillBankDetails();

    setRoutingNumber(INVALID_ROUTING_NUMBER);

    clickContinueButton();
  }

  /**
   * Leave all bank details blank and submit. Uses WebElement click since page is not reloaded.
   *
   */
  public void submitAllBankDetailsBlank() {
    nameOnAccount.clear();
    bankName.clear();

    Select selectType = new Select(accountType);
    selectType.selectByVisibleText("-- select --");

    routingNumber.clear();
    accountNumber.clear();
    confirmAccountNumber.clear();

    continueButton.click();

  }

  public String getFootnoteText() {
    return getTextBySelector(By.className("footnote"));
  }

  /**
   * Click the faq link and return the url of the faq page that opens in a new window.
   *
   * @return current url
   */
  public String getFaqUrl() {
    highlight(faqLink);
    faqLink.click();

    Logger.trace("Clicked on Faq link");

    WebDriver driver = DriverManager.getDriver();

    Object[] handles;

    handles = driver.getWindowHandles().toArray();

    if (handles.length == 2) {
      driver.switchTo().window(handles[1].toString());
    }

    return getCurrentPageUrl();
  }

  /**
   * Click 'Yes' on Confirm Navigation dialog box.
   *
   */
  public void clickYesConfirmNavigation() {
    highlight(yesButtonConfirmNavigation);
    clickAndWait(yesButtonConfirmNavigation);

    Logger.trace("Clicked on Yes button of dialog box.");
  }

  /**
   * Fill Account and Confirm Account fields with faker generated valid information that do not
   * match.
   *
   */
  public void setAndConfirmBankAccountNumberNotMatching() {
    setAccountNumber(generateAccountNumber());
    setConfirmBankAccountNumber(generateAccountNumber());
  }

  /**
   * Click on Previous button.
   *
   */
  public void clickPreviousButton() {
    highlight(previousButton);
    previousButton.click();

    Logger.trace("Clicked Previous button on Bank Account Details page");
  }

  /**
   * Get error message from name on account box when minimum character requirement is not met.
   *
   * @return nameOnAccountErrorMessage
   */
  public String getErrorNameOnAccountCharsRequirement() {
    return getTextBySelector(By.cssSelector("label.error[for='bank_validate']"));
  }

}
