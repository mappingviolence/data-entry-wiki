package org.mappingviolence.form;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mappingviolence.attribute.SimpleAttribute;
import org.mappingviolence.comment.Comment;

public class SimpleFormField<T> extends SimpleAttribute<T> implements FormField<T> {
  protected final String id;

  protected List<Comment> comments;

  protected SimpleFormField() {
    id = UUID.randomUUID().toString();
    comments = new ArrayList<>();
  }

  public SimpleFormField(String name, T value) {
    this();
    this.name = name;
    this.value = value;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public List<Comment> getComments() {
    return comments;
  }

  @Override
  public boolean addComment(Comment comment) {
    if (comment == null) {
      throw new IllegalArgumentException("comment cannot be null.");
    }
    return comments.add(comment);
  }

  @Override
  public boolean removeComment(String commentId) {
    return comments.remove(commentId);
  }

}
