package org.mappingviolence.poi.attribute;

import org.mappingviolence.entities.AbstractFormField;
import org.mappingviolence.entities.FormField;

public class Description extends AbstractFormField<String> implements FormField<String> {

  public Description(String description) {
    super("Description", description);
  }

}
