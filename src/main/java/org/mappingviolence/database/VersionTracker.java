package org.mappingviolence.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

public class VersionTracker<T> {
  private Map<String, LinkedList<Key<T>>> versions;
  private Datastore ds;

  private VersionTracker() {
    versions = new HashMap<>();
  }

  public VersionTracker(String dbName) {
    this();
    ds = DatabaseConnection.getDatabase(dbName);
  }

  public VersionTracker(Datastore ds) {
    this();
    this.ds = ds;
  }

  public T getCurrent(String id, Class<T> clazz) {
    Key<T> key = versions.get(id).getFirst();
    T version = ds.getByKey(clazz, key);
    return version;
  }

  public T getVersion(String id, Class<T> clazz, int prev) {
    if (!versions.containsKey(id)) {
      return null;
    }
    List<Key<T>> previous = versions.get(id);
    int numberOfPrev = previous.size();
    if (prev >= numberOfPrev) {
      throw new IndexOutOfBoundsException("There are not that many previous versions.");
    }
    Key<T> key = versions.get(id).get(prev);
    T version = ds.get(clazz, key);
    return version;
  }

  public void addVersion(String id, Key<T> newKey) {
    if (!versions.containsKey(id)) {
      LinkedList<Key<T>> prev = new LinkedList<>();
      prev.add(newKey);
      versions.put(id, prev);
    } else {
      versions.get(id).addFirst(newKey);
    }
  }

  public T revert(String id, Class<T> clazz, int rollback) {
    LinkedList<Key<T>> prev = versions.get(id);
    if (rollback >= prev.size()) {
      throw new IndexOutOfBoundsException("There are not that many previous versions.");
    }
    for (int i = 0; i < rollback; i++) {
      prev.remove();
    }
    return this.getCurrent(id, clazz);
  }

  public T getOriginal(String id, Class<T> clazz) {
    Key<T> key = versions.get(id).getLast();
    T version = ds.getByKey(clazz, key);
    return version;
  }
}
