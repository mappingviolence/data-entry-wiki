package org.mappingviolence.poi;

public class TestObject1<T> extends AbstractTest<T> {
  public String test;

  protected TestObject1() {
    super();
  }

  public TestObject1(String test, T data) {
    super(data, 3);
    this.test = test;
  }
}
