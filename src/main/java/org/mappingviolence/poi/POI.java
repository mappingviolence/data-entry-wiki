package org.mappingviolence.poi;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.mappingviolence.database.WikiPage;
import org.mappingviolence.entities.Comment;
import org.mappingviolence.entities.CommentContainer;
import org.mappingviolence.entities.Commentable;
import org.mappingviolence.poi.attribute.Title;

public class POI implements CommentContainer {
  private Title title;

  public POI() {

  }

  public Commentable getTitle() {
    return title;
  }

  @Override
  public Map<Field, List<Comment>> getComments(Field field) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Comment> getComments(String attributeId) {
    try {
      return WikiPage.getComments(this, attributeId);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }
}
