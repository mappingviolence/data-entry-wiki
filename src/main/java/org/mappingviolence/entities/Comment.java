package org.mappingviolence.entities;

import java.util.UUID;

import org.mappingviolence.user.User;

public class Comment {
  private final UUID id;

  private User author;

  private String commentText;

  private Comment() {
    id = UUID.randomUUID();
  }

  public Comment(User author) {
    this();
    this.author = author;
  }

  public Comment(User author, String commentText) {
    this();
    if (author == null) {
      throw new IllegalArgumentException("author cannot be null");
    }
    if (commentText == null) {
      throw new IllegalArgumentException("commentText cannot be null");
    }
    this.author = author;
    this.commentText = commentText;
  }

  public String getId() {
    return id.toString();
  }

  public User getAuthor() {
    return author;
  }

  public String getCommentText() {
    return commentText;
  }

  public void setCommentText(String commentText) {
    this.commentText = commentText;
  }
}
