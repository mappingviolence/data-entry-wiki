package org.mappingviolence.poi;

import org.bson.types.ObjectId;
import org.mappingviolence.user.User;
import org.mappingviolence.wiki.Version;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "pois")
public class POIVersion extends Version<POI> {
  @Id
  private ObjectId id;

  @SuppressWarnings("unused")
  private POIVersion() {
  }

  public POIVersion(POI data, User editor) {
    super(data, editor);
  }

  @Override
  public String getId() {
    return id.toHexString();
  }

}
