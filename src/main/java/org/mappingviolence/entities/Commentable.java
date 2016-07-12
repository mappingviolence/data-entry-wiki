package org.mappingviolence.entities;

import java.util.List;

public interface Commentable {
  public String getId();

  public List<Comment> getComments();

  public boolean addComment(Comment comment);

  public boolean removeComment(String commentId);
}
