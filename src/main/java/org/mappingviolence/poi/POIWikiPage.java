package org.mappingviolence.poi;

import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.user.User;
import org.mappingviolence.wiki.Version;
import org.mappingviolence.wiki.WikiPage;
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
    return this.id.toHexString();
  }

  @Override
  public List<Version<POI>> getPreviousVersions() {
    return ds.getByKeys(previous);
  }

  @Override
  public void addVersion(Version<POI> newVersion) {
    /* Make sure that newVersion is saved to db */
    ds.save(newVersion);
    /*
     * Alternatively, we could check if it's in the db.
     * ds.exists(keyOrEntity)
     * Then based on that add it or continue
     */
    /* Add current version to list of previous versions */
    previous.addFirst(ds.getKey(current));
    /* Set current to newVersion */
    current = newVersion;
  }
}
