package com.paylease.app.qa.framework.api;

import java.util.ArrayList;
import java.util.HashMap;

public class Element {
  private String name;
  private String value;
  private ArrayList<Element> subElements;

  public Element(String name) {
    this(name, "");
  }

  Element(String name, String value) {
    this.name = name;
    this.value = value;
    this.subElements = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public void addSubElement(Element element) {
    subElements.add(element);
  }

  public ArrayList<Element> getSubElements() {
    return subElements;
  }
}
