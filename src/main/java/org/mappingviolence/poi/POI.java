package org.mappingviolence.poi;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

@Entity("pois")
public class POI {
  @Id
  private ObjectId id;

  private String title;

  private String subTitle;

  private String name;

  @Version
  public Long v;

  private POI() {
  }

  public POI(String name) {
    this.name = name;
  }

  public void setName(String newName) {
    name = newName;
  }

  public String getName() {
    return name;
  }
}
