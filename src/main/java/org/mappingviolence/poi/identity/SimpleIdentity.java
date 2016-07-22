package org.mappingviolence.poi.identity;

import org.mappingviolence.form.SimpleFormField;

public class SimpleIdentity<T> extends SimpleFormField<T> implements Identity<T> {

  private String category;

  protected SimpleIdentity() {
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
