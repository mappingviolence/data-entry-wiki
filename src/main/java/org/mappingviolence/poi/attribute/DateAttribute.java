package org.mappingviolence.poi.attribute;

import org.mappingviolence.form.FormField;
import org.mappingviolence.form.SimpleFormField;
import org.mappingviolence.poi.date.Date;

public class DateAttribute extends SimpleFormField<Date> implements FormField<Date> {

  public DateAttribute(Date date) {
    super("Date", date);
  }

  @Override
  public boolean isValidValue() {
    return value.isValid();
  }

}
