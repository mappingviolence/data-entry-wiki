package org.mappingviolence.entities;

public abstract class AbstractAttribute<T> implements Attribute<T> {
  protected String name;
  protected T value;

  @SuppressWarnings("unused")
  private AbstractAttribute() {
  }

  protected AbstractAttribute(String name, T value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public T getValue() {
    return value;
  }
}
