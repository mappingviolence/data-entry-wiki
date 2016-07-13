package org.mappingviolence.database;

import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DatabaseConnection {
  private static final MongoClient CLIENT;
  private static final Morphia MORPHIA;
  private static Map<ClassId, VersionTracker<?>> versionTrackers;

  static {
    CLIENT = new MongoClient();
    MORPHIA = new Morphia();

    // tell Morphia where to find your classes
    // can be called multiple times with different packages or classes
    // TODO
    MORPHIA.mapPackage("org.mappingviolence.database");
    MORPHIA.mapPackage("org.mappingviolence.poi");
  }

  private DatabaseConnection() {
  }

  public static Datastore getDatabase(String dbName) {
    // create the Datastore connecting to the default port on the local host
    Datastore datastore = MORPHIA.createDatastore(CLIENT, dbName);
    datastore.createQuery(WikiPage.class).filter("creator =", null);
    datastore.ensureIndexes();
    return datastore;
  }

  private static <T> VersionTracker<T> startVersionTracker(Class<T> clazz, String dbName) {
    VersionTracker<T> vt = new VersionTracker<>(getDatabase(dbName));
    versionTrackers.put(new ClassId(dbName, clazz), vt);
    return vt;
  }

  @SuppressWarnings("unchecked")
  public static <T> VersionTracker<T> getVersionTracker(Class<T> clazz, String dbName) {
    if (versionTrackers.containsKey(new ClassId(dbName, clazz))) {
      return (VersionTracker<T>) versionTrackers.get(new ClassId(dbName, clazz));
    } else {
      return startVersionTracker(clazz, dbName);
    }
  }

}
