package com.paylease.app.qa.framework.components;

import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.components.datepicker.DateDisabledException;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.Calendar;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DatePicker extends PageBase {

  private enum ViewType {
    MONTH(0), YEAR(1), YEAR_RANGE(2);

    private final int level;

    ViewType(int level) {
      this.level = level;
    }

    int getLevel() {
      return level;
    }
  }

  private WebElement datePicker;

  public DatePicker(WebElement datePicker) {
    super();
    this.datePicker = datePicker;
  }

  /**
   * Determine if the given day can be selected.
   *
   * @param date Date to check
   * @return True if the calendar has the month view and the date is not disabled
   */
  public boolean isDayInMonthEnabled(Calendar date) {
    int day = date.get(Calendar.DAY_OF_MONTH);

    try {
      WebElement dayPicker = navigateToMonth(date);

      WebElement dayInMonth = dayPicker.findElement(By.xpath(".//td[" + xPathMatchNotClassName("dp_not_in_month") + " and text() = '" + day + "']"));
      highlight(dayInMonth);
      return !UtilityManager.elementHasClass(dayInMonth, "dp_disabled");
    } catch (DateDisabledException e) {
      // can not navigate to given month or year - date is definitely not enabled
      return false;
    }
  }

  /**
   * Determine if the given month can be selected.
   *
   * @param date Date to check
   * @return True if the calendar has the year view and the month is not disabled
   */
  public boolean isMonthInYearEnabled(Calendar date) {
    String desiredMonth = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

    try {
      WebElement monthPicker = navigateToYear(date);

      String monthAbbr = desiredMonth.substring(0, 3);
      WebElement monthElement = monthPicker
          .findElement(By.xpath(".//td[text() = '" + monthAbbr + "']"));
      return !UtilityManager.elementHasClass(monthElement, "dp_disabled");
    } catch (DateDisabledException e) {
      return false;
    }
  }

  private ViewType getCurrentView() {
    String currentMonthYear = getCaption();

    String[] monthYearParts = currentMonthYear.split(", ");
    if (monthYearParts.length == 2) {
      return ViewType.MONTH;
    }
    if (currentMonthYear.contains(" - ")) {
      return ViewType.YEAR_RANGE;
    }
    return ViewType.YEAR;
  }

  private WebElement navigateToYearRange(Calendar date) throws DateDisabledException {
    ViewType currentView = getCurrentView();
    for (int i = currentView.getLevel(); i < ViewType.YEAR_RANGE.getLevel(); i++) {
      up();
    }

    int desiredYear = date.get(Calendar.YEAR);
    boolean desiredYearInRange = false;

    while (!desiredYearInRange) {
      String yearRangeStr = getCaption();
      String[] yearRanges = yearRangeStr.split(" - ");
      int minYear = Integer.parseInt(yearRanges[0]);
      int maxYear = Integer.parseInt(yearRanges[1]);

      if (desiredYear < minYear) {
        previous();
      } else if (desiredYear > maxYear) {
        next();
      } else {
        desiredYearInRange = true;
      }
    }
    return datePicker.findElement(By.className("dp_yearpicker"));
  }

  private WebElement navigateToYear(Calendar date) throws DateDisabledException {
    ViewType currentView = getCurrentView();
    for (int i = currentView.getLevel(); i < ViewType.YEAR.getLevel(); i++) {
      up();
    }

    int desiredYear = date.get(Calendar.YEAR);
    int currentYear = Integer.parseInt(getCaption());
    if (currentYear != desiredYear) {
      WebElement yearPicker = navigateToYearRange(date);
      WebElement yearElement = yearPicker
          .findElement(By.xpath(".//td[text() = '" + desiredYear + "']"));
      if (UtilityManager.elementHasClass(yearElement, "dp_disabled")) {
        throw new DateDisabledException();
      }
      yearElement.click();
    }

    return datePicker.findElement(By.className("dp_monthpicker"));
  }

  private WebElement navigateToMonth(Calendar date) throws DateDisabledException {
    ViewType currentView = getCurrentView();
    if (currentView == ViewType.MONTH) {
      String header = getCaption();
      String[] monthYearParts = header.split(", ");

      String curMonth = monthYearParts[0];
      int curYear = Integer.valueOf(monthYearParts[1]);
      String desiredMonth = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
      int desiredYear = date.get(Calendar.YEAR);

      if (!curMonth.equals(desiredMonth) || curYear != desiredYear) {
        WebElement monthPicker = navigateToYear(date);

        String monthAbbr = desiredMonth.substring(0, 3);
        WebElement monthElement = monthPicker
            .findElement(By.xpath(".//td[text() = '" + monthAbbr + "']"));
        if (UtilityManager.elementHasClass(monthElement, "dp_disabled")) {
          throw new DateDisabledException();
        }
        monthElement.click();
      }
    }

    return datePicker.findElement(By.className("dp_daypicker"));
  }

  private String getCaption() {
    WebElement datePickerHeader = datePicker.findElement(By.className("dp_header"));
    WebElement datePickerMonthYear = datePickerHeader.findElement(By.className("dp_caption"));
    return datePickerMonthYear.getText();
  }

  private void up() {
    WebElement datePickerHeader = datePicker.findElement(By.className("dp_header"));
    datePickerHeader.findElement(By.className("dp_caption")).click();
  }

  private void previous() throws DateDisabledException {
    WebElement datePickerHeader = datePicker.findElement(By.className("dp_header"));
    WebElement previous = datePickerHeader.findElement(By.className("dp_previous"));
    if (UtilityManager.elementHasClass(previous, "dp_blocked")) {
      throw new DateDisabledException();
    }

    previous.click();
  }

  private void next() throws DateDisabledException {
    WebElement datePickerHeader = datePicker.findElement(By.className("dp_header"));
    WebElement next = datePickerHeader.findElement(By.className("dp_next"));
    if (UtilityManager.elementHasClass(next, "dp_blocked")) {
      throw new DateDisabledException();
    }

    next.click();
  }
}
