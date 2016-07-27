package org.mappingviolence.poi.identity;

import org.mappingviolence.form.SimpleFormField;

public class AbstractIdentity<T> extends SimpleFormField<T> implements Identity<T> {

  private String category;

  protected AbstractIdentity() {
  }

  public AbstractIdentity(String category, T value) {
    super(category, value);
    this.category = category;
  }

  @Override
  public String getCategory() {
    return category;
  }

}
