package org.mappingviolence.poi;

public abstract class AbstractTest<T> {
  @SuppressWarnings("unused")
  private T hidden;
  protected int visible;

  protected AbstractTest() {
  }

  public AbstractTest(T hidden, int visible) {
    this.hidden = hidden;
    this.visible = visible;
  }

}
