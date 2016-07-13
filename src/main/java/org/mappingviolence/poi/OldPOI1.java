package org.mappingviolence.poi;

import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.mappingviolence.database.User;
import org.mappingviolence.entities.Comment;
import org.mappingviolence.entities.CommentContainer;
import org.mappingviolence.entities.FormObject;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("pois")
public class OldPOI1 implements CommentContainer {

  @Id
  private ObjectId id;

  private User editor;

  private Title title;

  private Date date;

  private Description description;

  /* need to add location */

  private String locationRationale;

  private Collection<Person> victims;

  private Collection<Person> aggressors;

  private List<String> actions;

  private List<String> outcomes;

  private List<String> primarySources;

  private List<String> secondarySources;

  private List<String> additionalMedia;

  private String researchNotes;

  @SuppressWarnings("unused")
  private OldPOI1() {
  }

  public OldPOI1(User editor) {
    this.editor = editor;
  }

  public String getId() {
    return id.toHexString();
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
  }

  public User getEditor() {
    return editor;
  }

  public Title getTitle() {
    return title;
  }

  public void setTitle(Title title) {
    this.title = title;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getLocationRationale() {
    return locationRationale;
  }

  public void setLocationRationale(String locationRationale) {
    this.locationRationale = locationRationale;
  }

  public Description getDescription() {
    return description;
  }

  public void setDescription(Description description) {
    this.description = description;
  }

  public List<String> getActions() {
    return actions;
  }

  public void setActions(List<String> actions) {
    this.actions = actions;
  }

  public List<String> getOutcomes() {
    return outcomes;
  }

  public void setOutcomes(List<String> outcomes) {
    this.outcomes = outcomes;
  }

  public List<String> getPrimarySources() {
    return primarySources;
  }

  public void setPrimarySources(List<String> primarySources) {
    this.primarySources = primarySources;
  }

  public List<String> getSecondarySources() {
    return secondarySources;
  }

  public void setSecondarySources(List<String> secondarySources) {
    this.secondarySources = secondarySources;
  }

  public List<String> getAdditionalMedia() {
    return additionalMedia;
  }

  public void setAdditionalMedia(List<String> additionalMedia) {
    this.additionalMedia = additionalMedia;
  }

  public String getResearchNotes() {
    return researchNotes;
  }

  public void setResearchNotes(String researchNotes) {
    this.researchNotes = researchNotes;
  }

  @Override
  public List<Comment> getComments(String attributeId) {
    try {
      return FormObject.getComments(this, attributeId);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }
}
