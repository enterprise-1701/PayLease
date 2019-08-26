package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class CreditReportingOptOutDataProvider {

  private static final String ESCDNMAD0001_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESNAMEDP0001_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMDP0001_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMAR0001_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMFN0001_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMM20001_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMAR0004_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMFN0004_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMM20004_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMAR0007_ERROR_DESCRIPTION = "Invalid name entered due to \"AND\", \"&amp;\", or \"OR\".";
  private static final String ESCDNMFN0007_ERROR_DESCRIPTION = "Invalid name entered due to \"AND\", \"&amp;\", or \"OR\".";
  private static final String ESNAMEM20004_ERROR_DESCRIPTION = "Invalid name entered due to \"AND\", \"&amp;\", or \"OR\".";
  private static final String ESCDNMM20007_ERROR_DESCRIPTION = "Invalid name entered due to \"AND\", \"&amp;\", or \"OR\".";
  private static final String ESCDNMAR0010_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMFN0010_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMM20010_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMFN0011_ERROR_DESCRIPTION = "Invalid last name entered or invalid SSN entered";
  private static final String ESCDNMM20011_ERROR_DESCRIPTION = "Invalid last name entered or invalid SSN entered.";
  private static final String EAACCTNI0002_ERROR_DESCRIPTION = "The Credit Bureau encountered an error during processing.";
  private static final String ESCDNMFN0013_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String ESCDNMM20008_ERROR_DESCRIPTION = "Invalid name entered.";
  private static final String DATEOFBIRTH_ERROR_DESCRIPTION = "Invalid Date of Birth entered (less than 16 years of age).";


  @DataProvider(name = "creditReportingOptOut")
  public Object[][] dataOptOut() {

    return new Object[][]{
        {"tc01", "tc6721", ESCDNMAD0001_ERROR_DESCRIPTION},
        {"tc02", "tc6722", ESNAMEDP0001_ERROR_DESCRIPTION},
        {"tc03", "tc6723", ESCDNMDP0001_ERROR_DESCRIPTION},
        {"tc04", "tc6724", ESCDNMAR0001_ERROR_DESCRIPTION},
        {"tc05", "tc6725", ESCDNMFN0001_ERROR_DESCRIPTION},
        {"tc06", "tc6726", ESCDNMM20001_ERROR_DESCRIPTION},
        {"tc07", "tc6727", ESCDNMAR0004_ERROR_DESCRIPTION},
        {"tc08", "tc6728", ESCDNMFN0004_ERROR_DESCRIPTION},
        {"tc09", "tc6729", ESCDNMM20004_ERROR_DESCRIPTION},
        {"tc10", "tc6730", ESCDNMAR0007_ERROR_DESCRIPTION},
        {"tc11", "tc6731", ESCDNMFN0007_ERROR_DESCRIPTION},
        {"tc12", "tc6732", ESNAMEM20004_ERROR_DESCRIPTION},
        {"tc13", "tc6733", ESCDNMM20007_ERROR_DESCRIPTION},
        {"tc14", "tc6734", ESCDNMAR0010_ERROR_DESCRIPTION},
        {"tc15", "tc6735", ESCDNMFN0010_ERROR_DESCRIPTION},
        {"tc16", "tc6736", ESCDNMM20010_ERROR_DESCRIPTION},
        {"tc17", "tc6737", ESCDNMFN0011_ERROR_DESCRIPTION},
        {"tc18", "tc6738", ESCDNMM20011_ERROR_DESCRIPTION},
        {"tc19", "tc6739", EAACCTNI0002_ERROR_DESCRIPTION},
        {"tc20", "tc6740", ESCDNMFN0013_ERROR_DESCRIPTION},
        {"tc21", "tc6741", ESCDNMM20008_ERROR_DESCRIPTION},
        {"tc22", "tc6742", DATEOFBIRTH_ERROR_DESCRIPTION}
    };
  }
}
