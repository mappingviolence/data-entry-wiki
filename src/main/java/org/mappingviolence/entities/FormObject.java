package org.mappingviolence.entities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.mappingviolence.database.User;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class FormObject<T extends CommentContainer> implements CommentContainer {
  @Id
  private Long id;

  private User creator;

  private Collection<User> editors;

  public Status status;

  private T current;

  private LinkedList<T> previousVersions;

  private FormObject() {
    editors = new ArrayList<>();
    status = Status.DEFAULT;
    previousVersions = new LinkedList<>();
  }

  public FormObject(User creator) {
    this();
    this.creator = creator;
    this.editors.add(creator);
  }

  public FormObject(User creator, User... additionalEditors) {
    this(creator);
    for (User editor : additionalEditors) {
      this.editors.add(editor);
    }
  }

  public FormObject(User creator, T initialObject) {
    this(creator);
    this.current = initialObject;
  }

  public FormObject(User creator, T initialObject, User... additionalEditors) {
    this(creator, additionalEditors);
    this.current = initialObject;
  }

  public T getCurrent() {
    return current;
  }

  public T getPrevious() {
    return previousVersions.getFirst();
  }

  public void addVersion(T newVersion) {
    previousVersions.add(newVersion);
    current = newVersion;
  }

  @Override
  public List<Comment> getComments(String attributeId) {
    return current.getComments(attributeId);
  }

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
