package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;

import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.FapBankPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.FapBankResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.FapCcPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.FapCcResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.FapDebitPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.FapDebitResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.OtpBankPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.OtpBankResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.OtpCcPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.OtpCcResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.OtpDebitPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.OtpDebitResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.VapBankPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.VapBankResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.VapCcPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.VapCcResident;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.VapDebitPm;
import com.paylease.app.qa.testbase.dataproviders.tieredfeestructure.VapDebitResident;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.testng.annotations.DataProvider;

public class TieredFeeStructureDataProvider {

  /**
   * Select a subset of test cases to provide for testing.
   *
   * @return test cases
   */
  @DataProvider(parallel = true)
  public Iterator<Object[]> provideResidentDataSample() {
    List<Object[]> testCases = new ArrayList<>();

    testCases.addAll(Arrays.asList(FapBankResident.testCases));
    testCases.addAll(Arrays.asList(FapCcResident.testCases));
    testCases.addAll(Arrays.asList(FapDebitResident.testCases));
    testCases.addAll(Arrays.asList(OtpBankResident.testCases));
    testCases.addAll(Arrays.asList(OtpCcResident.testCases));
    testCases.addAll(Arrays.asList(OtpDebitResident.testCases));
    testCases.addAll(Arrays.asList(VapBankResident.testCases));
    testCases.addAll(Arrays.asList(VapCcResident.testCases));
    testCases.addAll(Arrays.asList(VapDebitResident.testCases));

    return testCases.iterator();
  }

  /**
   * Provides test cases exclusive to ACC PM configuration.
   *
   * @return array of object array of test cases
   */
  @DataProvider(parallel = true)
  public Object[][] provideResidentDataAcc() {

    return new Object[][]{
        // Test Case ID, Payment method, Include base fee for resident, Include phone fee, Logas admin, Payment type, Round base fee, Use express pay, Include ach + base fee, choose PM Incur option

        // ACC PM
        {"tc6086", NEW_BANK, true, false, true, PaymentFlow.SCHEDULE_ONE_TIME, false, false, false, false}
    };
  }

  /**
   * Provides test case id and payment method.
   *
   * @return array of object array of test case id and payment method
   */
  @DataProvider(parallel = true)
  public Object[][] provideResidentPaymentMethod() {

    return new Object[][]{
        // Test case id, payment method
        {"tc6094", NEW_BANK},
        {"tc6095", NEW_DEBIT},
        {"tc6096", NEW_CREDIT},
        {"tc6100", NEW_BANK},
        {"tc6101", NEW_DEBIT},
        {"tc6102", NEW_CREDIT},
    };
  }

  /**
   * Select a subset of test cases to provide for testing.
   *
   * @return test cases
   */
  @DataProvider(parallel = true)
  public Iterator<Object[]> providePmDataSample() {
    List<Object[]> testCases = new ArrayList<>();

    testCases.addAll(Arrays.asList(FapBankPm.testCases));
    testCases.addAll(Arrays.asList(FapCcPm.testCases));
    testCases.addAll(Arrays.asList(FapDebitPm.testCases));
    testCases.addAll(Arrays.asList(OtpBankPm.testCases));
    testCases.addAll(Arrays.asList(OtpCcPm.testCases));
    testCases.addAll(Arrays.asList(OtpDebitPm.testCases));
    testCases.addAll(Arrays.asList(VapBankPm.testCases));
    testCases.addAll(Arrays.asList(VapCcPm.testCases));
    testCases.addAll(Arrays.asList(VapDebitPm.testCases));

    return testCases.iterator();
  }

  @DataProvider(parallel = true)
  public Object[][] providePmDataAcc() {

    return new Object[][]{
        // Test Case ID, Payment method, Include base fee for resident, Include phone fee, Logas admin, Payment type, Round base fee, Use express pay, Include ach + base fee, choose PM Incur option

        // ACC PM
        {"tc6088", NEW_BANK, true, false, true, PaymentFlow.SCHEDULE_ONE_TIME, false, false, false, false},
    };
  }

  /**
   * Provides test case id and payment method.
   *
   * @return array of object array of test case id and payment method
   */
  @DataProvider(parallel = true)
  public Object[][] providePmPaymentMethod() {

    return new Object[][]{
        // Test case id, payment method
        {"tc6087", NEW_BANK},
        {"tc6089", NEW_DEBIT},
        {"tc6090", NEW_CREDIT},
        {"tc6097", NEW_BANK},
        {"tc6098", NEW_DEBIT},
        {"tc6099", NEW_CREDIT},
    };
  }
}
