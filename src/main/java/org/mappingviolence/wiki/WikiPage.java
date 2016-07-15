package org.mappingviolence.wiki;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mappingviolence.comment.Comment;
import org.mappingviolence.comment.CommentContainer;
import org.mappingviolence.user.User;

public abstract class WikiPage<T extends CommentContainer> implements CommentContainer {
  private User creator;
  private Date dateCreated;
  protected Version<T> current;
  private Status status;

  protected WikiPage() {
    dateCreated = new Date();
    status = Status.DRAFT;
  }

  public WikiPage(User creator) {
    this();
    this.creator = creator;
  }

  public WikiPage(User creator, Version<T> firstVersion) {
    this(creator);
    current = firstVersion;
  }

  public abstract String getId();

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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
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
