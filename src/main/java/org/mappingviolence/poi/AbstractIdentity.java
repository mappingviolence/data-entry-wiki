package org.mappingviolence.poi;

public abstract class AbstractIdentity implements Identity {

  protected final String CATEGORY;

  protected String value;

  @Override
  public String getCategory() {
    return CATEGORY;
  }

  @Override
  public String getValue() {
    return value;
  }

  protected AbstractIdentity(String category) {
    this.CATEGORY = category;
  }
}
