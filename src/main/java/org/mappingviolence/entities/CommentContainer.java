package org.mappingviolence.entities;

import java.util.List;

public interface CommentContainer {
  public List<Comment> getComments(String attributeId);
}
