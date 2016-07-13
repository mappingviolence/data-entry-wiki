package org.mappingviolence.poi;

import java.util.List;

import org.bson.types.ObjectId;
import org.mappingviolence.database.User;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

@Entity("pois")
public class OldPOI {
  @Id
  private ObjectId id;

  @Embedded
  private OldPOI1 current;

  private List<OldPOI1> previous;

  @Version
  public Long v;

  @SuppressWarnings("unused")
  private OldPOI() {
  }

  public OldPOI(OldPOI1 poi) {
    current = poi;
  }

  public String getId() {
    return id.toHexString();
  }

  public OldPOI1 getCurrentVersion() {
    return current;
  }

  public void addVersion(OldPOI1 updatedPOI) {
    previous.add(current);
    current = updatedPOI;
  }

  public User getCreator() {
    if (previous == null) {
      return current.getEditor();
    } else {
      return previous.get(0).getEditor();
    }
  }

  public User getUserLastModified() {
    return current.getEditor();
  }
}
