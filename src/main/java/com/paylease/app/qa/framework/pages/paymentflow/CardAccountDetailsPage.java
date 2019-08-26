package com.paylease.app.qa.framework.pages.paymentflow;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CardAccountDetailsPage extends PageBase {

  public static final String CARD_TYPE_CREDIT_VALID = "VALID_CREDIT";
  public static final String CARD_TYPE_DEBIT_VALID = "VALID_DEBIT";
  public static final String CARD_TYPE_CREDIT_VALID_VISA_LITLE = "CREDIT_CARD_VISA_LITLE";
  public static final String CARD_TYPE_CREDIT_VALID_MC_LITLE = "CREDIT_CARD_MC_LITLE";
  public static final String CARD_TYPE_CREDIT_VALID_DISCOVER_LITLE = "CREDIT_CARD_DISCOVER_LITLE";
  public static final String CARD_TYPE_CREDIT_VALID_AMEX_LITLE = "CREDIT_CARD_AMEX_LITLE";
  public static final String CARD_TYPE_CREDIT_VALID_VISA_CHASE = "CREDIT_CARD_VISA_CHASE";
  public static final String CARD_TYPE_CREDIT_VALID_MC_CHASE = "CREDIT_CARD_MC_CHASE";
  public static final String CARD_TYPE_CREDIT_VALID_DISCOVER_CHASE = "CREDIT_CARD_DISCOVER_CHASE";
  public static final String VALID_CREDIT = "4457010000000009";
  public static final String VALID_DEBIT = "4373330010000000";

  public static final String CARD_NUMBER_BOX = "Card Number";
  public static final String CVV2_BOX = "CVV2";
  public static final String EXPIRATION_MONTH_SELECT = "Expiration month";
  public static final String EXPIRATION_YEAR_SELECT = "Expiration year";
  public static final String FIRST_NAME_BOX = "First name";
  public static final String LAST_NAME_BOX = "Last name";
  public static final String BILLING_ADDRESS_BOX = "Billing address";
  public static final String BILLING_CITY_BOX = "Billing city";
  public static final String BILLING_STATE_SELECT = "Billing state";
  public static final String BILLING_ZIP_BOX = "Billing zip";

  private static final String CREDIT_CARD_VISA_LITLE = "4457010000000009";
  private static final String CREDIT_CARD_MC_LITLE = "5112000100000003";
  private static final String CREDIT_CARD_DISCOVER_LITLE = "6011000995500000";
  private static final String CREDIT_CARD_AMEX_LITLE = "375001000000005";
  private static final String CREDIT_CARD_VISA_CHASE = "4444444444444448";
  private static final String CREDIT_CARD_MC_CHASE = "5454545454545454";
  private static final String CREDIT_CARD_DISCOVER_CHASE = "6011000995500000";
  private static final String VALID_ZIP = "92123";

  private static final String CVV = "123";
  private static final String STATE = "CA";

  private Faker faker;

  @FindBy(id = "cc_number_cc_discovery_master")
  private WebElement creditDebitCardNo;

  @FindBy(id = "cc_sec_code_cc_discovery_master")
  private WebElement cvv2;

  @FindBy(id = "cc_exp_mo_cc_discovery_master")
  private WebElement expirationMonth;

  @FindBy(id = "cc_exp_yy_cc_discovery_master")
  private WebElement expirationYear;

  @FindBy(id = "cc_fname_cc_discovery_master")
  private WebElement firstNameField;

  @FindBy(id = "cc_lname_cc_discovery_master")
  private WebElement lastNameField;

  @FindBy(id = "cc_country_cc_discovery_master")
  private WebElement billingCountryField;

  @FindBy(id = "cc_address_cc_discovery_master")
  private WebElement billingAddressField;

  @FindBy(id = "cc_city_cc_discovery_master")
  private WebElement billingCityField;

  @FindBy(id = "cc_state_cc_discovery_master")
  private WebElement billingState;

  @FindBy(id = "cc_postal_code_cc_discovery_master")
  private WebElement zipField;

  @FindBy(name = "add_account")
  private WebElement continueButton;

  @FindBy(className = "jconfirm-box")
  private WebElement confirmCardTypeText;

  @FindBy(id = "cvv2_help_icon")
  private WebElement cvv2HelpIcon;

  @FindBy(css = "#pop_cvv2 > img")
  private WebElement cvv2Image;

  @FindBy(id = "read_more_btn")
  private WebElement readMoreBtn;

  @FindBy(id = "second_disclosure")
  private WebElement secondDisclosure;

  private String cardNumberPrep;
  private String cardTypePrep;
  private String zipCodePrep;

  /**
   * Card Account Details Page object.
   */
  public CardAccountDetailsPage() {
    super();
    faker = new Faker();
    cardNumberPrep = "";
    cardTypePrep = "";
    zipCodePrep = "";
  }

  // ********************************************Action*********************************************

  /**
   * Prepare a card number to be used when filling in form.
   *
   * @param cardNumber Card number to use
   */
  public void prepCardNumber(String cardNumber) {
    cardNumberPrep = cardNumber;
  }

  /**
   * Prepare a card type to be used when filling in form.
   *
   * @param cardType Card type
   */
  public void prepCardType(String cardType) {
    cardTypePrep = cardType;
  }

  private void prepZipCode(String zipCode) {
    zipCodePrep = zipCode;
  }

  /**
   * Fill all details with default or prepared values and submit.
   */
  public void fillAndSubmitCardDetails() {
    fillCardDetails();
    clickContinueButton();
  }

  /**
   * Fill all details along with default card number and prepared values and submit.
   */
  public void fillAndSubmitCardDetailsWithCardNum() {
    prepCardNumber(VALID_CREDIT);
    fillAndSubmitCardDetails();
  }

  /**
   * Fill all details along with given card number and prepared values and submit.
   */
  public void fillAndSubmitCardDetailsWithCardNum(String cardNumber) {
    prepCardNumber(cardNumber);
    fillAndSubmitCardDetails();
  }

  /**
   * Fill all details with default or prepared values except zip code.
   *
   * @param invalidZip invalid zip code
   */
  public void fillCardDetailsWithInvalidZipAndSubmit(String invalidZip) {
    prepZipCode(invalidZip);
    fillCardDetails();
    continueButton.click();
  }

  /**
   * Fill all details along with given card number and prepared values and submit.
   *
   * @param cardNumber valid card number
   */
  public void fillAndSubmitCardDetailsWithNo(String cardNumber) {
    setCardNumber(chooseCardNumber(cardNumber));
    setCvv();
    Date expirationDate = getExpirationDate();
    selectExpirationMonth(expirationDate);
    selectExpirationYear(expirationDate);
    enterFirstName();
    enterLastName();
    enterStreetAddress();
    enterCity();
    selectState(STATE);
    setZipCode(VALID_ZIP);

    continueButton.click();
  }

  /**
   * Fill all details with default or prepared values.
   */
  public void fillCardDetails() {
    setCardNumber();
    setCvv();
    Date expirationDate = getExpirationDate();
    selectExpirationMonth(expirationDate);
    selectExpirationYear(expirationDate);
    enterFirstName();
    enterLastName();
    enterStreetAddress();
    enterCity();
    selectState(STATE);
    setZipCode();
  }

  /**
   * Fill all details with default or prepared values for a Non-US country.
   */
  public void fillCardDetailsWithNonUsCountryAndSubmit() {
    setCardNumber();
    setCvv();
    Date expirationDate = getExpirationDate();
    selectExpirationMonth(expirationDate);
    selectExpirationYear(expirationDate);
    enterFirstName();
    enterLastName();
    selectBillingCountry();
    enterStreetAddress();
    enterCity();

    continueButton.click();
  }

  private String chooseCardNumber(String paymentMethod) {
    String cardNumber = "";
    switch (paymentMethod) {
      case CARD_TYPE_CREDIT_VALID:
        cardNumber = VALID_CREDIT;
        break;
      case CARD_TYPE_DEBIT_VALID:
        cardNumber = VALID_DEBIT;
        break;
      case CARD_TYPE_CREDIT_VALID_VISA_LITLE:
        cardNumber = CREDIT_CARD_VISA_LITLE;
        break;
      case CARD_TYPE_CREDIT_VALID_MC_LITLE:
        cardNumber = CREDIT_CARD_MC_LITLE;
        break;
      case CARD_TYPE_CREDIT_VALID_DISCOVER_LITLE:
        cardNumber = CREDIT_CARD_DISCOVER_LITLE;
        break;
      case CARD_TYPE_CREDIT_VALID_AMEX_LITLE:
        cardNumber = CREDIT_CARD_AMEX_LITLE;
        break;
      case CARD_TYPE_CREDIT_VALID_VISA_CHASE:
        cardNumber = CREDIT_CARD_VISA_CHASE;
        break;
      case CARD_TYPE_CREDIT_VALID_MC_CHASE:
        cardNumber = CREDIT_CARD_MC_CHASE;
        break;
      case CARD_TYPE_CREDIT_VALID_DISCOVER_CHASE:
        cardNumber = CREDIT_CARD_DISCOVER_CHASE;
        break;
      default:
        // not supported
    }
    return cardNumber;
  }

  private void setCardNumber() {
    String cardNumber = "";

    if (!cardTypePrep.isEmpty()) {
      cardNumber = chooseCardNumber(cardTypePrep);
    }
    if (!cardNumberPrep.isEmpty()) {
      cardNumber = cardNumberPrep;
    }

    setCardNumber(cardNumber);
  }

  private void setCardNumber(String cardNumber) {
    highlight(creditDebitCardNo);
    creditDebitCardNo.click();
    creditDebitCardNo.clear();

    creditDebitCardNo.sendKeys(cardNumber);
  }

  private void setCvv() {
    highlight(cvv2);
    cvv2.click();
    cvv2.clear();
    cvv2.sendKeys(CVV);
  }

  private void selectExpirationMonth(Date expirationDate) {
    highlight(expirationMonth);

    Select selectExpMonth = new Select(expirationMonth);
    SimpleDateFormat twoDigitMonth = new SimpleDateFormat("MM");
    String expMonth = twoDigitMonth.format(expirationDate);

    selectExpMonth.selectByValue(expMonth);
  }

  private void selectExpirationYear(Date expirationDate) {
    highlight(expirationYear);

    Select selectExpYear = new Select(expirationYear);
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy");
    String expYear = formatDate.format(expirationDate);

    selectExpYear.selectByValue(expYear);
  }

  private void enterFirstName() {
    String firstName = faker.name().firstName();

    highlight(firstNameField);
    firstNameField.click();

    firstNameField.sendKeys(firstName);

    Logger.trace("First Name entered as: " + firstName);
  }

  private void enterLastName() {
    String lastName = faker.name().lastName();

    highlight(lastNameField);
    lastNameField.click();

    lastNameField.sendKeys(lastName);

    Logger.trace("Last Name entered as: " + lastName);
  }

  private void selectBillingCountry() {
    highlight(billingCountryField);

    Select selectCountry = new Select(billingCountryField);

    String country = faker.address().countryCode();

    if (country.equals("US")) {
      selectCountry.selectByValue("CA");
    } else {
      selectCountry.selectByValue(country);
    }
  }

  private void enterStreetAddress() {
    //String streetAddress = faker.address().streetAddress();

    //Hard coding address according to ticket
    // AUTO-292-hard-code-address-in-cc-details-page-and-related-gapi
    String streetAddress = "4 Main St.";

    highlight(billingAddressField);
    billingAddressField.click();

    billingAddressField.sendKeys(streetAddress);

    Logger.trace("Billing Address entered as: " + streetAddress);
  }

  private void enterCity() {
    //String cityIn = faker.address().city();

    //Hard coding address according to ticket
    // AUTO-292-hard-code-address-in-cc-details-page-and-related-gapi
    String cityIn = "Laurel";

    highlight(billingCityField);
    billingCityField.click();

    billingCityField.sendKeys(cityIn);

    Logger.trace("City entered as: " + cityIn);
  }

  private void selectState(String state) {
    highlight(billingState);
    Select chooseStateSelectObj = new Select(billingState);
    chooseStateSelectObj.selectByVisibleText(state);

    Logger.trace("State selected: " + state);
  }

  private void setZipCode() {
    String zipCode = VALID_ZIP;

    if (!zipCodePrep.isEmpty()) {
      zipCode = zipCodePrep;
    }

    setZipCode(zipCode);
  }

  private void setZipCode(String zipCode) {
    highlight(zipField);
    zipField.click();
    zipField.clear();
    zipField.sendKeys(zipCode);

    Logger.trace("Zip Code entered as: " + zipCode);
  }

  /**
   * Click on continue button and wait for next page to load.
   */
  protected void clickContinueButton() {
    Logger.trace("Clicked Continue button on Card Account Details page");

    highlight(continueButton);
    clickAndWait(continueButton);
  }

  /**
   * Click on continue button.
   */
  public void clickContinueButtonNoWait() {
    Logger.trace("Clicked Continue button on Card Account Details page");

    highlight(continueButton);
    continueButton.click();
  }

  /**
   * Click continue and wait for the Credit/Debit Fee Warning message to appear.
   */
  public void clickContinueButtonPopUp() {
    Logger.trace("Clicked Continue button on Card Account Details page");

    highlight(continueButton);
    continueButton.click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jconfirm-box")));
  }

  public String getConfirmCardTypeText() {

    return confirmCardTypeText.getText();
  }

  private Date getExpirationDate() {
    Date expirationDate = faker.date().future(365, TimeUnit.DAYS);
    Logger.trace("Using expiration date: " + expirationDate);

    return expirationDate;
  }

  /**
   * Determine if page is loaded.
   *
   * @return true if the routing number field is present
   */
  public boolean pageIsLoaded() {
    return !driver.findElements(By.id("cc_number_cc_discovery_master")).isEmpty();
  }

  /**
   * Fill CC details page excluding the given box name.
   *
   * @param exclude box to be excluded when filling CC details page.
   */
  public void fillExcludeAndSubmitCard(String exclude) {

    if (exclude.equals(CARD_NUMBER_BOX)) {
      Logger.info("Card number excluded");
    } else {
      setCardNumber(VALID_CREDIT);
    }

    if (exclude.equals(CVV2_BOX)) {
      Logger.info("Cvv2 excluded");
    } else {
      setCvv();
    }

    Date expirationDate = getExpirationDate();

    if (exclude.equals(EXPIRATION_MONTH_SELECT)) {
      Logger.trace("Expiration month excluded");
    } else {
      selectExpirationMonth(expirationDate);
    }

    if (exclude.equals(EXPIRATION_YEAR_SELECT)) {
      Logger.trace("Expiration year excluded");
    } else {
      selectExpirationYear(expirationDate);
    }

    if (exclude.equals(FIRST_NAME_BOX)) {
      Logger.trace("First name excluded");
    } else {
      enterFirstName();
    }

    if (exclude.equals(LAST_NAME_BOX)) {
      Logger.trace("Last name excluded");
    } else {
      enterLastName();
    }

    if (exclude.equals(BILLING_ADDRESS_BOX)) {
      Logger.trace("Billing address excluded");
    } else {
      enterStreetAddress();
    }

    if (exclude.equals(BILLING_CITY_BOX)) {
      Logger.trace("Billing city excluded");
    } else {
      enterCity();
    }

    if (exclude.equals(BILLING_STATE_SELECT)) {
      Logger.trace("Billing state excluded");
    } else {
      selectState("MD");
    }

    if (exclude.equals(BILLING_ZIP_BOX)) {
      Logger.trace("Billing zip excluded");
    } else {
      setZipCode(VALID_ZIP);
    }

    continueButton.click();
  }

  /**
   * Get card number error message.
   *
   * @return error message
   */
  public String getCardNumberErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_number_cc_discovery_master']"));
  }

  /**
   * Get cvv2 error message.
   *
   * @return cvv2 error message
   */
  public String getCvv2ErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_sec_code_cc_discovery_master']"));
  }

  /**
   * Get expiration month error message.
   *
   * @return expiration month error message.
   */
  public String getExpirationMonthErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_exp_mo_cc_discovery_master']"));
  }

  /**
   * Get expiration year error message.
   *
   * @return expiration year error message
   */
  public String getExpirationYearErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_exp_yy_cc_discovery_master']"));
  }

  /**
   * Get first name error message.
   *
   * @return first name error message.
   */
  public String getFirstNameErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_fname_cc_discovery_master']"));
  }

  /**
   * Get last name error message.
   *
   * @return last name error message
   */
  public String getLastNameErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_lname_cc_discovery_master']"));
  }

  /**
   * Get billing address error message.
   *
   * @return billing address error message
   */
  public String getBillingAddressErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_address_cc_discovery_master']"));
  }

  /**
   * Get billing city error message.
   *
   * @return billing city error message
   */
  public String getBillingCityErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_city_cc_discovery_master']"));
  }

  /**
   * Get billing state error message.
   *
   * @return billing state error message
   */
  public String getBillingStateErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='cc_state_cc_discovery_master']"));
  }

  /**
   * Get zip code error message.
   *
   * @return zip code error message
   */
  public String getZipErrorMessage() {
    return getTextBySelector(
        By.cssSelector("label.error[for='cc_postal_code_cc_discovery_master']"));
  }

  public void mouseHoverCvv2Help() {
    mouseHover(cvv2HelpIcon);
  }

  /**
   * Find if cvv2 image is displayed and url is present.
   *
   * @return boolean value
   */
  public Boolean isCvv2ImageDisplayed() {
    String cvv2Url = cvv2Image.getAttribute("src");

    return cvv2Image.isDisplayed() && cvv2Url.equals(BASE_URL + "assets/images/shared/cvv2Pop.png");
  }

  public boolean isAmexLogoPresent() {
    return !driver.findElements(By.cssSelector("span.amex_logo_sm")).isEmpty();
  }

  /**
   * Get visa disclosure message.
   *
   * @return disclosure text
   */
  public String getDisclosureMessage() {
    return getTextBySelector(By.id("visa_disclosure"));
  }

  /**
   * Click read more on visa disclosure.
   *
   */
  public void clickDisclosureReadMoreButton() {
    readMoreBtn.click();
  }

  /**
   * Check if full text of visa disclosure message is visible.
   *
   * @return boolean
   */
  public boolean isSecondDisclosureMessageVisible() {
    return secondDisclosure.isDisplayed();
  }

  public String getSecondDislosureMessage() {
    return getTextBySelector(By.id("second_disclosure"));
  }

  /**
   * Get server-side error messages for card number.
   *
   * @return String
   */
  public String getCardNumberValidationError() {
    return getTextBySelector(By.className("error_server"));
  }
}
