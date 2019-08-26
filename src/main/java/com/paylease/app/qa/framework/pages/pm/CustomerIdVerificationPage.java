package com.paylease.app.qa.framework.pages.pm;

import com.github.javafaker.Faker;
import com.google.common.collect.ImmutableMap;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class CustomerIdVerificationPage extends PageBase {

  private static final String URL = BASE_URL + "pm/dashboard/customer_id_verification";

  public static final String TAB_NAME_1 = "Legal Entity";
  public static final String TAB_2_CONTROL_PERSON = "Control Person";
  public static final String TAB_3_BENEFICIAL_OWNER = "Beneficial Owner";
  public static final String TAB_NAME_4 = "Beneficial Owner";
  public static final String TAB_NAME_5 = "Beneficial Owner";
  public static final String TAB_NAME_6 = "Beneficial Owner";
  public static final String PRIVATE_COMPANY_TYPE = "PRIVATE";
  public static final String PUBLIC_COMPANY_TYPE = "PUBLIC";
  public static final String GIACT_ENV_FILE = "giact.env";
  public static final String CI11_PASS_RESPONSE = "resp=PASSnoCRC.xml";

  private static final String ERROR_MESSAGE_OFFICER_PERCENT = " Officer Percent Ownership must be between 25 and 100";
  private static final String MESSAGE_KYC_WELCOME_1 = "Welcome to PayLease.com!";
  private static final String MESSAGE_KYC_WELCOME_2 = "The following pages will guide you through our due diligence process to fulfill PayLease's Know-Your-Customer requirements. Your company's Legal Entity information has already been collected and input by your PayLease Representative for you.";
  private static final String MESSAGE_KYC_WELCOME_3 = "Collection of this information is required to comply with federally mandated Customer Due Diligence requirements for financial institutions.";
  private static final String MESSAGE_KYC_WELCOME_4 = "Thank you for using PayLease!";

  private static final Map<Integer, String> TAB_NAMES = ImmutableMap.of(

      2, "Primary",
      3, "Secondary",
      4, "Tertiary",
      5, "Quaternary",
      6, "Quinary"
  );

  public enum ElementType {
    OFFICER_PERCENT("officer_percent"),
    OFFICER_FIRST_NAME("officer_fname"),
    OFFICER_LAST_NAME("officer_lname"),
    OFFICER_TITLE("officer_title"),
    OFFICER_EMAIL("officer_email"),
    OFFICER_ADDRESS("officer_home_address"),
    OFFICER_CITY("officer_city"),
    OFFICER_STATE("officer_state"),
    OFFICER_ZIP("officer_zip"),
    OFFICER_LICENSE("officer_dl"),
    OFFICER_LICENSE_STATE("officer_dl_state"),
    OFFICER_DOB("officer_dob"),
    OFFICER_SSN("officer_ssn"),
    LEGAL_ENTITY_TYPE("saved_legal_entity_type"),
    FORMATION_DATE("date_co_formed"),
    BUSINESS_NAME("legal_bus_name"),
    TAX_ID("legal_bus_tax_id"),
    LEGAL_BUSINESS_ADDRESS("legal_bus_address"),
    LEGAL_BUSINESS_CITY("legal_bus_city"),
    LEGAL_BUSINESS_STATE("legal_bus_state"),
    LEGAL_BUSINESS_ZIP("legal_bus_zip"),
    LEGAL_BUSINESS_COUNTRY("legal_bus_country"),
    ENTITY_PARTIAL_SAVE_BUTTON("entity_partial_save"),
    LEGAL_ENTITY_PRIVATE_PUBLIC_DROPDOWN("legal_entity_ownership_type");

    private final String name;

    ElementType(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }


  @FindBy(id = "date_co_formed")
  private WebElement formationDate;

  private Faker faker;

  /**
   * Initialize faker and current tab.
   */
  public CustomerIdVerificationPage() {
    super();

    faker = new Faker();
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Determine if page is loaded.
   *
   * @return true if the page title = "Customer Identity Verification"
   */
  public boolean pageIsLoaded() {
    WebElement pageTitle = driver.findElement(By.className("page_title"));
    String titleText = pageTitle.getText();

    return titleText.equalsIgnoreCase("Customer Identity Verification");
  }

  /**
   * click the submit button.
   */
  public void clickSubmit() {
    WebElement btn = driver.findElement(By.id("Primary_req_submit"));
    highlight(btn);
    btn.click();
  }

  public void clickMultiSubmit() {
    String btnId = TAB_NAMES.get(getCurrentTab()) + "_multi_submit";
    WebElement btn = driver.findElement(By.id(btnId));
    highlight(btn);
    btn.click();
  }

  public void clickSummarySubmit() {
    WebElement btn = driver.findElement(By.id("summary_submit"));
    highlight(btn);
    clickAndWaitForAjax(btn);
  }

  public void clickSummarySubmitAndWait() {
    WebElement btn = driver.findElement(By.id("summary_submit"));
    highlight(btn);
    clickAndWait(btn);
  }

  /**
   * Navigates to next tab.
   *
   * @return Boolean if exception caught.
   */
  public boolean doContinueToNextTab() {
    WebElement currentTab = getCurrentTabBody();
    clickNextButton();
    return waitForTabSwitch(currentTab);
  }

  /**
   * Click the next button.
   */
  public void clickNextButton() {
    String btnId;
    if (getCurrentTab() == 1) {
      btnId = "entity_continue";
    } else {
      btnId = TAB_NAMES.get(getCurrentTab()) + "_multi_next";
    }
    WebElement btn = driver.findElement(By.id(btnId));
    wait.until(ExpectedConditions.elementToBeClickable(btn));
    highlight(btn);
    btn.click();
  }

  /**
   * Helper method to consolidate navigating and filling forms for given number of tabs.
   *
   * @param nTabs
   */
  public void fillGivenNumberOfOfficerTabs(int nTabs) {
    for (int i = 0; i < nTabs; i++) {
      doContinueToNextTab();
      fillOfficerForm();
      addAdditionalOfficerForCurrentTab();
    }
  }

  public int getCurrentTab() {
    WebElement activeTab = getCurrentTabBody();
    return Integer.parseInt(activeTab.getAttribute("id").replace("tab-", ""));
  }

  private WebElement getCurrentTabBody() {
    return driver.findElement(By.cssSelector(".tab_content.current"));
  }

  /**
   * Navigates to previous tab.
   */
  public void goToPreviousTab() {
    WebElement currentTab = getCurrentTabBody();
    String previousBtnId = TAB_NAMES.get(getCurrentTab()) + "_prev";
    Logger.info(previousBtnId);
    WebElement previousBtn = wait
        .until(ExpectedConditions.elementToBeClickable(By.id(previousBtnId)));
    highlight(previousBtn);
    previousBtn.click();
    waitForTabSwitch(currentTab);
  }

  public String getCurrentActiveTabName() {
    return getTextBySelector(By.cssSelector(".tab-link.current"));
  }

  /**
   * Click on the next owner for partnership entity type.
   */
  public void clickPartnerNextOwner() {
    String nextPartnerBtnId = TAB_NAMES.get(getCurrentTab()) + "_next";
    wait.until(ExpectedConditions.elementToBeClickable(By.id(nextPartnerBtnId)));
    WebElement partnerNextOwnerBtn = driver.findElement(By.id(nextPartnerBtnId));
    highlight(partnerNextOwnerBtn);
    partnerNextOwnerBtn.click();
  }

  /**
   * Click on the save and log off button.
   */
  public void clickSaveAndLogOff() {
    int currentTab = getCurrentTab();
    String partialSaveId;

    if (currentTab == 1) {
      partialSaveId = ElementType.ENTITY_PARTIAL_SAVE_BUTTON.getName();
    } else {
      partialSaveId = TAB_NAMES.get(getCurrentTab()) + "_partial_save";
    }

    WebElement saveAndLogOffBtn = driver.findElement(By.id(partialSaveId));
    highlight(saveAndLogOffBtn);
    List<WebElement> errorMessages = driver.findElements(By.className("error_frontend"));
    if (errorMessages.isEmpty()) {
      clickAndWait(saveAndLogOffBtn);
    } else {
      saveAndLogOffBtn.click();
    }
  }

  public boolean isNextOwnerButtonDisplayed() {
    String nextOwnerBtn = TAB_NAMES.get(getCurrentTab()) + "_multi_next";
    return driver.findElement(By.id(nextOwnerBtn)).isDisplayed();
  }

  /**
   * Checks if new beneficiary can be added for Sole Proprietorship.
   *
   * @return if the next owner button is not present.
   */
  public boolean isNextOwnerSolePropPresent() {
    String nextOwnerBtn = TAB_NAMES.get(getCurrentTab()) + "_multi_next";
    return isElementPresentBySelector(By.id(nextOwnerBtn));
  }

  /**
   * Checks if new Owner button is Present.
   *
   * @return if the next owner button is not present.
   */
  public boolean isNextOwnerPartnershipDisplayed() {
    String nextPartnerBtnId = TAB_NAMES.get(getCurrentTab()) + "_multi_next";
    return driver.findElement(By.id(nextPartnerBtnId))
        .isDisplayed();
  }

  /**
   * check to see if next owner button is displayed.
   */
  public boolean isNextOwnerDisplayed() {
    String nxtPartnerBtnId = TAB_NAMES.get(getCurrentTab()) + "_next";
    return driver.findElement(By.id(nxtPartnerBtnId))
        .isDisplayed();
  }

  public boolean isSubmitButtonDisplayed() {
    String submitBtn = TAB_NAMES.get(getCurrentTab()) + "_multi_submit";
    return driver.findElement(By.id(submitBtn)).isDisplayed();
  }

  public boolean isPartnerSubmitButtonPresent() {
    String submitBtn = TAB_NAMES.get(getCurrentTab()) + "_multi_submit";
    return isElementPresentBySelector(By.id(submitBtn));
  }

  public boolean isCompletedSubmitButtonDisplayed() {
    return driver.findElement(By.id("Quinary_req_submit")).isDisplayed();
  }

  public boolean isSoleProprietorshipSubmitDisplayed() {
    return driver.findElement(By.id("Primary_req_submit")).isDisplayed();
  }

  public boolean isRemoveOwnerButtonDisplayed() {
    String removeOwnerBtn = TAB_NAMES.get(getCurrentTab()) + "_multi_remove";
    return driver.findElement(By.id(removeOwnerBtn)).isDisplayed();
  }

  public String getPercentOwnership() {
    return driver.findElement(By.id("officer_percent_display")).getText();
  }

  public String getLegalEntityType() {
    Select select = new Select(driver.findElement(By.id("saved_legal_entity_type")));
    return select.getFirstSelectedOption().getText();
  }

  /**
   * Finds related error message label for input field.
   *
   * @return validation error message text for given element type.
   */
  public String getInputElementErrorMessageForCurrentTab(ElementType type) {
    WebElement el = getTabInputElementByName(type.getName());
    By errorSelector = getErrorSelectorFromOriginator(el);
    return getTextBySelector(errorSelector);
  }

  /**
   * Determine if correct error message is displayed for given tab officer percentage.
   */
  public boolean isServerPercentageErrorDisplayed(int tabKey) {
    List<WebElement> errorMessages = driver.findElements(By.className("error_item"));
    String errorMessage = TAB_NAMES.get(tabKey) + ERROR_MESSAGE_OFFICER_PERCENT;
    for (WebElement element : errorMessages) {
      if (element.getText().equals(errorMessage)) {
        return true;
      }
    }

    return false;
  }

  public boolean isFormVisible() {
    return formationDate.isDisplayed();
  }

  public boolean isElementTypeEditable(ElementType type) {
    return driver.findElement(By.id(type.getName())).isEnabled();
  }

  public boolean isElementTypeVisible(ElementType type) {
    return driver.findElement(By.id(type.getName())).isDisplayed();
  }


  /**
   * Fill in the Officer information.
   */
  public Map<ElementType, String> fillOfficerForm() {
    return fillOfficerForm(false);
  }

  /**
   * Fills in the Officer information and allows the Officer Percent field to optionally be
   * skipped.
   */
  public Map<ElementType, String> fillOfficerForm(boolean skipOfficerPercent) {
    EnumMap<ElementType, String> fieldValues = new EnumMap<>(ElementType.class);

    if (!skipOfficerPercent) {
      fieldValues.put(ElementType.OFFICER_PERCENT,
          setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "25"));
    }
    fieldValues.put(ElementType.OFFICER_FIRST_NAME,
        setFormFieldForCurrentTab(ElementType.OFFICER_FIRST_NAME, faker.name().firstName()));
    fieldValues.put(ElementType.OFFICER_LAST_NAME,
        setFormFieldForCurrentTab(ElementType.OFFICER_LAST_NAME, faker.name().lastName()));
    fieldValues.put(ElementType.OFFICER_TITLE,
        setFormFieldForCurrentTab(ElementType.OFFICER_TITLE, faker.name().title()));
    fieldValues.put(ElementType.OFFICER_EMAIL,
        setFormFieldForCurrentTab(ElementType.OFFICER_EMAIL, faker.internet().emailAddress()));
    fieldValues.put(ElementType.OFFICER_ADDRESS,
        setFormFieldForCurrentTab(ElementType.OFFICER_ADDRESS, faker.address().streetAddress()));
    fieldValues.put(ElementType.OFFICER_CITY,
        setFormFieldForCurrentTab(ElementType.OFFICER_CITY, faker.address().city()));
    fieldValues.put(ElementType.OFFICER_STATE,
        setFormFieldForCurrentTab(ElementType.OFFICER_STATE, faker.address().stateAbbr()));
    fieldValues.put(ElementType.OFFICER_ZIP, setFormFieldForCurrentTab(ElementType.OFFICER_ZIP,
        faker.address().zipCode().substring(0, 5)));
    fieldValues.put(ElementType.OFFICER_LICENSE,
        setFormFieldForCurrentTab(ElementType.OFFICER_LICENSE,
            faker.bothify("??#####").toUpperCase()));
    fieldValues.put(ElementType.OFFICER_LICENSE_STATE,
        setFormFieldForCurrentTab(ElementType.OFFICER_LICENSE_STATE, faker.address().stateAbbr()));
    fieldValues.put(ElementType.OFFICER_DOB, setFormFieldForCurrentTab(ElementType.OFFICER_DOB,
        new SimpleDateFormat("MM/dd/yyyy").format(faker.date().past(20 * 365, TimeUnit.DAYS))));
    fieldValues.put(ElementType.OFFICER_SSN, setFormFieldForCurrentTab(ElementType.OFFICER_SSN,
        String.valueOf(faker.number().numberBetween(100000000, 999999999))));

    return fieldValues;
  }

  public void clearFormField(ElementType type) {
    WebElement el = getTabInputElementByName(type.getName());
    el.clear();
  }

  /**
   * Enters a value into the form field element.
   */
  public String setFormFieldForCurrentTab(ElementType type, String value) {
    WebElement el = getTabInputElementByName(type.getName());

    if (ElementType.OFFICER_DOB == type) {
      setDateFieldValue(el.getAttribute("id"), value);
    } else {
      enterElementValue(el, value);
    }

    forceLastElementBlur();

    return value;
  }

  public String getFormFieldValueForCurrentTab(ElementType type) {
    WebElement el = getTabInputElementByName(type.getName());

    return el.getAttribute("value");
  }

  /**
   * Selects the  "additional Beneficial Owner with ownership greater than or equal to 25%?:" to
   * NO.
   */
  public void notAddAdditionalOfficerForCurrentTab() {
    WebElement el = getTabInputElementByName("additional_officer");
    highlight(el);
    new Select(el).selectByValue("no");
    Logger.trace("Set Additional Officer select field to 'no'");
  }

  /**
   * Selects the "additional Beneficial Owner with ownership greater than or equal to 25%?:" to
   * YES.
   */
  public void addAdditionalOfficerForCurrentTab() {
    WebElement el = getTabInputElementByName("additional_officer");
    highlight(el);
    new Select(el).selectByValue("yes");
    Logger.trace("Set Additional Officer select field to 'yes'");
  }

  private By getErrorSelectorFromOriginator(WebElement selector) {
    String originatingElementId = selector.getAttribute("id");
    return (By.cssSelector("label.error[for='" + originatingElementId + "']"));
  }

  private void enterElementValue(WebElement el, String value) {
    highlight(el);
    Logger.trace("Entering " + el.toString() + ": " + value);
    el.sendKeys(value);
  }

  private WebElement getTabInputElementByName(String name) {
    Logger.trace("Looking for input element: " + name + " in tab-" + getCurrentTab());
    int currentTab = getCurrentTab();
    if (currentTab != 1) {
      name = "[" + name + "]";
    }
    WebElement el = driver
        .findElement(By.cssSelector("#tab-" + currentTab + " [name$='" + name + "']"));
    return el;
  }

  /**
   * Determine if KYC welcome message is displayed.
   *
   * @return true if welcome message is displayed.
   */
  public boolean isWelcomeMessageDisplayed() {
    WebElement el = driver.findElement(By.id("kyc_welcome"));
    String text = el.getText();
    return text.contains(MESSAGE_KYC_WELCOME_1) && text.contains(MESSAGE_KYC_WELCOME_2) && text
        .contains(MESSAGE_KYC_WELCOME_3) && text.contains(MESSAGE_KYC_WELCOME_4);
  }

  private boolean waitForTabSwitch(WebElement currentTab) {
    try {
      wait.until(new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver driver) {
          return getCurrentTabBody() != currentTab;
        }
      });
      wait.until(new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver driver) {
          JavascriptExecutor js = (JavascriptExecutor) driver;
          return (Boolean) js.executeScript("return window.switchingTabs === false");
        }
      });
      return true;
    } catch (Throwable exception) {
      Logger.error(exception.toString());
      return false;
    }
  }

  /**
   * Get the label of the Private or Public Company dropdown.
   *
   * @return String
   */
  public String getLegalEntityPublicPrivateDropdownLabel() {
    return getTextBySelector(By.cssSelector("label[for='legal_entity_ownership_type']"));
  }

  /**
   * Get the Public or Private Company dropdown options.
   *
   * @return List of options
   */
  public List<String> getLegalEntityPublicPrivateDropdownOptions() {
    Select select = new Select(
        driver.findElement(By.id(ElementType.LEGAL_ENTITY_PRIVATE_PUBLIC_DROPDOWN.getName())));

    List<WebElement> dropdownOpt = select.getOptions();
    List<String> optionsList = new ArrayList<>();

    for (WebElement option : dropdownOpt) {
      optionsList.add(option.getText());
    }

    return optionsList;
  }

  /**
   * Select Private or Public Company dropdown option.
   *
   * @param option to select
   */
  public void selectPrivatePublicCompanyOption(String option) {
    Select select = new Select(
        driver.findElement(By.id(ElementType.LEGAL_ENTITY_PRIVATE_PUBLIC_DROPDOWN.getName())));
    select.selectByValue(option);
  }

  /**
   * Get value of selected option for Private or Public Company dropdown.
   *
   * @return selected option text
   */
  public String getLegalEntityPublicPrivateSelectedValue() {
    Select select = new Select(
        driver.findElement(By.id(ElementType.LEGAL_ENTITY_PRIVATE_PUBLIC_DROPDOWN.getName())));
    return select.getFirstSelectedOption().getText();
  }

  /** Fill out officers info with valid info. */
  public void fillWithValidOfficerInfo() {
    this.setFormFieldForCurrentTab(ElementType.OFFICER_FIRST_NAME, "John");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_LAST_NAME, "Smith");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_TITLE, "CEO");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_EMAIL, "johnsmith@testguy.com");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_DOB, "05/15/1970");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_SSN, "123456543");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_STATE, "TX");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_LICENSE, "01234567");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_ADDRESS, "1923 Lake Forest Rd");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_CITY, "Grapevine");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_LICENSE_STATE, "TX");
    this.setFormFieldForCurrentTab(ElementType.OFFICER_ZIP, "76051");
  }

  /** Clear the value of Tax ID field for Legal Entity. */
  public void clearValueFromTaxIdField() {
    String clearPlaceHolderTaxId = "document.getElementById('legal_bus_tax_id').setAttribute('placeholder', '')";
    String clearValueFromMaskedTaxId = "document.getElementsByName('legal_bus_tax_id_masked')[0].setAttribute('value', '')";

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(clearPlaceHolderTaxId);
    js.executeScript(clearValueFromMaskedTaxId);
  }

  /**
   * Change the maxlength attribute of the HTML field.
   *
   * @param id ID of the HTML field being changed
   * @param length Numeric length to change the maxlength attribute to
   */
  public void changeMaxLengthOfHtmlField(String id, int length) {
    String changeMaxLength = "document.getElementById('" + id + "').setAttribute('maxlength', " + length + ")";

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(changeMaxLength);
  }

  /**
   * Get array of errors on submit page.
   * @return array of errors
   */
  public ArrayList<String> getErrors() {
    List<WebElement> errorsArray = driver.findElements(By.cssSelector(".error_item.error_server"));
    ArrayList<String> errors = new ArrayList<>();

    for (WebElement errorArrayItem : errorsArray) {
      errors.add(errorArrayItem.getText());
    }

    return errors;
  }

  public boolean isRemoveOwnerButtonDisplayedAfterVerified() {
    String removeOwnerBtn = TAB_NAMES.get(getCurrentTab()) + "_multi_remove";
    return driver.findElements(By.id(removeOwnerBtn)).size() > 0;
  }


  public boolean isAdditionalOwnerDropdownEditable() {
    WebElement el = getTabInputElementByName("additional_officer");
    return el.isEnabled();
  }

  public boolean isAdditionalOwnerDropdownVisible() {
    int currentTab = getCurrentTab();

    List<WebElement> el = driver
        .findElements(By.id(TAB_NAMES.get(currentTab) + "_additional_officer"));

    return (el.size() > 0 && el.get(0).isDisplayed());
  }

  public void clickRemoveOwnerButton() {
    String removeOwnerButtonId = TAB_NAMES.get(getCurrentTab()) + "_multi_remove";
    wait.until(ExpectedConditions.elementToBeClickable(By.id(removeOwnerButtonId)));
    WebElement removeOwnerButton = driver.findElement(By.id(removeOwnerButtonId));
    highlight(removeOwnerButton);
    removeOwnerButton.click();
  }

  public void selectReviewOwnerButtonOnSummary(int officer) {
    String officerType = TAB_NAMES.get(officer);
    WebElement el = driver
        .findElement(By.cssSelector("input[data-officer=" + officerType + "]"));
    highlight(el);
    el.click();
  }

  public int getNumOfOwnersInSummary() {
    List<WebElement> owners = driver.findElements(By.cssSelector(".summary_officer_row"));
    return owners.size();
  }
}
