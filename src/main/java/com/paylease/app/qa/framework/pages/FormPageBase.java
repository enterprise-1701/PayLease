package com.paylease.app.qa.framework.pages;

import java.util.HashMap;

public abstract class FormPageBase extends PageBase {

  public static final String FIELD_VALUE_EMPTY = "::empty::";

  protected HashMap<String, Object> prepFields;

  public FormPageBase() {
    super();
    prepFields = new HashMap<>();
  }

  public void prepField(String fieldName, Object value) {
    prepFields.put(fieldName, value);
  }

  protected abstract Object getValid(String fieldName);

  protected Object getFieldValue(String fieldName) {
    return getFieldValue(fieldName, "");
  }

  protected Object getFieldValue(String fieldName, Object emptyValue) {
    Object value;
    if (prepFields.containsKey(fieldName)) {
      value = prepFields.get(fieldName);
      if (value.equals(FIELD_VALUE_EMPTY)) {
        value = emptyValue;
      }
    } else {
      value = getValid(fieldName);
    }

    return value;
  }
}
