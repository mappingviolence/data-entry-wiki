package org.mappingviolence.poi;

import org.bson.types.ObjectId;
import org.mappingviolence.user.User;
import org.mappingviolence.wiki.Version;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "pois")
public class POIVersion extends Version<POI> {
  @Id
  public String id;

  @Embedded
  public POI data;

  @SuppressWarnings("unused")
  private POIVersion() {
    super();
    id = new ObjectId().toHexString();
  }

  public POIVersion(POI data, User editor) {
    super(editor);
    id = new ObjectId().toHexString();
    this.data = data;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public POI getData() {
    return data;
  }
}
