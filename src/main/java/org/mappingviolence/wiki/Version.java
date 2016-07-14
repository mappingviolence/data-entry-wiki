package org.mappingviolence.wiki;

import java.util.Date;
import java.util.UUID;

import org.mappingviolence.user.User;

public class Version<T> {
  private String id;
  private T data;
  private User editor;
  private Date dateModified;

  private Version() {
    id = UUID.randomUUID().toString();
    dateModified = new Date();
  }

  public Version(T data, User editor) {
    this();
    this.data = data;
    this.editor = editor;
  }

  public String getId() {
    return id;
  }

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
