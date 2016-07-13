package org.mappingviolence.poi.identity;

public class Race extends AbstractIdentity implements Identity {

  private Race() {
    super("Race");
  }

  public Race(String value) {
    this();
    super.value = value;
  }

  /**
   * It is possible to add restrictions on value data.
   */
}
