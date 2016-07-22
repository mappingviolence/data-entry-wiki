package org.mappingviolence.poi.identity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mappingviolence.comment.Comment;
import org.mappingviolence.comment.CommentContainer;

public class Person implements CommentContainer {
  @SuppressWarnings("rawtypes")
  private Collection<Identity> identities;

  public Person() {
    identities = new ArrayList<>();
  }

  public <T> boolean addIdentity(Identity<T> newIdentity) {
    return identities.add(newIdentity);
  }

  public <T> boolean removeIdentity(Identity<T> identity) {
    return identities.remove(identity);
  }

  public void setIdentities(@SuppressWarnings("rawtypes") Collection<Identity> identities) {
    this.identities = identities;
  }

  @SuppressWarnings("rawtypes")
  public Collection<Identity> getIdentities() {
    return identities;
  }

  @Override
  public Map<Field, List<Comment>> getComments(Field field) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Comment> getComments(String attributeId) {
    return CommentContainer.getComments(this, attributeId);
  }
}
