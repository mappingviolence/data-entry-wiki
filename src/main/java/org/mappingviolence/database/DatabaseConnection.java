package org.mappingviolence.database;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DatabaseConnection {
  private static final MongoClient CLIENT;
  private static final Morphia MORPHIA;

  static {
    CLIENT = new MongoClient();
    MORPHIA = new Morphia();

    // tell Morphia where to find your classes
    // can be called multiple times with different packages or classes
    // TODO
    MORPHIA.mapPackage("org.mappingviolence.database");
    MORPHIA.mapPackage("org.mappingviolence.user");
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
}
