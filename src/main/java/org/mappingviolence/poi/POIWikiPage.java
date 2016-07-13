package org.mappingviolence.poi;

import java.util.List;

import org.bson.types.ObjectId;
import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.database.User;
import org.mappingviolence.database.Version;
import org.mappingviolence.database.WikiPage;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("poi-pages")
public class POIWikiPage extends WikiPage<OldPOI1> {
  private static final Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

  @Id
  private ObjectId id;

  private List<Key<Version<OldPOI1>>> previous;

  @SuppressWarnings("unused")
  private POIWikiPage() {
    super();
  }

  public POIWikiPage(User creator) {
    super(creator);
  }

  public POIWikiPage(User creator, Version<OldPOI1> firstVersion) {
    super(creator, firstVersion);
  }

  @Override
  public String getId() {
    return id.toHexString();
  }

  @Override
  public List<Version<OldPOI1>> getPreviousVersions() {
    return ds.getByKeys(previous);
  }
}
