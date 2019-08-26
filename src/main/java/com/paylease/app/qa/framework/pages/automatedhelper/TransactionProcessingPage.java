package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.components.datatable.TransHelperPayouts;
import com.paylease.app.qa.framework.components.datatable.TransHelperPayouts.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * this class interacts with the transaction process result page.
 */
public class TransactionProcessingPage extends PageBase {

  @FindBy(id = "payouts")
  WebElement payoutTable;

  /**
   * get the from_id of the transaction.
   *
   * @return from_id
   */
  public String getFromId() {
    return getTextBySelector(By.id("fromId"));
  }

  /**
   * get the to_id of the transaction.
   *
   * @return to_id
   */
  public String getToId() {
    return getTextBySelector(By.id("toId"));
  }

  /**
   * get the paylease_fee of the transaction.
   *
   * @return paylease_fee
   */
  public String getPayleaseFee() {
    return getTextBySelector(By.id("payleaseFee"));
  }

  /**
   * get the PM_pay_fee of the transaction.
   *
   * @return PM_pay_fee
   */
  public String getPmPayFee() {
    return getTextBySelector(By.id("pmPayFee"));
  }

  /**
   * get the pm_fee_amount of the transaction.
   *
   * @return pm_fee_amount
   */
  public String getPmFeeAmount() {
    return getTextBySelector(By.id("pmFeeAmount"));
  }

  /**
   * Get the Pending Status for the corresponding Gateway Transaction, if any.
   *
   * @return status id
   */
  public String getGatewayPendingStatus() {
    return getTextBySelector(By.id("gateway_pending_status"));
  }

  /**
   * get the from user yardi_account of the transaction.
   *
   * @return yardi_account
   */
  public String getFromUserYardiAccount() {
    return getTextBySelector(By.id("fromUserYardiAccount"));
  }

  /**
   * Find the payout by accountId and amount.
   *
   * @param accountId the account id
   * @param amount the amount
   * @return true if found in table
   */
  public boolean isPayoutPresent(String accountId, String amount) {
    TransHelperPayouts payouts = new TransHelperPayouts(payoutTable);

    HashMap<String, String> payoutData = new HashMap<>();
    payoutData.put(Columns.ACCOUNT_ID.getLabel(), accountId);
    payoutData.put(Columns.AMOUNT.getLabel(), amount);

    return payouts.getRowMatchingData(payoutData) != null;
  }
}
