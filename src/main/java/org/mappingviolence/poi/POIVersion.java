package org.mappingviolence.poi;

import org.bson.types.ObjectId;
import org.mappingviolence.user.User;
import org.mappingviolence.wiki.Version;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("pois")
public class POIVersion extends Version<POI> {
  @Id
  private ObjectId id;

  public POIVersion(User editor, POI data) {
    super(data, editor);
  }

  @Override
  public String getId() {
    return id.toHexString();
  }

}
