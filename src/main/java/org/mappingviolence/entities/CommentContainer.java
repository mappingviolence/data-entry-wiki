package org.mappingviolence.entities;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface CommentContainer {
  public Map<Field, List<Comment>> getComments(Field field);

  public List<Comment> getComments(String attributeId);
}
