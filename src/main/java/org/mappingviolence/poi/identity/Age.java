package org.mappingviolence.poi.identity;

public class Age extends SimpleIdentity<Integer> implements Identity<Integer> {

  @SuppressWarnings("unused")
  private Age() {
  }

  public Age(Integer age) {
    super("Age", age);
  }
}
