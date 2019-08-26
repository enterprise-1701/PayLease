package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.AddPropertyTransaction;

public class AddProperty extends BasicTestCase {

  private static final String VALID_FIELD_PROPERTY_ID = "propertyId";
  private static final String VALID_FIELD_PROPERTY_NAME = "propertyName";
  private static final String VALID_FIELD_STREET_ADDRESS = "streetAddress";
  private static final String VALID_FIELD_CITY = "city";
  private static final String VALID_FIELD_STATE = "state";
  private static final String VALID_FIELD_POSTAL_CODE = "postalCode";
  private static final String VALID_FIELD_PHONE = "phone";
  private static final String VALID_FIELD_EMAIL = "email";
  private static final String VALID_FIELD_UNIT_COUNT = "unitCount";
  private static final String VALID_FIELD_FREQ_ID = "freqId";
  private static final String VALID_FIELD_LOGO_URL = "logoUrl";
  private static final String VALID_FIELD_VAR_NAME = "varName";
  private static final String VALID_FIELD_FIELD_NAME = "fieldNAme";
  private static final String VALID_FIELD_BANK_NAME = "bankName";
  private static final String VALID_FIELD_BANK_ACCOUNT_TYPE = "bankAccountType";
  private static final String VALID_FIELD_BANK_ACCOUNT_ROUTING = "bankAccountRouting";
  private static final String VALID_FIELD_BANK_ACCOUNT_NUMBER = "bankAccountNumber";

  private String propertyId;
  private String propertyName;
  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String phone;
  private String email;
  private String unitCount;
  private String freqId;
  private String logoUrl;
  private String fieldName;
  private String varName;
  private String bankName;
  private String bankAccountType;
  private String bankAccountRouting;
  private String bankAccountNumber;

  /**
   * Create a basic AddProperty test case with given values, and nulls for other fields. By
   * itself, this will not create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public AddProperty(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null, null,
        null, null, null, null, null, null,
        null, null, null, null, null,
        null, null, null);
  }

  private AddProperty(String summary, ExpectedResponse expectedResponse, String propertyId,
      String propertyName, String streetAddress, String city, String state, String postalCode,
      String phone, String email, String unitCount, String freqId, String logoUrl,
      String fieldName, String varName, String bankName, String bankAccountType,
      String bankAccountRouting, String bankAccountNumber) {
    super(summary, expectedResponse);

    this.propertyId = propertyId;
    this.propertyName = propertyName;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.phone = phone;
    this.email = email;
    this.unitCount = unitCount;
    this.freqId = freqId;
    this.logoUrl = logoUrl;
    this.fieldName = fieldName;
    this.varName = varName;
    this.bankName = bankName;
    this.bankAccountType = bankAccountType;
    this.bankAccountRouting = bankAccountRouting;
    this.bankAccountNumber = bankAccountNumber;

  }

  /**
   * Create a valid test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @return AddProperty test case that can be submitted as valid
   */
  public static AddProperty createValid(String summary, ExpectedResponse expectedResponse) {
    AddProperty validTestCase = new AddProperty(summary, expectedResponse);

    validTestCase.propertyId = validTestCase.getValidValue(VALID_FIELD_PROPERTY_ID);
    validTestCase.propertyName = validTestCase.getValidValue(VALID_FIELD_PROPERTY_NAME);
    validTestCase.streetAddress = validTestCase.getValidValue(VALID_FIELD_STREET_ADDRESS);
    validTestCase.city = validTestCase.getValidValue(VALID_FIELD_CITY);
    validTestCase.state = validTestCase.getValidValue(VALID_FIELD_STATE);
    validTestCase.postalCode = validTestCase.getValidValue(VALID_FIELD_POSTAL_CODE);
    validTestCase.phone = validTestCase.getValidValue(VALID_FIELD_PHONE);
    validTestCase.email = validTestCase.getValidValue(VALID_FIELD_EMAIL);
    validTestCase.unitCount = validTestCase.getValidValue(VALID_FIELD_UNIT_COUNT);
    validTestCase.freqId = validTestCase.getValidValue(VALID_FIELD_FREQ_ID);
    validTestCase.logoUrl = validTestCase.getValidValue(VALID_FIELD_LOGO_URL);
    validTestCase.fieldName = validTestCase.getValidValue(VALID_FIELD_FIELD_NAME);
    validTestCase.varName = validTestCase.getValidValue(VALID_FIELD_VAR_NAME);
    validTestCase.bankName = validTestCase.getValidValue(VALID_FIELD_BANK_NAME);
    validTestCase.bankAccountType = validTestCase.getValidValue(VALID_FIELD_BANK_ACCOUNT_TYPE);
    validTestCase.bankAccountRouting = validTestCase.getValidValue(VALID_FIELD_BANK_ACCOUNT_ROUTING);
    validTestCase.bankAccountNumber = validTestCase.getValidValue(VALID_FIELD_BANK_ACCOUNT_NUMBER);

    return validTestCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_PROPERTY_ID, new String[]{
        "associaAPI01", "cincAPI01", "collierAPI01", "fiservAPII01", "onsiteAPI01", "topsAPI01",
        "associaAPI02", "cincAPI02", "collierAPI02", "fiservAPII02", "onsiteAPI02", "topsAPI02",
        "associaAPI03", "cincAPI03", "collierAPI03", "fiservAPII03", "onsiteAPI03", "topsAPI03",
        "associaAPI04", "cincAPI04", "collierAPI04", "fiservAPII04", "onsiteAPI04", "topsAPI04",
        "associaAPI05", "cincAPI01", "collierAPI01", "fiservAPII01", "onsiteAPI01", "topsAPI01",
        "associaAPI06", "cincAPI06", "collierAPI06", "fiservAPII06", "onsiteAPI06", "topsAPI06",
        "associaAPI07", "cincAPI07", "collierAPI07", "fiservAPII07", "onsiteAPI07", "topsAPI07",
        "associaAPI08", "cincAPI08", "collierAPI08", "fiservAPII08", "onsiteAPI08", "topsAPI08",
        "associaAPI09", "cincAPI09", "collierAPI09", "fiservAPII09", "onsiteAPI09", "topsAPI09",
        "associaPropSAVEID01", "cincPropSAVEID01", "collierPropSAVEID01", "fiservAPII01",
        "onsitePropSAVEID01", "topsPropSAVEID01", "associaAPID1", "cincAPID1", "collierAPID1",
        "fiservAPIID1", "onsiteAPIID1", "topsAPID1","associaAPID0", "cincAPID0", "collierAPID0",
        "fiservAPIID0", "onsiteAPIID0", "topsAPID0",
    });

    validValues.put(VALID_FIELD_PROPERTY_NAME, new String[]{
        "1 API lane", "2 API lane", "3 API lane", "4 API lane", "5 API lane", "6 API lane",
        "7 API lane", "8 API lane", "9 API lane", "10 API lane", "11 API lane", "12 API lane",
        "13 API lane", "14 API lane", "15 API lane", "16 API lane", "17 API lane", "18 API lane",
        "19 API lane", "20 API lane", "21 API lane", "22 API lane", "23 API lane", "24 API lane",
        "25 API lane", "26 API lane", "27 API lane", "28 API lane", "29 API lane", "30 API lane",
        "31 API lane", "32 API lane", "33 API lane", "34 API lane", "35 API lane", "36 API lane",
        "31 API lane", "32 API lane", "33 API lane", "34 API lane", "35 API lane", "36 API lane",
        "37 API lane", "38 API lane", "39 API lane", "40 API lane", "41 API lane", "42 API lane",
        "43 API lane", "44 API lane", "45 API lane", "46 API lane", "47 API lane", "48 API lane",
    });

    validValues.put(VALID_FIELD_STREET_ADDRESS, new String[]{
        "1 API lane", "2 API lane", "3 API lane", "4 API lane", "5 API lane", "6 API lane",
        "7 API lane", "8 API lane", "9 API lane", "10 API lane", "11 API lane", "12 API lane",
        "13 API lane", "14 API lane", "15 API lane", "16 API lane", "17 API lane", "18 API lane",
        "19 API lane", "20 API lane", "21 API lane", "22 API lane", "23 API lane", "24 API lane",
        "25 API lane", "26 API lane", "27 API lane", "28 API lane", "29 API lane", "30 API lane",
        "31 API lane", "32 API lane", "33 API lane", "34 API lane", "35 API lane", "36 API lane",
        "31 API lane", "32 API lane", "33 API lane", "34 API lane", "35 API lane", "36 API lane",
        "37 API lane", "38 API lane", "39 API lane", "40 API lane", "41 API lane", "42 API lane",
        "43 API lane", "44 API lane", "45 API lane", "46 API lane", "47 API lane", "48 API lane",
    });

    validValues.put(VALID_FIELD_CITY, new String[]{
        "San Diego", "Cavetown", "Connoquenessing", "Suquamish", "Yellow Hammer", "Mounds",
        "Pothook", "Bat Cave", "Friendsville", "Maidstone", "Welagamika", "Winneboujou", "Una",
        "Busthead", "Third Cliff", "Ronkonkoma", "Gunsight", "Lipps", "Coleville", "Shadow Gate",
        "Chickasawba", "Good Thunder", "Ohogamiut", "Hardscrabble", "Corner", "Black Wolf",
        "Stovepipe", "Glasgow", "Enterprise", "Neilburg", "Shivwits", "Tiptop", "Deputy",
        "Hepburn", "Flag of Regina", "Deadman", "Crossing", "Poysippi", "Codette", "Galilee",
        "Miami", "Beach Clover",
    });

    validValues.put(VALID_FIELD_STATE, new String[]{
        "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA", "ID", "IL",
        "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE",
        "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT",
        "VA", "VT", "WA", "WI", "WV", "WY"
    });

    validValues.put(VALID_FIELD_POSTAL_CODE, new String[]{
        "52081", "59327", "38275", "48666", "62358", "39801", "19023", "37519", "82789", "28282",
        "19752", "3659", "34480", "29953", "3047", "27315", "49608", "71938", "88956", "29563",
        "29791", "81493", "83954", "81042", "49629", "96857", "35950", "98442", "98727", "36049",
        "94904", "89105", "83869", "19951", "71857", "82114", "41210", "2590", "36175", "45993",
        "1611", "4485", "68208",
    });

    validValues.put(VALID_FIELD_PHONE, new String[]{
        "760-711-1111",
    });

    validValues.put(VALID_FIELD_EMAIL, new String[]{
        "test@test.com",
    });

    validValues.put(VALID_FIELD_UNIT_COUNT, new String[]{
        "0", "1", "2", "10", "100", "1000", "10000", "16777215", "200000000",
    });

    validValues.put(VALID_FIELD_FREQ_ID, new String[]{
        "3", "4", "1", "2",
    });

    validValues.put(VALID_FIELD_LOGO_URL, new String[]{
        "https://paylease.com/image.png",
    });

    validValues.put(VALID_FIELD_FIELD_NAME, new String[]{
        "Lease Payment",
    });

    validValues.put(VALID_FIELD_VAR_NAME, new String[]{
        "lease_payment",
    });

    validValues.put(VALID_FIELD_BANK_NAME, new String[]{
        "API-JP Morgan Chase", "API-USAA", "API-Bank Of America",
        "API-San Diego County Credit Union", "API-Navy Federal", "API-Pacific Marine"
    });

    validValues.put(VALID_FIELD_BANK_ACCOUNT_TYPE, new String[]{
        "Checking", "Savings"
    });

    validValues.put(VALID_FIELD_BANK_ACCOUNT_ROUTING, new String[]{
        "490000018",
    });

    validValues.put(VALID_FIELD_BANK_ACCOUNT_NUMBER, new String[]{
        "8335939580", "1508579796", "3250332230", "8242745794", "8198956935", "5781725949",
    });
  }

  /**
   * PropertyId setter.
   *
   * @param propertyId propertyId
   * @return propertyId instance
   */
  public AddProperty setPropertyId(String propertyId) {
    this.propertyId = propertyId;

    return this;
  }

  /**
   * PropertyName setter.
   *
   * @param propertyName propertyName
   * @return propertyName instance
   */
  public AddProperty setPropertyName(String propertyName) {
    this.propertyName = propertyName;

    return this;
  }

  /**
   * StreetAddress setter.
   *
   * @param streetAddress streetAddress
   * @return streetAddress instance
   */
  public AddProperty setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;

    return this;
  }

  /**
   * City setter.
   *
   * @param city city
   * @return city instance
   */
  public AddProperty setCity(String city) {
    this.city = city;

    return this;
  }

  /**
   * State setter.
   *
   * @param state state
   * @return state instance
   */
  public AddProperty setState(String state) {
    this.state = state;

    return this;
  }

  /**
   * PostalCode setter.
   *
   * @param postalCode postalCode
   * @return postalCode instance
   */
  public AddProperty setPostalCode(String postalCode) {
    this.postalCode = postalCode;

    return this;
  }

  /**
   * Phone setter.
   *
   * @param phone phone
   * @return phone instance
   */
  public AddProperty setPhone(String phone) {
    this.phone = phone;

    return this;
  }

  /**
   * Email setter.
   *
   * @param email email
   * @return email instance
   */
  public AddProperty setEmail(String email) {
    this.email = email;

    return this;
  }

  /**
   * UnitCount setter.
   *
   * @param unitCount unitCount
   * @return unitCount instance
   */
  public AddProperty setUnitCount(String unitCount) {
    this.unitCount = unitCount;

    return this;
  }

  /**
   * FreqId setter.
   *
   * @param freqId freqId
   * @return freqId instance
   */
  public AddProperty setFreqId(String freqId) {
    this.freqId = freqId;

    return this;
  }

  /**
   * LogoUrl setter.
   *
   * @param logoUrl logoUrl
   * @return logoUrl instance
   */
  public AddProperty setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;

    return this;
  }

  /**
   * FieldName setter.
   *
   * @param fieldName fieldName
   * @return fieldName instance
   */
  public AddProperty setFieldName(String fieldName) {
    this.fieldName = fieldName;

    return this;
  }

  /**
   * VarName Id setter.
   *
   * @param varName varName
   * @return varName instance
   */
  public AddProperty setVarName(String varName) {
    this.varName = varName;

    return this;
  }

  /**
   * BankName setter.
   *
   * @param bankName bankName
   * @return bankName instance
   */
  public AddProperty setBankName(String bankName) {
    this.bankName = bankName;

    return this;
  }

  /**
   * BankAccountType setter.
   *
   * @param bankAccountType bankAccountType
   * @return bankAccountType instance
   */
  public AddProperty setBankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;

    return this;
  }

  /**
   * BankAccountRouting setter.
   *
   * @param bankAccountRouting bankAccountRouting
   * @return bankAccountRouting instance
   */
  public AddProperty setBankAccountRouting(String bankAccountRouting) {
    this.bankAccountRouting = bankAccountRouting;

    return this;
  }

  /**
   * BankAccountNumber setter.
   *
   * @param bankAccountNumber bankAccountNumber
   * @return bankAccountNumber instance
   */
  public AddProperty setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;

    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    Parameter property = new Parameter();

    property.addParameter(AddPropertyTransaction.PROPERTY_ID, propertyId);
    property.addParameter(AddPropertyTransaction.PROPERTY_NAME, propertyName);
    property.addParameter(AddPropertyTransaction.STREET_ADDRESS, streetAddress);
    property.addParameter(AddPropertyTransaction.CITY, city);
    property.addParameter(AddPropertyTransaction.STATE, state);
    property.addParameter(AddPropertyTransaction.POSTAL_CODE, postalCode);
    property.addParameter(AddPropertyTransaction.PHONE, phone);
    property.addParameter(AddPropertyTransaction.EMAIL, email);
    property.addParameter(AddPropertyTransaction.UNIT_COUNT, unitCount);
    property.addParameter(AddPropertyTransaction.FREQ_ID, freqId);
    property.addParameter(AddPropertyTransaction.LOGO_URL, logoUrl);

    Parameter paymentField = new Parameter();
    paymentField.addParameter(AddPropertyTransaction.FIELD_NAME, fieldName);
    paymentField.addParameter(AddPropertyTransaction.VAR_NAME, varName);
    paymentField.addParameter(AddPropertyTransaction.BANK_NAME, bankName);
    paymentField.addParameter(AddPropertyTransaction.BANK_ACCOUNT_TYPE, bankAccountType);
    paymentField.addParameter(AddPropertyTransaction.BANK_ACCOUNT_ROUTING, bankAccountRouting);
    paymentField.addParameter(AddPropertyTransaction.BANK_ACCOUNT_NUMBER, bankAccountNumber);

    Parameter paymentFields = new Parameter();
    paymentFields.addParameter(AddPropertyTransaction.PAYMENT_FIELD, paymentField);

    property.addParameter(AddPropertyTransaction.PAYMENT_FIELDS, paymentFields);

    ParameterCollection parameters = getTransactionParameterCollection();
    parameters.put(AddPropertyTransaction.PROPERTY, property);

    AapiTransaction transaction = new AddPropertyTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
