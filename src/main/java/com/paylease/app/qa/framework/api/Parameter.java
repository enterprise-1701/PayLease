package com.paylease.app.qa.framework.api;

public class Parameter {
  private String value;
  private ParameterCollection parameters;

  Parameter(String value) {
    this.value = value;
    this.parameters = new ParameterCollection();
  }

  public Parameter() {
    this(null);
  }

  public ParameterCollection getParameters() {
    return parameters;
  }

  public String getValue() {
    return value;
  }

  public void addParameter(String key, Parameter parameter) {
    this.parameters.put(key, parameter);
  }

  public void addParameter(String key, String value) {
    this.parameters.put(key, value);
  }
}
