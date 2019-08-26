package com.paylease.app.qa.framework.api;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Transaction {

  protected ParameterCollection parameters;

  protected ArrayList<String> elementNames;

  protected HashMap<String, String> actionAttributes;

  /**
   * Transaction object.
   *
   * @param parameters parameters
   */
  public Transaction(ParameterCollection parameters) {
    this.parameters = parameters;
    this.elementNames = new ArrayList<>();
    this.actionAttributes = new HashMap<>();
  }

  /**
   * Transaction object with parameter collection.
   *
   * @param parameters Hashmap of parameters
   */
  public Transaction(HashMap<String, String> parameters) {
    ParameterCollection params = new ParameterCollection();

    for (String key : parameters.keySet()) {
      params.put(key, parameters.get(key));
    }

    this.parameters = params;
    this.elementNames = new ArrayList<>();
    this.actionAttributes = new HashMap<>();
  }

  protected void addElementName(String elementName) {
    elementNames.add(elementName);
  }

  public void addActionAttribute(String name, String value) {
    actionAttributes.put(name, value);
  }

  public HashMap<String, String> getActionAttributes() {
    return actionAttributes;
  }

  public ArrayList<Element> getElements() {
    return getElements(elementNames, parameters);
  }

  private ArrayList<Element> getElements(ArrayList<String> elementNames,
      ParameterCollection parameters) {
    ArrayList<Element> elements = new ArrayList<>();

    for (String elementName : elementNames) {
      if (parameters.containsKey(elementName) && parameters.get(elementName).getValue() != null) {
        elements.add(new Element(elementName, parameters.get(elementName).getValue()));
      }
    }
    return elements;
  }

  protected void setSubElements(Element element, ArrayList<String> elementNames,
      ParameterCollection parameters) {
    for (String elementName : elementNames) {
      if (parameters.containsKey(elementName) && parameters.get(elementName).getValue() != null) {
        element.addSubElement(new Element(elementName, parameters.get(elementName).getValue()));
      }
    }
  }
}
