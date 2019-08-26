package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.SetInvoices;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class SetInvoicesTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "SetInvoices";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "SetInvoices"})
  public void setInvoices() throws ParseException {
    Logger.info("Set Invoices Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String residentId = testSetupPage.getString("residentId");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    DataHelper dataHelper = new DataHelper();

    UtilityManager utilityManager = new UtilityManager();

    String dateTodayFormatted = dateToday();
    Date date = utilityManager.stringToDate(dateTodayFormatted, DATE_FORMAT);
    String pastDateFormatted = addDays(date, -30);

    //SetInvoices,1,1,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,NULL,3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,0
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 1 - No additional payments",
                getExpectedResponse(gatewayErrors, "1"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
        )
    );

    //SetInvoices,1,2,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,NULL,3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,1
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 2 - 1 additional payment",
                getExpectedResponse(gatewayErrors, "1"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem(null,
                    null,
                    null,
                    "-0.01",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    dateTodayFormatted)
        )
    );

    //SetInvoices,1,3,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,NULL,3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,2
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 3 - 1 additional payment",
                getExpectedResponse(gatewayErrors, "1"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem(null,
                    null,
                    null,
                    "-25.46",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    dateTodayFormatted)
        )
    );

    //SetInvoices,1,4,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,NULL,3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,3
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 4 - 2 additional payments",
                getExpectedResponse(gatewayErrors, "1"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem(null,
                    null,
                    null,
                    "-485.00",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    "02/11/2016")
                .addLineItem(null,
                    null,
                    null,
                    "-145.00",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    "02/17/2016")
                .addLineItem("Credit Card Convenience Fee",
                    "12.00",
                    "1",
                    "12.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
        )
    );

    //SetInvoices,1,5,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,This resident is consistently late.,3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,1
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 5 - 1 additional payment with comment",
                getExpectedResponse(gatewayErrors, "1"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .setComments("This resident is consistently late.")
                .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem(null,
                    null,
                    null,
                    "-0.01",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    dateTodayFormatted)
        )
    );

    //SetInvoices,1,6,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam luctus posuere. Nunc ac felis eget risus fermentum semper ac ut lectus. Duis nunc nunc, pretium eu augue sit amet, consequat suscipit enim. Donec convallis mi non malesuada aliquet. Vivamus id viverra elit. Nunc vitae nunc enim. Nulla condimentum efficitur sodales. Cras dignissim vitae neque eget vehicula. Proin lacinia posuere tortor non feugiat. Suspendisse tincidunt condimentum facilisis. Maecenas semper lacinia nibh non fringilla. Sed consequat convallis nibh, vel cursus mi dapibus id. Sed dapibus eleifend tortor et malesuada. In sit amet malesuada felis, porttitor placerat enim. In maximus sed est ac mattis. In congue magna dictum nisi euismod lobortis non non arcu. Fusce felis erat, lacinia sed pharetra vitae, lacinia quis nulla. Phasellus justo tortor, sagittis non eros vel, imperdiet vulputate sem. Duis pharetra cursus hendrerit. In malesuada ultricies tellus, nec pretium odio aliquam sit amet. Morbi consectetur ante non est convallis, ut ullamcorper ex aliquam. Quisque eleifend, risus sit amet blandit placerat, ipsum mi iaculis tortor, eu scelerisque elit ligula eu felis. Nunc sodales odio sit amet erat imperdiet, non congue quam lacinia. Nulla facilisi. Phasellus id malesuada elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Interdum et malesuada fames ac ante ipsum primis in faucibus. Ut eu enim vel odio suscipit laoreet. Donec at gravida odio. Integer pharetra hendrerit dolor, sed aliquam orci ultrices eget. Morbi et fringilla quam. Integer arcu lorem, pharetra rhoncus imperdiet sit amet, tempor sed erat. Vivamus vitae leo ac odio laoreet malesuada at sit amet velit. Aenean vel nisi et nulla facilisis feugiat. Ut eu scelerisque nibh, bibendum laoreet est. Curabitur arcu nisl, df pharetra et leo ut, dignissim pellentesque ante. Maecenas dignissim ligula eu mi ullamcorper molestie. Vivamus orci nunc, facilisis non sodales id, vestibulum nec.",3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,1
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 6 - 1 additional payment with long comment",
                getExpectedResponse(gatewayErrors, "1"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .setComments("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam luctus posuere. Nunc ac felis eget risus fermentum semper ac ut lectus. Duis nunc nunc, pretium eu augue sit amet, consequat suscipit enim. Donec convallis mi non malesuada aliquet. Vivamus id viverra elit. Nunc vitae nunc enim. Nulla condimentum efficitur sodales. Cras dignissim vitae neque eget vehicula. Proin lacinia posuere tortor non feugiat. Suspendisse tincidunt condimentum facilisis. Maecenas semper lacinia nibh non fringilla. Sed consequat convallis nibh, vel cursus mi dapibus id. Sed dapibus eleifend tortor et malesuada. In sit amet malesuada felis, porttitor placerat enim. In maximus sed est ac mattis. In congue magna dictum nisi euismod lobortis non non arcu. Fusce felis erat, lacinia sed pharetra vitae, lacinia quis nulla. Phasellus justo tortor, sagittis non eros vel, imperdiet vulputate sem. Duis pharetra cursus hendrerit. In malesuada ultricies tellus, nec pretium odio aliquam sit amet. Morbi consectetur ante non est convallis, ut ullamcorper ex aliquam. Quisque eleifend, risus sit amet blandit placerat, ipsum mi iaculis tortor, eu scelerisque elit ligula eu felis. Nunc sodales odio sit amet erat imperdiet, non congue quam lacinia. Nulla facilisi. Phasellus id malesuada elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Interdum et malesuada fames ac ante ipsum primis in faucibus. Ut eu enim vel odio suscipit laoreet. Donec at gravida odio. Integer pharetra hendrerit dolor, sed aliquam orci ultrices eget. Morbi et fringilla quam. Integer arcu lorem, pharetra rhoncus imperdiet sit amet, tempor sed erat. Vivamus vitae leo ac odio laoreet malesuada at sit amet velit. Aenean vel nisi et nulla facilisis feugiat. Ut eu scelerisque nibh, bibendum laoreet est. Curabitur arcu nisl, df pharetra et leo ut, dignissim pellentesque ante. Maecenas dignissim ligula eu mi ullamcorper molestie. Vivamus orci nunc, facilisis non sodales id, vestibulum nec.")
                .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem(null,
                    null,
                    null,
                    "-0.01",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    dateTodayFormatted)
        )
    );

    //SetInvoices,1,7,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam luctus posuere. Nunc ac felis eget risus fermentum semper ac ut lectus. Duis nunc nunc, pretium eu augue sit amet, consequat suscipit enim. Donec convallis mi non malesuada aliquet. Vivamus id viverra elit. Nunc vitae nunc enim. Nulla condimentum efficitur sodales. Cras dignissim vitae neque eget vehicula. Proin lacinia posuere tortor non feugiat. Suspendisse tincidunt condimentum facilisis. Maecenas semper lacinia nibh non fringilla. Sed consequat convallis nibh, vel cursus mi dapibus id. Sed dapibus eleifend tortor et malesuada. In sit amet malesuada felis, porttitor placerat enim. In maximus sed est ac mattis. In congue magna dictum nisi euismod lobortis non non arcu. Fusce felis erat, lacinia sed pharetra vitae, lacinia quis nulla. Phasellus justo tortor, sagittis non eros vel, imperdiet vulputate sem. Duis pharetra cursus hendrerit. In malesuada ultricies tellus, nec pretium odio aliquam sit amet. Morbi consectetur ante non est convallis, ut ullamcorper ex aliquam. Quisque eleifend, risus sit amet blandit placerat, ipsum mi iaculis tortor, eu scelerisque elit ligula eu felis. Nunc sodales odio sit amet erat imperdiet, non congue quam lacinia. Nulla facilisi. Phasellus id malesuada elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Interdum et malesuada fames ac ante ipsum primis in faucibus. Ut eu enim vel odio suscipit laoreet. Donec at gravida odio. Integer pharetra hendrerit dolor, sed aliquam orci ultrices eget. Morbi et fringilla quam. Integer arcu lorem, pharetra rhoncus imperdiet sit amet, tempor sed erat. Vivamus vitae leo ac odio laoreet malesuada at sit amet velit. Aenean vel nisi et nulla facilisis feugiat. Ut eu scelerisque nibh, bibendum laoreet est. Curabitur arcu nisl, df pharetra et leo ut, dignissim pellentesque ante. Maecenas dignissim ligula eu mi ullamcorper molestie. Vivamus orci nunc, facilisis !@#$%^&*()/\.,\'\'\\""""",3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,1
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 7 - 1 additional payment with long comment including characters",
                getExpectedResponse(gatewayErrors, "1"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .setComments("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam luctus posuere. Nunc ac felis eget risus fermentum semper ac ut lectus. Duis nunc nunc, pretium eu augue sit amet, consequat suscipit enim. Donec convallis mi non malesuada aliquet. Vivamus id viverra elit. Nunc vitae nunc enim. Nulla condimentum efficitur sodales. Cras dignissim vitae neque eget vehicula. Proin lacinia posuere tortor non feugiat. Suspendisse tincidunt condimentum facilisis. Maecenas semper lacinia nibh non fringilla. Sed consequat convallis nibh, vel cursus mi dapibus id. Sed dapibus eleifend tortor et malesuada. In sit amet malesuada felis, porttitor placerat enim. In maximus sed est ac mattis. In congue magna dictum nisi euismod lobortis non non arcu. Fusce felis erat, lacinia sed pharetra vitae, lacinia quis nulla. Phasellus justo tortor, sagittis non eros vel, imperdiet vulputate sem. Duis pharetra cursus hendrerit. In malesuada ultricies tellus, nec pretium odio aliquam sit amet. Morbi consectetur ante non est convallis, ut ullamcorper ex aliquam. Quisque eleifend, risus sit amet blandit placerat, ipsum mi iaculis tortor, eu scelerisque elit ligula eu felis. Nunc sodales odio sit amet erat imperdiet, non congue quam lacinia. Nulla facilisi. Phasellus id malesuada elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Interdum et malesuada fames ac ante ipsum primis in faucibus. Ut eu enim vel odio suscipit laoreet. Donec at gravida odio. Integer pharetra hendrerit dolor, sed aliquam orci ultrices eget. Morbi et fringilla quam. Integer arcu lorem, pharetra rhoncus imperdiet sit amet, tempor sed erat. Vivamus vitae leo ac odio laoreet malesuada at sit amet velit. Aenean vel nisi et nulla facilisis feugiat. Ut eu scelerisque nibh, bibendum laoreet est. Curabitur arcu nisl, df pharetra et leo ut, dignissim pellentesque ante. Maecenas dignissim ligula eu mi ullamcorper molestie. Vivamus orci nunc, facilisis !@#$%^&*()/\\.,\\'\\'\\\\\"\"\"\"\"")
                 .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                     pastDateFormatted,
                     dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem(null,
                    null,
                    null,
                    "-0.01",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    dateTodayFormatted)
        )
    );

    //SetInvoices,95,8,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,NUM,oakwoodAPI2,NOW,NOW,Yes,Penthouse Test Suite,Tom Builder,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam luctus posuere. Nunc ac felis eget risus fermentum semper ac ut lectus. Duis nunc nunc, pretium eu augue sit amet, consequat suscipit enim. Donec convallis mi non malesuada aliquet. Vivamus id viverra elit. Nunc vitae nunc enim. Nulla condimentum efficitur sodales. Cras dignissim vitae neque eget vehicula. Proin lacinia posuere tortor non feugiat. Suspendisse tincidunt condimentum facilisis. Maecenas semper lacinia nibh non fringilla. Sed consequat convallis nibh, vel cursus mi dapibus id. Sed dapibus eleifend tortor et malesuada. In sit amet malesuada felis, porttitor placerat enim. In maximus sed est ac mattis. In congue magna dictum nisi euismod lobortis non non arcu. Fusce felis erat, lacinia sed pharetra vitae, lacinia quis nulla. Phasellus justo tortor, sagittis non eros vel, imperdiet vulputate sem. Duis pharetra cursus hendrerit. In malesuada ultricies tellus, nec pretium odio aliquam sit amet. Morbi consectetur ante non est convallis, ut ullamcorper ex aliquam. Quisque eleifend, risus sit amet blandit placerat, ipsum mi iaculis tortor, eu scelerisque elit ligula eu felis. Nunc sodales odio sit amet erat imperdiet, non congue quam lacinia. Nulla facilisi. Phasellus id malesuada elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Interdum et malesuada fames ac ante ipsum primis in faucibus. Ut eu enim vel odio suscipit laoreet. Donec at gravida odio. Integer pharetra hendrerit dolor, sed aliquam orci ultrices eget. Morbi et fringilla quam. Integer arcu lorem, pharetra rhoncus imperdiet sit amet, tempor sed erat. Vivamus vitae leo ac odio laoreet malesuada at sit amet velit. Aenean vel nisi et nulla facilisis feugiat. Ut eu scelerisque nibh, bibendum laoreet est. Curabitur arcu nisl, df pharetra et leo ut, dignissim pellentesque ante. Maecenas dignissim ligula eu mi ullamcorper molestie. Vivamus orci nunc, facilisis non sodales id, vestibulum nec. test",3 Bedroom Suite,292.36,30,8770.80,PAST,NOW,Parking,2.00,30,60.00,PAST,NOW,Pet Rent,5,30,150,PAST,NOW,1
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new SetInvoices(
                "Test case 8 - 1 additional payment with long comment exceeding 2000 characters",
                getExpectedResponse(gatewayErrors, "95"))
                .setInvoiceId(dataHelper.getInvoiceId())
                .setResidentId(residentId)
                .setInvoiceDate(dateTodayFormatted)
                .setDueDate(dateTodayFormatted)
                .setIncurFee("Yes")
                .setUnitType("Penthouse Test Suite")
                .setPreparedBy("Tom Builder")
                .setComments("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquam luctus posuere. Nunc ac felis eget risus fermentum semper ac ut A1258:AI1292 Duis nunc nunc, pretium eu augue sit amet, consequat suscipit enim. Donec convallis mi non malesuada aliquet. Vivamus id viverra elit. Nunc vitae nunc enim. Nulla condimentum efficitur sodales. Cras dignissim vitae neque eget vehicula. Proin lacinia posuere tortor non feugiat. Suspendisse tincidunt condimentum facilisis. Maecenas semper lacinia nibh non fringilla. Sed consequat convallis nibh, vel cursus mi dapibus id. Sed dapibus eleifend tortor et malesuada. In sit amet malesuada felis, porttitor placerat enim. In maximus sed est ac mattis. In congue magna dictum nisi euismod lobortis non non arcu. Fusce felis erat, lacinia sed pharetra vitae, lacinia quis nulla. Phasellus justo tortor, sagittis non eros vel, imperdiet vulputate sem. Duis pharetra cursus hendrerit. In malesuada ultricies tellus, nec pretium odio aliquam sit amet. Morbi consectetur ante non est convallis, ut ullamcorper ex aliquam. Quisque eleifend, risus sit amet blandit placerat, ipsum mi iaculis tortor, eu scelerisque elit ligula eu felis. Nunc sodales odio sit amet erat imperdiet, non congue quam lacinia. Nulla facilisi. Phasellus id malesuada elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Interdum et malesuada fames ac ante ipsum primis in faucibus. Ut eu enim vel odio suscipit laoreet. Donec at gravida odio. Integer pharetra hendrerit dolor, sed aliquam orci ultrices eget. Morbi et fringilla quam. Integer arcu lorem, pharetra rhoncus imperdiet sit amet, tempor sed erat. Vivamus vitae leo ac odio laoreet malesuada at sit amet velit. Aenean vel nisi et nulla facilisis feugiat. Ut eu scelerisque nibh, bibendum laoreet est. Curabitur arcu nisl, df pharetra et leo ut, dignissim pellentesque ante. Maecenas dignissim ligula eu mi ullamcorper molestie. Vivamus orci nunc, facilisis non sodales id, vestibulum nec. test")
                .addLineItem("3 Bedroom Suite",
                    "292.36",
                    "30",
                    "8770.80",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Parking",
                    "2.00",
                    "30",
                    "60.00",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem("Pet Rent",
                    "5",
                    "30",
                    "150",
                    "Charge",
                    pastDateFormatted,
                    dateTodayFormatted,
                    null,
                    null)
                .addLineItem(null,
                    null,
                    null,
                    "-0.01",
                    "Payment",
                    null,
                    null,
                    "Credit",
                    dateTodayFormatted)
        )
    );

    executeTests(testCases);
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private String dateToday() {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();

    return dateFormat.format(date);
  }

}

