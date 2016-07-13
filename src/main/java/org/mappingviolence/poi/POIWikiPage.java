package org.mappingviolence.poi;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.database.Version;
import org.mappingviolence.database.WikiPage;
import org.mappingviolence.entities.Comment;
import org.mappingviolence.user.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("poi-pages")
public class POIWikiPage extends WikiPage<POI> {
  private static final Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

  @Id
  private ObjectId id;

  private LinkedList<Key<Version<POI>>> previous;

  @SuppressWarnings("unused")
  private POIWikiPage() {
    super();
  }

  public POIWikiPage(User creator) {
    super(creator);
  }

  public POIWikiPage(User creator, Version<POI> firstVersion) {
    super(creator, firstVersion);
  }

  @Override
  public String getId() {
    return id.toHexString();
  }

  @Override
  public List<Version<POI>> getPreviousVersions() {
    return ds.getByKeys(previous);
  }

  @Override
  public Map<Field, List<Comment>> getComments(Field field) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Comment> getComments(String attributeId) {
    // TODO Auto-generated method stub
    return null;
  }
}
