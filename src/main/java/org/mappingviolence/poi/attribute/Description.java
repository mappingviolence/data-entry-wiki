package org.mappingviolence.poi.attribute;

import org.mappingviolence.entities.AbstractAttribute;
import org.mappingviolence.entities.Attribute;

public class Description extends AbstractAttribute<String> implements Attribute<String> {

  public Description(String description) {
    super("Description", description);
  }

}
