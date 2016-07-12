package org.mappingviolence.entities;

import java.util.List;
import java.util.UUID;

public abstract class AbstractFormField<T> extends AbstractAttribute<T> implements FormField<T> {
  protected final UUID id;

  protected List<Comment> comments;

  protected AbstractFormField(String name, T value) {
    super(name, value);
    id = UUID.randomUUID();
  }

  @Override
  public String getId() {
    return id.toString();
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
