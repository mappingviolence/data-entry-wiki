package org.mappingviolence.poi;

import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("test")
public class TestObject extends AbstractParent<Integer> {
  @Id
  private String id;

  private StringTestObject1 name;

  private TestObject() {
    super();
    id = UUID.randomUUID().toString();
    this.pro = 1000;
  }

  public TestObject(String test) {
    this();
    this.name = new StringTestObject1("asdf", test);
  }

  public String getId() {
    return id;
  }

  public int getPro() {
    return pro;
  }

  public TestObject1<String> getData() {
    return name;
  }
}
