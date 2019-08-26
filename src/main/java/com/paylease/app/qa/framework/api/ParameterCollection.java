package com.paylease.app.qa.framework.api;

import java.util.ArrayList;
import java.util.HashMap;

public class ParameterCollection {
  private HashMap<String, ArrayList<Parameter>> parameters;

  public ParameterCollection() {
    this.parameters = new HashMap<>();
  }

  public void put(String key, String value) {
    Parameter param = new Parameter(value);
    put(key, param);
  }

  /**
   * Function to store values for parameters.
   *
   * @param key unique key
   * @param parameter parameter
   */
  public void put(String key, Parameter parameter) {
    if (parameters.get(key) == null) {
      ArrayList<Parameter> params = new ArrayList<>();
      parameters.put(key, params);
    }
    ArrayList<Parameter> params = parameters.get(key);
    params.add(parameter);
    parameters.put(key, params);
  }

  boolean containsKey(String key) {
    return parameters.containsKey(key);
  }

  public Parameter get(String key) {
    return get(key, 0);
  }

  public Parameter get(String key, int index) {
    return parameters.get(key).get(index);
  }

  public int getParamCount(String key) {
    if (parameters.get(key) == null) {
      return 0;
    }

    return parameters.get(key).size();
  }
}
