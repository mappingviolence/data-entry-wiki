package org.mappingviolence.database;

import java.util.Objects;

public class ClassId {
  private String id;
  private Class<?> clazz;

  public ClassId(String id, Class<?> clazz) {
    this.id = id;
    this.clazz = clazz;
  }

  public String getId() {
    return id;
  }

  public Class<?> getClassId() {
    return clazz;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, clazz);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ClassId) {
      ClassId c = (ClassId) o;
      return c.id.equals(id) && c.clazz.equals(clazz);
    } else {
      return false;
    }
  }
}
