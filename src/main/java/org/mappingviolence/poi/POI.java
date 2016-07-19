package org.mappingviolence.poi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mappingviolence.comment.Comment;
import org.mappingviolence.comment.CommentContainer;
import org.mappingviolence.form.FormField;
import org.mappingviolence.form.StringSimpleFormField;
import org.mappingviolence.poi.attribute.DateFormField;
import org.mappingviolence.poi.attribute.Description;
import org.mappingviolence.poi.attribute.Location;
import org.mappingviolence.poi.date.Date;
import org.mappingviolence.poi.identity.Person;
import org.mongodb.morphia.geo.Point;

public class POI implements CommentContainer {

  private StringSimpleFormField title;

  private Description description;

  private DateFormField date;

  private Location location;

  private StringSimpleFormField locationRationale;

  private Collection<Person> victims;

  private Collection<Person> aggressors;

  private Collection<StringSimpleFormField> tags;

  private Collection<StringSimpleFormField> primarySources;

  private Collection<StringSimpleFormField> secondarySources;

  private StringSimpleFormField researchNotes;

  public POI() {
  }

  public FormField<String> getTitle() {
    return title;
  }

  public void setTitle(String title) {
    if (this.title == null) {
      this.title = new StringSimpleFormField("Title", title);
    } else {
      this.title.setValue(title);
    }
  }

  public FormField<String> getDescription() {
    return description;
  }

  public void setDescription(String description) {
    if (this.description == null) {
      this.description = new Description(description);
    } else {
      this.description.setValue(description);
    }
  }

  public FormField<Date> getDate() {
    return date;
  }

  public void setDate(Date date) {
    if (this.date == null) {
      this.date = new DateFormField(date);
    } else {
      this.date.setValue(date);
    }
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Point point) {
    location.setValue(point);
  }

  public void setLocation(Double lat, Double lng) {
    location.setValue(Location.buildPoint(lat, lng));
  }

  public FormField<String> getLocationRationale() {
    return locationRationale;
  }

  public void setLocationRationale(String locationRationale) {
    this.locationRationale.setValue(locationRationale);
  }

  public Collection<Person> getVictims() {
    return victims;
  }

  public boolean addVictim(Person victim) {
    return victims.add(victim);
  }

  // idk how to do
  // remove because how do
  // you identify
  // unique persons

  public void setVictims(Collection<Person> victims) {
    this.victims = victims;
  }

  public Collection<Person> getAggressors() {
    return aggressors;
  }

  public boolean addAggressor(Person aggressor) {
    return aggressors.add(aggressor);
  }

  public void setAggressors(Collection<Person> aggressors) {
    this.aggressors = aggressors;
  }

  public Collection<FormField<String>> getPrimarySources() {
    Collection<FormField<String>> pSources = new ArrayList<>();
    pSources.addAll(this.primarySources);
    return pSources;
  }

  public boolean addPrimarySource(String primarySource) {
    return primarySources.add(new StringSimpleFormField("Primary Source", primarySource));
  }

  // idk remove

  public void setPrimarySources(Collection<String> primarySources) {
    this.primarySources.clear();
    for (String primarySource : primarySources) {
      this.primarySources.add(new StringSimpleFormField("Primary Source", primarySource));
    }
  }

  public Collection<FormField<String>> getSecondarySources() {
    Collection<FormField<String>> sSources = new ArrayList<>();
    sSources.addAll(this.secondarySources);
    return sSources;
  }

  public boolean addSecondarySource(String SecondarySource) {
    return secondarySources.add(new StringSimpleFormField("Secondary Source", SecondarySource));
  }

  // idk remove

  public void setSecondarySources(Collection<String> SecondarySources) {
    this.secondarySources.clear();
    for (String SecondarySource : SecondarySources) {
      this.secondarySources.add(new StringSimpleFormField("Secondary Source", SecondarySource));
    }
  }

  public Collection<FormField<String>> getTags() {
    Collection<FormField<String>> tags = new ArrayList<>();
    tags.addAll(this.tags);
    return tags;
  }

  public boolean addTag(String tag) {
    return tags.add(new StringSimpleFormField("Tag", tag));
  }

  // idk remove

  public void setTags(Collection<String> tags) {
    this.tags.clear();
    for (String tag : tags) {
      this.tags.add(new StringSimpleFormField("Tag", tag));
    }
  }

  public FormField<String> getResearchNotes() {
    return researchNotes;
  }

  public void setResearchNotes(String researchNotes) {
    if (this.researchNotes == null) {
      this.researchNotes = new StringSimpleFormField("Research Notes", researchNotes);
    } else {
      this.researchNotes.setValue(researchNotes);
    }
  }

  @Override
  public Map<Field, List<Comment>> getComments(Field field) {
    // TODO
    return null;
  }

  @Override
  public List<Comment> getComments(String attributeId) {
    return CommentContainer.getComments(this, attributeId);
  }
}
