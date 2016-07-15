package org.mappingviolence.wiki;

import java.util.Date;

import org.mappingviolence.user.User;
import org.mongodb.morphia.annotations.Embedded;

public abstract class Version<T> {
  // This forces a reliance on mongodb
  @Embedded
  private T data;
  private User editor;
  private Date dateModified;

  protected Version() {
    dateModified = new Date();
  }

  public Version(T data, User editor) {
    this();
    this.data = data;
    this.editor = editor;
  }

  public abstract String getId();

  public T getData() {
    return data;
  }

  public User getEditor() {
    return editor;
  }

  public Date getDateModified() {
    return dateModified;
  }
}
