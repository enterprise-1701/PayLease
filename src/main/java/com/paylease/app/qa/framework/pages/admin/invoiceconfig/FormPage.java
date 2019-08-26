package com.paylease.app.qa.framework.pages.admin.invoiceconfig;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Mahfuz Alam on 10/18/2017.
 */

public abstract class FormPage extends PageBase {

  public static final String CHECKBOX_RUN = "Run Once";
  public static final String CHECKBOX_INCURRED = "Get Incurred Fees";
  public static final String CHECKBOX_PAYDIRECT = "Get PayDirect Fees";
  public static final String CHECKBOX_NSF = "Get NSF Fees";

  protected String pmId;

  @FindBy(id = "direct_debit_or_mail")
  private WebElement directDebitOrMail;

  @FindBy(name = "run_once")
  private WebElement checkboxRunOnce;

  @FindBy(name = "get_fees")
  private WebElement checkboxGetIncurredFees;

  @FindBy(name = "get_paydirects")
  private WebElement checkboxGetPaydirect;

  @FindBy(name = "get_nsf")
  private WebElement checkboxGetNsf;

  @FindBy(id = "currency_code")
  private WebElement currencyCode;

  @FindBy(id = "property")
  private WebElement property;

  @FindBy(id = "bankacc")
  private WebElement bankAccount;

  @FindBy(name = "Submit")
  private WebElement saveButton;

  private Select selectPaymentMode;

  private Select selectProperty;

  private Select selectBankAccount;

  private Select selectCurrencyCode;

  /**
   * Create a Form Page.
   *
   * @param pmId PM ID for this page
   */
  FormPage(String pmId) {
    super();
    this.pmId = pmId;
  }

  void initSelectElements() {
    selectPaymentMode = new Select(directDebitOrMail);
    selectProperty = new Select(property);
    selectBankAccount = new Select(bankAccount);
    selectCurrencyCode = new Select(currencyCode);
  }

  // ********************************************Action*********************************************

  /**
   * Get the selected value for Direct Debit or Mail dropdown.
   *
   * @return Selected value
   */
  public String getDebitOrMail() {
    highlight(directDebitOrMail);
    WebElement defaultOption = selectPaymentMode.getFirstSelectedOption();
    Logger.trace("Default Invoice option is: " + defaultOption.getText());

    return defaultOption.getText();
  }

  /**
   * Select the given option for Direct Debit or Mail dropdown.
   *
   * @param debitOrMail Desired value
   */
  public void setDebitOrMail(String debitOrMail) {
    highlight(directDebitOrMail);
    selectPaymentMode.selectByVisibleText(debitOrMail);
    Logger.trace("Selected Invoice option: " + debitOrMail);
  }

  /**
   * Select the given currency code for dropdown.
   *
   * @param code Currency Code value
   */
  public void setCurrencyCode(String code) {
    highlight(currencyCode);
    Logger.trace("Selecting Currency code: " + code);
    selectCurrencyCode.selectByValue(code);
  }

  /**
   * Check the desired checkbox.
   *
   * @param checkboxName Checkbox to check
   * @throws Exception Unrecognized checkbox name
   */
  public void setCheckbox(String checkboxName) throws Exception {
    WebElement checkbox = getCheckboxElement(checkboxName);
    if (!checkbox.isSelected()) {
      checkbox.click();
      Logger.trace("Checked " + checkboxName + " box");
    } else {
      Logger.trace("Checkbox " + checkboxName + " already checked");
    }
  }

  public boolean isCheckBoxChecked(String checkboxName) throws Exception {
    WebElement checkbox = getCheckboxElement(checkboxName);
    return checkbox.isSelected();
  }

  /**
   * Overload setSelectedPropertyName with default to NOT include currency code in response.
   *
   * @return Name of the selected property without the currency code(s)
   */
  public String setSelectedPropertyName() {
    return setSelectedPropertyName(false);
  }

  /**
   * Select a random property from the Property dropdown. Excludes first option which is PM name.
   *
   * @return Name of the selected property
   */
  private String setSelectedPropertyName(boolean includeCurrencyCode) {
    selectProperty.selectByIndex(new Random().nextInt(selectProperty.getOptions().size() - 1) + 1);

    return getPropertyName(includeCurrencyCode);
  }

  /**
   * Get selected property name without currency code(s).
   *
   * @return Property name
   */
  public String getPropertyName() {
    return getPropertyName(false);
  }

  /**
   * Get selected property.
   *
   * @return Property name
   */
  private String getPropertyName(boolean includeCurrencyCode) {
    highlight(property);
    WebElement defaultOption = selectProperty.getFirstSelectedOption();
    String fullPropertyName = defaultOption.getText();
    Logger.trace("Selected Property: " + fullPropertyName);

    if (includeCurrencyCode) {
      return fullPropertyName;
    }

    return fullPropertyName.replaceAll("\\(.*\\)$", "").trim();
  }

  /**
   * Set a random bank account from Bank Account dropdown.
   *
   * @return selected bank account option text
   */
  public String setSelectedBankAccount() {
    selectBankAccount.selectByIndex(new Random().nextInt(selectBankAccount.getOptions().size()));

    WebElement selectedBankAccount = selectBankAccount.getFirstSelectedOption();
    Logger.trace("Selected Bank Account: " + selectedBankAccount.getText());

    return selectedBankAccount.getText();
  }

  /**
   * Get selected bank account option text.
   *
   * @return selected bank account option text
   */
  public String getBankName() {
    highlight(bankAccount);
    WebElement defaultOption = selectBankAccount.getFirstSelectedOption();
    Logger.trace("Selected Bank Account: " + defaultOption.getText());

    return defaultOption.getText();
  }

  /**
   * Get an array of all options for Bank Account.
   *
   * @return All Bank Account option texts
   */
  public String[] getAllBankAccountNames() {
    highlight(bankAccount);
    List<WebElement> options = selectBankAccount.getOptions();
    String[] allNames = new String[options.size()];
    int i = 0;
    for (WebElement option : options) {
      allNames[i++] = option.getText();
    }

    return allNames;
  }

  /**
   * Get an array of all options for Properties. Can include PM or not
   *
   * @return All Property option texts
   */
  public String[] getAllPropertyNames(boolean includePm) {
    highlight(property);
    List<WebElement> options = selectProperty.getOptions();

    int optionCount = options.size();
    if (!includePm) {
      optionCount -= 1;
    }

    String[] allProperties = new String[optionCount];

    int i = 0;
    for (int j = 0; j < options.size(); j++) {
      if (!includePm && 0 == j) {
        continue;
      }
      allProperties[i++] = options.get(j).getText();
    }

    return allProperties;
  }

  /**
   * Click the save button on the form and return config Id after page reload.
   *
   * @return The FormEditPage object that is loaded after save
   */
  public FormEditPage clickSave() {
    highlight(saveButton);
    clickAndWait(saveButton);
    Logger.trace("Clicked on the save button");

    String configId = getConfigId();

    return new FormEditPage(pmId, configId);
  }

  public String getErrorMessage() {
    return getTextBySelector(By.className("error_bar"));
  }

  private String getConfigId() {
    String url = driver.getCurrentUrl();

    String paramName = "invoices_config_id=";
    int startIndex = url.indexOf(paramName);
    if (startIndex < 0) {
      Logger.trace("URL does not include config id");
      return null;
    }
    int start = startIndex + paramName.length();
    int end = url.indexOf("&", start);

    end = (end == -1 ? url.length() : end);

    String configId = url.substring(start, end);
    Logger.trace("Config Id is: " + configId);

    return configId;
  }

  private WebElement getCheckboxElement(String checkboxName) throws Exception {
    WebElement checkbox;
    switch (checkboxName) {
      case CHECKBOX_RUN:
        checkbox = checkboxRunOnce;
        break;
      case CHECKBOX_INCURRED:
        checkbox = checkboxGetIncurredFees;
        break;
      case CHECKBOX_PAYDIRECT:
        checkbox = checkboxGetPaydirect;
        break;
      case CHECKBOX_NSF:
        checkbox = checkboxGetNsf;
        break;
      default:
        throw new Exception("Checkbox Name not recognized " + checkboxName);
    }
    highlight(checkbox);
    return checkbox;
  }

}
