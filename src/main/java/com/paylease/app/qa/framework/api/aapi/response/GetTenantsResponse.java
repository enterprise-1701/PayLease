package com.paylease.app.qa.framework.api.aapi.response;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.api.SpecificResponse;

public class GetTenantsResponse extends SpecificResponse {

  private static final String ELEMENT_PROPERTIES = "Properties";
  private static final String ELEMENT_TENANT_ID = "TenantID";
  private static final String ELEMENT_TENANT_FIRST_NAME = "TenantFirstName";

  private static final String XPATH_PROPERTY = ELEMENT_PROPERTIES + "/Property";
  private static final String XPATH_PROPERTY_TENANT = "Tenants/Tenant";

  public GetTenantsResponse(XMLBuilder response) {
    super(response);
  }

  /**
   * Get the TenantID contents for the given property and tenant in response.
   *
   * @param propertyIndex nth property to retrieve
   * @param tenantIndex nth tenant to retrieve
   * @return contents of TenantID element
   */
  public String getTenantId(int propertyIndex, int tenantIndex) {
    String xpath = "//" + XPATH_PROPERTY + "[" + propertyIndex + "]/"
        + XPATH_PROPERTY_TENANT + "[" + tenantIndex + "]/" + ELEMENT_TENANT_ID;

    return getElementText(xpath);
  }

  /**
   * Get the TenantFirstName contents for the given property and tenant in response.
   *
   * @param propertyIndex nth property to retrieve
   * @param tenantIndex nth tenant to retrieve
   * @return contents of TenantFirstName element
   */
  public String getTenantFirstName(int propertyIndex, int tenantIndex) {
    String xpath = "//" + XPATH_PROPERTY + "[" + propertyIndex + "]/"
        + XPATH_PROPERTY_TENANT + "[" + tenantIndex + "]/" + ELEMENT_TENANT_FIRST_NAME;

    return getElementText(xpath);
  }
}
