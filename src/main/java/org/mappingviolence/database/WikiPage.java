package org.mappingviolence.database;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.mappingviolence.entities.Comment;
import org.mappingviolence.entities.CommentContainer;
import org.mappingviolence.entities.Commentable;
import org.mappingviolence.entities.FormObject;

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

  public static List<Comment> getComments(Object obj, String attributeId)
      throws IllegalAccessException {
    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.getType().equals(Commentable.class)) {
        try {
          Object fieldVal = field.get(obj);
          Commentable attribute = Commentable.class.cast(fieldVal);
          if (attributeId.equals(attribute.getId())) {
            return attribute.getComments();
          }
        } catch (IllegalAccessException | ClassCastException e) {
          e.printStackTrace();
        }
      }
    }
    for (Field field : fields) {
      if (field.getType().equals(CommentContainer.class)) {
        List<Comment> comments = FormObject.getComments(field.get(obj), attributeId);
        if (comments != null) {
          return comments;
        }
      }
    }
    return null;
  }

}
