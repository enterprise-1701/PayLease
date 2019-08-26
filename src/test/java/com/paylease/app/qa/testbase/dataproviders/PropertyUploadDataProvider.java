package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class PropertyUploadDataProvider {

  /**
   * Data Provider for property upload test.
   */
  @DataProvider(name = "propertyUploadDataProvider", parallel = true)
  public Object[][] dataResident() {

    return new Object[][]{
        {
            "propertyUploadMisMatchPmId",
            "Property Manager ID on line: 1 does not match what was entered.",
            "This test is checking to make sure that when "
                + "a file is uploaded the first column of each row matches the value that is set "
                + "in the PM ID input field on the property upload form. We did not get a "
                + "failure so the validation in admin_functions/upload_properties_proc.php "
                + "may be broken",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n1234\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t98\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t15\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t264262942",
        },
        {
            "propertyUploadBadPmId",
            "Fatal Error! Could not locate PM ID in Database. Upload cancelled.",
            "This test is checking to make sure that when a Pm ID is entered into the "
                + "form PM ID field that it is an actual PM ID within our DB. Since this "
                + "test did not fail the function isValidPM may be broken within "
                + "upload_properties_proc or the PM ID we are using was added to the DB.",
            "property_upload_tc2",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t98\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t15\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t264262942",
        },
        {
            "propertyUploadBadHeaders",
            "Fatal Error! Invalid file format (check column headers). Upload cancelled.",
            "This test is checking to make sure that when "
                + "a file is uploaded the first row of the file contains a set of required "
                + "names specified in upload_properties_proc->validateHeaderRow(). "
                + "Since we did not get a failure during this test the aforementioned "
                + "function may be broken",
            "property_upload_tc1",
            "PM #\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n",
        },
        {
            "propertyUploadBadColumnCount",
            "missing the required number of fields (>14). Please correct and try again.",
            "This test is checking to make sure that when "
                + "a file is uploaded the first column of each row matches the value that is set "
                + "in the PM ID input field on the property upload form. We did not get a "
                + "failure so the validation in admin_functions/upload_properties_proc.php "
                + "may be broken",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t98\t2\t1.99\t0\t278\t",
        },
        {
            "propertyUploadBadPaymentFrequencyId",
            "Line 1 contains a frequency (17) that we do not understand",
            "This test is checking to make sure that when a file is uploaded the payment "
                + "frequency that is used in each line matches a set of known payment frequency "
                + "ids. Since this test did not fail upload_properties_proc->checkFrequencyAllowed "
                + "may be broken.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t98\t17\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t15\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t264262942",
        },
        {
            "propertyUploadReusedPropertyRefCode",
            "Line 2 contains a property reference code (278) that already exists for this PM",
            "This test is to confirm that when the file is uploaded and within the "
                + "file are multiple rows that use the same property reference code "
                + "we reject the lines with the duplicated property reference codes. "
                + "Since this failed "
                + "upload_properties_proc->checkThatPropertyReferenceIsNotAlreadyUsed "
                + "may be broken.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t98\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t15\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t264262942\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t98\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t15\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t264262942",
        },
        {
            "propertyUploadBadUnitCount",
            "Line 1 is missing the unit count. Unit count is a required field. Please "
                + "make sure that unit count is a number greater than zero",
            "This test is to verify that when the file is uploaded and an invalid unit "
                + "count is passed we reject it. Since this test failed the unit count "
                + "validation may be broken in upload_properties_proc",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t15\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t264262942\n",
        },
        {
            "propertyUploadMissingRoutingNumber",
            "Line 1 is missing a routing number",
            "This test is to verify that when a file is uploaded with a missing routing "
                + "number in any line we reject it. Since this test did not fail the "
                + "routing number check within upload_properties_proc may be broken.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t12\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t13\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t\t264262942\n",
        },
        {
            "propertyUploadBadRoutingNumber",
            "Line 1 contains a routing number (123456) that does not exist in our DB. "
                + "Please validate and use the adv. admin tool to correct the DB.",
            "This test is to verify that when a file is uploaded with a routing number "
                + "that Paylease does not know about we reject the file. Since this "
                + "test did not fail we may have either added the routing number to the "
                + "routing numbers table or the check is broken in upload_properties_proc.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t12\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t13\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t123456\t264262942\n",
        },
        {
            "propertyUploadMissingAccountNumber",
            "Line 1 is missing an account number",
            "This test is to verify that when a file is uploaded with an account number "
                + "missing from any line we reject it. Since this test did not fail the "
                + "account number validation may be broken within upload_properties_proc.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t12\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t13\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t\n",
        },
        {
            "propertyUploadBadPaymentFieldIdentifier",
            "Line 1 contains a payment field reference code (67) that is not allowed. "
                + "Please make sure you are only using these codes: 3, 4, 5, 6, 8, 9, "
                + "10, 11, 12, 13, 14, 15, 16, 17, 18, 19",
            "This test is to verify that when a file is uploaded with a payment field "
                + "identifier that is not within a static set defined in the code we "
                + "reject the file. Since this test did not fail the verification of "
                + "the payment field ID may be broken or someone has adjusted the list "
                + "of acceptable payment field IDs within upload_properties_proc.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t12\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t67\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t323095682\n",
        },
        {
            "propertyUploadMissingPaymentFieldIdentifier",
            "Line 1 is missing a payment field reference code",
            "This test is to verify that when a file is uploaded and is missing a payment "
                + "field identifier in any of the rows that we reject it. Since this test "
                + "did not fail the verification within upload_properties_proc may be broken.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n{{pm_id}}\t180.5039494\t123 Fake St.\tSan "
                + "Diego\tCA\t92121\t12\t2\t1.99\t0\t278\t3.5\t3.5\t\t13\tUnion Bank\t"
                + "000461567\t323095682\t\tUnion Bank\t000461567\t648539493\t5\tUnion "
                + "Bank\t000461567\t323095682\n",
        },
        {
            "propertyUploadNoPropertiesInFile",
            "Found no properties within uploaded file, Upload Cancelled",
            "This test is to verify that when a file is uploaded but does not contain "
                + "any rows after the header we reject it. Since this test did not fail "
                + "the check for the rows may be broken in upload_properties_proc.",
            "property_upload_tc1",
            "PM Number\tProperty Name\tAddress\tCity\tState\tZip\tUnit Count\tPayment "
                + "Frequency\tTransaction Fee\tTransaction Fee Pass Through\tReference "
                + "Id\tCredit Card Fee (%)\tAmex Fee (%)\tVISA Fee\t14-HOA Fees\tBank "
                + "Name\trouting\taccount\n",
        },
    };
  }
}
