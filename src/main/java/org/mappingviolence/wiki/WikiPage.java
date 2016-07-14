package org.mappingviolence.wiki;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mappingviolence.comment.Comment;
import org.mappingviolence.comment.CommentContainer;
import org.mappingviolence.user.User;

public abstract class WikiPage<T extends CommentContainer> implements CommentContainer {
  protected String id;
  private User creator;
  private Date dateCreated;
  protected Version<T> current;

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

  public abstract void addVersion(Version<T> newVersion);

  @Override
  public Map<Field, List<Comment>> getComments(Field field) {
    return current.getData().getComments(field);
  }

  @Override
  public List<Comment> getComments(String attributeId) {
    return CommentContainer.getComments(this, attributeId);
  }
}
