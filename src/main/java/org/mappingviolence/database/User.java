package org.mappingviolence.database;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.Query;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@Entity("users")
public class User {

  private static final Datastore db = DatabaseConnection.getDatabase("data-entry-wiki");

  @Id
  private ObjectId id;

  private String email;

  private Role role;

  @SuppressWarnings("unused")
  private User() {
  }

  public User(@Nonnull String email, @Nonnull Role role) {
    if (email == null || email.equals("")) {
      throw new IllegalArgumentException();
    }
    if (role == null) {
      throw new IllegalArgumentException();
    }
    this.email = email;
    this.role = role;
    db.save(this);
  }

  public static User getUser(String id) {
    return db.get(User.class, new ObjectId(id));
  }

  public static User getUserByEmail(String email) {
    return db.find(User.class).filter("email", email).get();
  }

  private User getUser() {
    return getUser(id.toHexString());
  }

  public boolean delete() {
    return db.delete(this).getN() > 0;
  }

  public String getId() {
    return id.toHexString();
  }

  public String getEmail() {
    if (email == null) {
      email = getUser().email;
    }
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    if (role == null) {
      role = getUser().role;
    }
    return role;
  }

  public boolean isViewer() {
    return getRole().ordinal() >= Role.VIEWER.ordinal();
  }

  public boolean isCommentor() {
    return getRole().ordinal() >= Role.COMMENTOR.ordinal();
  }

  public boolean isEditor() {
    return getRole().ordinal() >= Role.EDITOR.ordinal();
  }

  public boolean isAdmin() {
    return getRole().ordinal() >= Role.ADMIN.ordinal();
  }

  public boolean isPublisher() {
    return getRole().ordinal() >= Role.PUBLISHER.ordinal();
  }

  public enum Role implements Comparable<Role> {
    VIEWER, COMMENTOR, EDITOR, ADMIN, PUBLISHER;
  }

  public static boolean isAuthorizedUser(Payload payload) {
    String email = payload.getEmail();
    Query<User> results = db.find(User.class, "email", email);
    return results.fetchKeys().hasNext();
  }
}
