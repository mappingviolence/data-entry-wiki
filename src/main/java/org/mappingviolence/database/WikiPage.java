package org.mappingviolence.database;

import java.util.Date;
import java.util.List;

public abstract class WikiPage<T> {
  private String id;
  private User creator;
  private Date dateCreated;
  private Version<T> current;

  protected WikiPage() {
    dateCreated = new Date();
  }

  public WikiPage(User creator) {
    this();
    this.creator = creator;
  }

  public WikiPage(User creator, Version<T> firstVersion) {
    this(creator);
    current = firstVersion;
  }

  public String getId() {
    return id;
  }

  public User getCreator() {
    return creator;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public Version<T> getCurrentVersion() {
    return current;
  }

  public T getCurrentData() {
    return current.getData();
  }

  public abstract List<Version<T>> getPreviousVersions();

}
