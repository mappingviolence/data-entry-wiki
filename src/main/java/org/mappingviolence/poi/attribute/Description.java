package org.mappingviolence.poi.attribute;

import org.mappingviolence.form.FormField;
import org.mappingviolence.form.SimpleFormField;
import org.mappingviolence.markdown.Markdown;

public class Description extends SimpleFormField<String> implements FormField<String> {

  private static final int MAX_WORD_COUNT = 150;

  public Description(String description) {
    super("Description", description);
  }

  @Override
  public boolean isValidValue() {
    return Markdown.wordCount(value) <= MAX_WORD_COUNT;
  }

}