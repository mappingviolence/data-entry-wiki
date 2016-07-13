package org.mappingviolence.poi;

import org.bson.types.ObjectId;
import org.mappingviolence.database.User;
import org.mappingviolence.database.Version;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("pois")
public class POIVersion extends Version<OldPOI1> {
  @Id
  private ObjectId id;

  public POIVersion(User editor, OldPOI1 data) {
    super(data, editor);
  }

  @Override
  public String getId() {
    return id.toHexString();
  }

}
