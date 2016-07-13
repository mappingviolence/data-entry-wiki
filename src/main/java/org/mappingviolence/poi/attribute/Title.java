package org.mappingviolence.poi.attribute;

import org.mappingviolence.entities.AbstractFormField;
import org.mappingviolence.entities.FormField;

public class Title extends AbstractFormField<String> implements FormField<String> {

  public Title(String title) {
    super("Title", title);
  }

}
