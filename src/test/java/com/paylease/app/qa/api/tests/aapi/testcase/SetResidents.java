package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.SetResidentsTransaction;

public class SetResidents extends BasicTestCase {

  private static final String VALID_FIELD_RESIDENT_ID = "residentId";
  private static final String VALID_FIELD_PROPERTY_ID = "propertyId";
  private static final String VALID_FIELD_FIRST_NAME = "firstName";
  private static final String VALID_FIELD_LAST_NAME = "lastName";

  private static final String VALID_FIELD_STREET_ADDRESS = "streetAddress";
  private static final String VALID_FIELD_CITY = "city";
  private static final String VALID_FIELD_STATE = "state";
  private static final String VALID_FIELD_POSTAL_CODE = "postalCode";
  private static final String VALID_FIELD_PHONE = "phone";
  private static final String VALID_FIELD_ALTERNATE_PHONE = "alternatePhone";
  private static final String VALID_FIELD_EMAIL = "email";
  private static final String VALID_FIELD_AMOUNT = "amount";

  private String propertyId;
  private String residentId;
  private String firstName;
  private String lastName;
  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String phone;
  private String alternatePhone;
  private String email;
  private String amount;
  private String hold;
  private String unit;
  private String secondaryResidentId;
  private String generateRegistrationUrl;
  private String isCorporateClient;
  private String isCorporateClientResident;
  private String corporateClientId;

  /**
   * Create a basic SetResidents test case with given values, and nulls for other fields. By itself,
   * this will not create a valid request
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   */
  public SetResidents(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null, null,
        null, null, null, null, null, null,
        null, null, null, null, null,
        null, null, null,
        null, null);
  }

  private SetResidents(String summary, ExpectedResponse expectedResponse, String residentId,
      String propertyId, String firstName, String lastName, String streetAddress, String city,
      String state, String postalCode, String phone, String alternatePhone, String email,
      String amount, String unit, String hold, String secondaryResidentId,
      String generateRegistrationUrl, String isCorporateClient, String isCorporateClientResident,
      String corporateClientId) {
    super(summary, expectedResponse);

    this.residentId = residentId;
    this.propertyId = propertyId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.phone = phone;
    this.alternatePhone = alternatePhone;
    this.email = email;
    this.amount = amount;
    this.hold = hold;
    this.unit = unit;
    this.secondaryResidentId = secondaryResidentId;
    this.generateRegistrationUrl = generateRegistrationUrl;
    this.isCorporateClient = isCorporateClient;
    this.isCorporateClientResident = isCorporateClientResident;
    this.corporateClientId = corporateClientId;
  }

  /**
   * Create a valid test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @return SetResidents test case that can be submitted as valid
   */
  public static SetResidents createValid(String summary, ExpectedResponse expectedResponse) {
    SetResidents validTestCase = new SetResidents(summary, expectedResponse);

    validTestCase.residentId = validTestCase.getValidValue(VALID_FIELD_RESIDENT_ID);
    validTestCase.firstName = validTestCase.getValidValue(VALID_FIELD_FIRST_NAME);
    validTestCase.lastName = validTestCase.getValidValue(VALID_FIELD_LAST_NAME);
    validTestCase.streetAddress = validTestCase.getValidValue(VALID_FIELD_STREET_ADDRESS);
    validTestCase.city = validTestCase.getValidValue(VALID_FIELD_CITY);
    validTestCase.state = validTestCase.getValidValue(VALID_FIELD_STATE);
    validTestCase.postalCode = validTestCase.getValidValue(VALID_FIELD_POSTAL_CODE);
    validTestCase.phone = validTestCase.getValidValue(VALID_FIELD_PHONE);
    validTestCase.email = validTestCase.getValidValue(VALID_FIELD_EMAIL);
    validTestCase.amount = validTestCase.getValidValue(VALID_FIELD_AMOUNT);

    return validTestCase;
  }

  /**
   * Create a test case with all fields set to empty.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @return SetResidents test case with mostly empty values (not null)
   */
  public static SetResidents createAllEmpty(String summary, ExpectedResponse expectedResponse) {
    SetResidents emptyTestCase = new SetResidents(summary, expectedResponse);

    emptyTestCase.setResidentId("")
        .setSecondaryResidentId("")
        .setFirstName("")
        .setLastName("")
        .setStreetAddress("")
        .setUnit("")
        .setCity("")
        .setState("")
        .setPostalCode("")
        .setPhone("")
        .setAlternatePhone("")
        .setEmail("")
        .setAmount("")
        .setHold("")
        .setGenerateRegistrationUrl("");

    return emptyTestCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_PROPERTY_ID, new String[]{
        "robsynergy1", "123QAgapi", "associaPropSAVEID01", "cincPropSAVEID01",
        "collierPropSAVEID01", "fiservPropSAVEID01", "onsitePropSAVEID01", "topsPropSAVEID01",
    });

    validValues.put(VALID_FIELD_RESIDENT_ID, new String[]{
        "robsynergy1", "123QAgapi", "associaPropSAVEID01", "cincPropSAVEID01",
        "collierPropSAVEID01", "fiservPropSAVEID01", "onsitePropSAVEID01", "topsPropSAVEID01",
    });

    validValues.put(VALID_FIELD_STREET_ADDRESS, new String[]{
        "123 Synergy Lane", "123 GAPI Lane", "3514 Tawny Mews", "4658 Iron Pond Vista",
        "4580 Velvet Horse Edge", "8504 Harvest Apple Round", "3751 Emerald Impasse",
        "8701 Sunny Rise Boulevard",
    });

    validValues.put(VALID_FIELD_CITY, new String[]{
        "San Diego", "Testland", "Shiawasseetown", "Monumental", "Do Stop", "Turnip Hole",
        "Nenahnezad", "Windthorst",
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
        "2222222222",
    });

    validValues.put(VALID_FIELD_ALTERNATE_PHONE, new String[]{
        "3333333333",
    });

    validValues.put(VALID_FIELD_EMAIL, new String[]{
        "test@paylease.com",
    });

    validValues.put(VALID_FIELD_AMOUNT, new String[]{
        "1300.33", "0", "0.1", "0.11",
    });

    validValues.put(VALID_FIELD_FIRST_NAME, new String[]{
        "CorpClient", "CorpResident", "Blah", "SetResidents", "Kenneth", "Aaron", "Cheryl", "Paula",
        "Joshua", "Bonnie",
    });

    validValues.put(VALID_FIELD_LAST_NAME, new String[]{
        "Tester", "Two", "Three", "Four", "Five", "Williams", "Gonzalez", "Sanders", "Russell",
        "Rivera", "Bryant",
    });

  }

  /**
   * Amount setter.
   *
   * @param amount amount
   * @return amount instance
   */
  public SetResidents setAmount(String amount) {
    this.amount = amount;

    return this;
  }

  /**
   * Hold setter.
   *
   * @param hold hold
   * @return hold instance
   */
  public SetResidents setHold(String hold) {
    this.hold = hold;

    return this;
  }

  /**
   * FirstName setter.
   *
   * @param firstName firstName
   * @return firstName instance
   */
  public SetResidents setFirstName(String firstName) {
    this.firstName = firstName;

    return this;
  }

  /**
   * LastName setter.
   *
   * @param lastName lastName
   * @return lastName instance
   */
  public SetResidents setLastName(String lastName) {
    this.lastName = lastName;

    return this;
  }

  /**
   * PropertyId setter.
   *
   * @param propertyId propertyId
   * @return propertyId instance
   */
  public SetResidents setPropertyId(String propertyId) {
    this.propertyId = propertyId;

    return this;
  }

  /**
   * StreetAddress setter.
   *
   * @param streetAddress streetAddress
   * @return streetAddress instance
   */
  public SetResidents setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;

    return this;
  }

  /**
   * Unit setter.
   *
   * @param unit unit
   * @return instance
   */
  public SetResidents setUnit(String unit) {
    this.unit = unit;

    return this;
  }

  /**
   * City setter.
   *
   * @param city city
   * @return city instance
   */
  public SetResidents setCity(String city) {
    this.city = city;

    return this;
  }

  /**
   * State setter.
   *
   * @param state state
   * @return state instance
   */
  public SetResidents setState(String state) {
    this.state = state;

    return this;
  }

  /**
   * PostalCode setter.
   *
   * @param postalCode postalCode
   * @return postalCode instance
   */
  public SetResidents setPostalCode(String postalCode) {
    this.postalCode = postalCode;

    return this;
  }

  /**
   * Phone setter.
   *
   * @param phone phone
   * @return phone instance
   */
  public SetResidents setPhone(String phone) {
    this.phone = phone;

    return this;
  }

  /**
   * Email setter.
   *
   * @param email email
   * @return email instance
   */
  public SetResidents setEmail(String email) {
    this.email = email;

    return this;
  }

  /**
   * ResidentId setter.
   *
   * @param residentId residentId
   * @return residentId instance
   */
  public SetResidents setResidentId(String residentId) {
    this.residentId = residentId;

    return this;
  }

  /**
   * AlternatePhone setter.
   *
   * @param alternatePhone alternatePhone
   * @return alternatePhone instance
   */
  public SetResidents setAlternatePhone(String alternatePhone) {
    this.alternatePhone = alternatePhone;

    return this;
  }

  /**
   * SecondaryResidentId setter.
   *
   * @param secondaryResidentId secondaryResidentId
   * @return secondaryResidentId instance
   */
  public SetResidents setSecondaryResidentId(String secondaryResidentId) {
    this.secondaryResidentId = secondaryResidentId;

    return this;
  }

  /**
   * GenerateRegistrationUrl setter.
   *
   * @param generateRegistrationUrl generateRegistrationUrl
   * @return generateRegistrationUrl instance
   */
  public SetResidents setGenerateRegistrationUrl(String generateRegistrationUrl) {
    this.generateRegistrationUrl = generateRegistrationUrl;

    return this;
  }

  /**
   * IsCorporateClient setter.
   *
   * @param isCorporateClient isCorporateClient
   * @return isCorporateClient instance
   */
  public SetResidents setIsCorporateClient(String isCorporateClient) {
    this.isCorporateClient = isCorporateClient;

    return this;
  }

  /**
   * IsCorporateClientResident setter.
   *
   * @param isCorporateClientResident isCorporateClientResident
   * @return isCorporateClientResident instance
   */
  public SetResidents setIsCorporateClientResident(String isCorporateClientResident) {
    this.isCorporateClientResident = isCorporateClientResident;

    return this;
  }

  /**
   * CorporateClientId setter.
   *
   * @param corporateClientId corporateClientId
   * @return corporateClientId instance
   */
  public SetResidents setCorporateClientId(String corporateClientId) {
    this.corporateClientId = corporateClientId;

    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    ParameterCollection parameters = getTransactionParameterCollection();

    Parameter residents = new Parameter();

    Parameter resident = new Parameter();
    resident.addParameter(SetResidentsTransaction.RESIDENT_ID, residentId);
    resident.addParameter(SetResidentsTransaction.PROPERTY_ID, propertyId);
    resident.addParameter(SetResidentsTransaction.FIRST_NAME, firstName);
    resident.addParameter(SetResidentsTransaction.LAST_NAME, lastName);
    resident.addParameter(SetResidentsTransaction.STREET_ADDRESS, streetAddress);
    resident.addParameter(SetResidentsTransaction.CITY, city);
    resident.addParameter(SetResidentsTransaction.STATE, state);
    resident.addParameter(SetResidentsTransaction.POSTAL_CODE, postalCode);
    resident.addParameter(SetResidentsTransaction.PHONE, phone);
    resident.addParameter(SetResidentsTransaction.ALTERNATE_PHONE, alternatePhone);
    resident.addParameter(SetResidentsTransaction.EMAIL, email);
    resident.addParameter(SetResidentsTransaction.AMOUNT, amount);
    resident.addParameter(SetResidentsTransaction.HOLD, hold);
    resident.addParameter(SetResidentsTransaction.UNIT, unit);
    resident.addParameter(SetResidentsTransaction.SECONDARY_RESIDENT_ID, secondaryResidentId);
    resident
        .addParameter(SetResidentsTransaction.GENERATE_REGISTRATION_URL, generateRegistrationUrl);

    Parameter corpClient = new Parameter();
    corpClient.addParameter(SetResidentsTransaction.IS_CORPORATE_CLIENT, isCorporateClient);
    corpClient.addParameter(SetResidentsTransaction.IS_CORPORATE_CLIENT_RESIDENT,
        isCorporateClientResident);
    corpClient.addParameter(SetResidentsTransaction.CORPORATE_CLIENT_ID, corporateClientId);

    resident.addParameter(SetResidentsTransaction.CORPORATE_CLIENT, corpClient);
    residents.addParameter(SetResidentsTransaction.RESIDENT, resident);

    parameters.put(SetResidentsTransaction.RESIDENTS, residents);

    AapiTransaction transaction = new SetResidentsTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
