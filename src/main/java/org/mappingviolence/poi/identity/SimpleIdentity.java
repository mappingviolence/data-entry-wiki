package org.mappingviolence.poi.identity;

import org.mappingviolence.form.SimpleFormField;

public class SimpleIdentity<T> extends SimpleFormField<T> implements Identity<T> {

  protected String category;

  protected T value;

  @SuppressWarnings("unused")
  private SimpleIdentity() {
  }

  protected SimpleIdentity(String category, T value) {
    super(category, value);
    this.category = category;
  }

  @Override
  public String getCategory() {
    return category;
  }

}
