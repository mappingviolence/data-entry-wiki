package org.mappingviolence.poi;

public abstract class AbstractParent<T> {
  protected T pro;

  protected AbstractParent() {

  }

  public AbstractParent(T pro) {
    this();
    this.pro = pro;
  }
}
