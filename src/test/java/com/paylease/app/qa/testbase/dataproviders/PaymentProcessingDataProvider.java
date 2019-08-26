package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;

import org.testng.annotations.DataProvider;

public class PaymentProcessingDataProvider {

  public static final String BR_PT = "php web/batches/br_pt.php";
  public static final String BR_PT_PROFIT_STARS = "php web/batches/br_pt_profit_stars.php";
  public static final String BR_FN_SP = "php web/batches/br_fn_sp.php";
  public static final String BR_PROCESS_CC = "php web/batches/br_process_cc.php";
  public static final String BR_SP_PROFITSTARS = "php web/batches/br_sp_profitstars.php";
  public static final String BR_FN_PROCESS_RETURNS = "php web/batches/br_fn_process_returns.php";
  public static final String BR_SP_PROFITSTARS_NEW_WORLD = "php web/batches"
      + "/br_sp_profitstars_new_world.php";
  public static final String BR_PROFITSTARS_PROCESS_RETURNS = "php web/"
      + "batches/br_profitstars_process_returns";

  public static final String BR_PT_AND_BR_FN_SP = "Runs br_pt and br_fn_sp";
  public static final String BR_PT_PROFIT_STARS_AND_BR_SP_PROFITSTARS = "Runs br_pt_profit_stars "
      + "and br_sp_profitstars";
  public static final String BR_PROCESS_CC_AND_BR_PT = "Runs " + BR_PROCESS_CC + " and " + BR_PT;
  public static final String BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP =
      "Runs " + BR_PROCESS_CC + ", " + BR_PT + " and " + BR_FN_SP;

  /**
   * Provides data required to run various kinds of test combinations for different processors.
   *
   * @return data
   */
  @DataProvider(name = "paymentProcessing")
  public Object[][] dataOldWorld() {

    return new Object[][]{
        //Test variation no., PaymentType, ScriptName
        {"tc1", NEW_BANK, BR_PT_PROFIT_STARS},
        {"tc2", NEW_BANK, BR_PT_PROFIT_STARS_AND_BR_SP_PROFITSTARS},
        {"tc3", NEW_BANK, BR_PT},
        {"tc4", NEW_CREDIT, BR_PROCESS_CC_AND_BR_PT},
        {"tc5", NEW_BANK, BR_PT_AND_BR_FN_SP},
        {"tc6", NEW_CREDIT, BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP}
    };
  }

  /**
   * Provides data required to run payment returns FNBO old and new world processes.
   *
   * @return data
   */
  @DataProvider(name = "paymentReturns")
  public Object[][] paymentReturns() {

    return new Object[][]{
        //Test variation no., isNewWorld
        {"tc1", false},
        {"tc2", true}
    };
  }
}
