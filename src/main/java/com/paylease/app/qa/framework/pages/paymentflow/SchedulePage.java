package com.paylease.app.qa.framework.pages.paymentflow;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.DatePicker;
import com.paylease.app.qa.framework.pages.FormPageBase;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SchedulePage extends FormPageBase {

  public static final String DATE_PICKER_START_DATE = "datePickerStartDate";
  public static final String DATE_PICKER_END_DATE = "datePickerEndDate";

  public static final String FIELD_START_DATE = "startDate";
  public static final String FIELD_FREQUENCY = "frequency";
  public static final String FIELD_INDEFINITE = "indefinite";
  public static final String FIELD_END_DATE = "endDate";
  public static final String FIELD_MAX_LIMIT_ENABLED = "maxLimitEnabled";
  public static final String FIELD_MAX_LIMIT_VALUE = "maxLimitValue";

  public static final String SELECT_MONTHLY = "Monthly";
  public static final String SELECT_QUARTERLY = "Quarterly";
  public static final String SELECT_BI_ANNUALLY = "Bi-Annually";
  public static final String SELECT_ANNUALLY = "Annually";

  private static final String WARNING_RECENT_PAYMENT = "A payment was recently scheduled.";
  private static final String WARNING_EXISTING_AUTOPAY = "You have an existing AutoPay.";

  private static final String DATE_PICKER_CLASS = "Zebra_DatePicker";

  @FindBy(id = "datepicker_autopay_start")
  private WebElement startDate;

  @FindBy(id = "frequency")
  private WebElement paymentFrequency;

  @FindBy(id = "datepicker_autopay_end")
  private WebElement endDate;

  @FindBy(id = "indefinite")
  private WebElement indefiniteCheckbox;

  @FindBy(name = "submit_schedule")
  private WebElement continueButton;

  private DatePicker datePicker;

  // ********************************************Action*********************************************

  /**
   * The page is loaded when the success message appears.
   *
   * @return true if the success message is present
   */
  public boolean pageIsLoaded() {
    return !driver.findElements(By.className("ap_sched")).isEmpty();
  }

  /**
   * Fills details in the Payment Schedule page and then clicks on the continue button.
   */
  public void fillAndSubmitPaymentScheduleDetails() {
    fillForm();
    clickContinueButton();
  }

  /**
   * Fill the form with valid or pre-defined values and submit. Does not expect a new page to load -
   * returns after clicking submit button.
   */
  public void fillAndSubmitWithErrors() {
    fillForm();
    clickContinueWithErrors();
  }

  /**
   * Determine if the indefinite checkbox is checked.
   *
   * @return True if the box is checked
   */
  public boolean isIndefiniteChecked() {
    highlight(indefiniteCheckbox);
    return indefiniteCheckbox.isSelected();
  }

  /**
   * Open the Start Date calendar - click the start date field and wait for the calendar to appear.
   */
  public void openCalendar(String dateField) {
    closeCalendar();

    WebElement field;
    int index;
    switch (dateField) {
      case DATE_PICKER_START_DATE:
        field = startDate;
        index = 1;
        break;
      case DATE_PICKER_END_DATE:
        field = endDate;
        index = 2;
        break;
      default:
        throw new InvalidArgumentException("Date field " + dateField + " not recognized");
    }
    highlight(field);
    field.click();

    datePicker = new DatePicker(wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//*[" + xPathMatchClassName(DATE_PICKER_CLASS) + "][" + index + "]"))));
  }

  /**
   * Determine if a date on the currently open calendar is available to be selected. This is based
   * on the presence or absence of the "dp_disabled" classname for the DatePicker
   *
   * @param date Date to check
   * @return True if the date is not disabled
   */
  public boolean isDayInMonthEnabled(Calendar date) {
    return datePicker.isDayInMonthEnabled(date);
  }

  public boolean isMonthEnabled(Calendar date) {
    return datePicker.isMonthInYearEnabled(date);
  }

  public String getStartDateError() {
    return getTextBySelector(By.cssSelector("label.error[for='datepicker_autopay_start']"));
  }

  public String getFrequencyError() {
    return getTextBySelector(By.cssSelector("label.error[for='frequency']"));
  }

  public String getEndDateError() {
    return getTextBySelector(By.cssSelector("label.error[for='datepicker_autopay_end']"));
  }

  public String getMaxLimitValueError() {
    return getTextBySelector(By.cssSelector("label.error[for='max_limit_val"));
  }

  /**
   * Look for a warning message specifically about existing auto pays.
   *
   * @return true if such a warning exists
   */
  public boolean hasExistingAutoPayWarning() {
    return hasWarningMessage(WARNING_EXISTING_AUTOPAY);
  }

  /**
   * Look for a warning message specifically about recent payment.
   *
   * @return true if such a warning exists
   */
  public boolean hasRecentPaymentWarning() {
    return hasWarningMessage(WARNING_RECENT_PAYMENT);
  }

  /**
   * Hover over the help icon with the Recent Payment Warning, if it exists. Blocks until the
   * tooltip becomes visible.
   */
  public void hoverRecentPaymentWarningHelp() {
    WebElement warningMessage = getWarningMessage(WARNING_RECENT_PAYMENT);
    if (warningMessage != null) {
      WebElement warningMessageHelp = warningMessage.findElement(By.className("help_icon"));
      mouseHover(warningMessageHelp);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("qtip-focus")));
    }
  }

  public String getTooltipText() {
    return getTextBySelector(By.className("qtip-focus"));
  }

  private boolean hasWarningMessage(String message) {
    WebElement warningMessage = getWarningMessage(message);
    return warningMessage != null;
  }

  private WebElement getWarningMessage(String message) {
    List<WebElement> warnings = driver.findElements(By.id("new_warning_container"));
    for (WebElement warning : warnings) {
      WebElement warningMessage = warning.findElement(By.className("warning_box_p"));
      if (warningMessage.getText().trim().equals(message)) {
        return warningMessage;
      }
    }
    return null;
  }

  private void closeCalendar() {
    driver.findElement(By.tagName("body")).click();
    datePicker = new DatePicker(null);
  }

  private void fillForm() {
    setRecurringPaymentDate();
    selectPaymentFrequency();
    clickIndefiniteCheckbox();
    setMaxLimitCheckbox();
    setMaxLimitAmount();
    setFinalDate();
  }

  private String dateGenerator() {

    SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
    Date date = new Date();
    String yearToday = formatYear.format(date);
    int yearTodayInt = Integer.parseInt(yearToday);
    int newYearValue = yearTodayInt + 1;

    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    int month = localDate.getMonthValue();

    return month + "/" + "1" + "/" + newYearValue;
  }

  private void setRecurringPaymentDate() {
    highlight(startDate);

    String dateToSet = (String) getFieldValue(FIELD_START_DATE);

    Logger.trace("Set the recurring payment date to " + dateToSet);

    ((JavascriptExecutor) driver).executeScript(
        "document.getElementById('datepicker_autopay_start').value = '" + dateToSet + "'");
  }

  private void setFinalDate() {
    highlight(endDate);

    String dateToSet = (String) getFieldValue(FIELD_END_DATE);
    Logger.trace("Set the final payment date to " + dateToSet);

    ((JavascriptExecutor) driver).executeScript(
        "document.getElementById('datepicker_autopay_end').value = '" + dateToSet + "'");
  }

  private void selectPaymentFrequency() {
    highlight(paymentFrequency);

    String optionToChoose = (String) getFieldValue(FIELD_FREQUENCY, "-- select --");

    Logger.trace("Selecting Payment Frequency " + optionToChoose);

    Select selectFrequency = new Select(paymentFrequency);
    selectFrequency.selectByVisibleText(optionToChoose);
  }

  private void clickIndefiniteCheckbox() {
    Boolean checkboxState = (Boolean) getFieldValue(FIELD_INDEFINITE, true);

    if (checkboxState) {
      // by default, if indefinite is checked, we can leave the end date blank
      // but if someone already set a value for end date, leave that intact
      prepFields.putIfAbsent(FIELD_END_DATE, FIELD_VALUE_EMPTY);
    }
    if (isIndefiniteChecked() != checkboxState) {
      indefiniteCheckbox.click();
      Logger.trace("Checked " + indefiniteCheckbox + " box");
    } else {
      Logger.trace("Checkbox " + indefiniteCheckbox + " already set as desired");
    }
  }

  private void setMaxLimitCheckbox() {
    List<WebElement> checkboxElements = driver.findElements(By.id("max_limit_check"));
    if (checkboxElements.isEmpty()) {
      return;
    }
    WebElement maxLimitCheckbox = checkboxElements.get(0);

    Boolean checkboxState = (Boolean) getFieldValue(FIELD_MAX_LIMIT_ENABLED, false);
    if (!checkboxState) {
      // by default, if max limit is not checked, we can leave the max limit blank
      prepFields.putIfAbsent(FIELD_MAX_LIMIT_VALUE, FIELD_VALUE_EMPTY);
    }

    if (maxLimitCheckbox.isSelected() != checkboxState) {
      maxLimitCheckbox.click();
      Logger.trace("Checked max limit box");
    } else {
      Logger.trace("Checkbox max limit already set as desired");
    }
  }

  private void setMaxLimitAmount() {
    List<WebElement> maxLimitElements = driver.findElements(By.id("max_limit_val"));
    if (maxLimitElements.isEmpty() || !maxLimitElements.get(0).isDisplayed()) {
      return;
    }
    WebElement maxLimitValue = maxLimitElements.get(0);

    String maxLimitToSet = (String) getFieldValue(FIELD_MAX_LIMIT_VALUE, "");

    Logger.trace("Entering max limit amount " + maxLimitToSet);

    maxLimitValue.click();
    maxLimitValue.clear();
    maxLimitValue.sendKeys(maxLimitToSet);
  }

  /**
   * Clicks the continue button.
   */
  public void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);

    Logger.trace("Clicked Continue button on Payment Schedule page");
  }

  /**
   * Click the continue button. Expect validation errors and no new page load.
   */
  public void clickContinueWithErrors() {
    continueButton.click();
  }

  protected Object getValid(String fieldName) throws InvalidArgumentException {
    Object validValue;
    switch (fieldName) {
      case FIELD_START_DATE:
        validValue = dateGenerator();
        break;
      case FIELD_FREQUENCY:
        Select selectFrequency = new Select(paymentFrequency);

        List<WebElement> options = selectFrequency.getOptions();
        ArrayList<String> optionValues = new ArrayList<>();
        for (WebElement optElement : options) {
          String value = optElement.getAttribute("value");
          if (!value.isEmpty()) {
            optionValues.add(optElement.getText());
          }
        }

        Random randomizer = new Random();
        validValue = optionValues.get(randomizer.nextInt(optionValues.size()));
        break;
      case FIELD_INDEFINITE:
        validValue = true;
        break;
      case FIELD_END_DATE:
        Date currentDate = new Date();

        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault())
            .toLocalDateTime();

        localDateTime = localDateTime.plusMonths(1).plusYears(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        validValue = localDateTime.format(formatter);

        break;
      case FIELD_MAX_LIMIT_ENABLED:
        validValue = false;
        break;
      case FIELD_MAX_LIMIT_VALUE:
        DataHelper dataHelper = new DataHelper();
        validValue = dataHelper.getAmount();
        break;
      default:
        throw new InvalidArgumentException("Unsupported field name " + fieldName);
    }

    return validValue;
  }
}
