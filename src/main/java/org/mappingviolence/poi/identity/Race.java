package org.mappingviolence.poi.identity;

public class Race extends SimpleIdentity<String> {

  @SuppressWarnings("unused")
  private Race() {
  }

  public Race(String value) {
    super("Race", value);
  }

}
