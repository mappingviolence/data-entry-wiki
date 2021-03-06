package org.mappingviolence.data_entry_wiki.servlet.ajax;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.comment.Comment;
import org.mappingviolence.data_entry_wiki.servlet.Servlets;
import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.form.FormField;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIVersion;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.poi.identity.Identity;
import org.mappingviolence.poi.identity.Person;
import org.mappingviolence.user.User;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class CommentServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) {
    Map<String, String> data = Servlets.parseData(req);
    String poiId = req.getParameter("id");
    String formFieldId = data.get("formFieldId");
    String commentText = data.get("commentText");
    if (poiId == null) {
      throw new RuntimeException("poiId");
    }
    if (formFieldId == null) {
      throw new RuntimeException("formFieldId");
    }
    if (commentText == null) {
      throw new RuntimeException("commentText");
    }
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    POIWikiPage poiW = ds.get(POIWikiPage.class, poiId);
    if (poiW == null) {
      Servlets.sendError(Servlets.Error.ID_NOT_FOUND, req, resp);
      return;
    }
    Comment newComment = null;
    Field[] fields = poiW.getClass().getDeclaredFields();
    boolean success = false;
    for (Field field : fields) {
      if ("current".equals(field.getName())) {
        POIVersion poiVersion;
        POI poi;
        try {
          field.setAccessible(true);
          poiVersion = (POIVersion) field.get(poiW);
          poi = poiVersion.getData();
        } catch (IllegalArgumentException | IllegalAccessException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          throw new IllegalArgumentException(e1);
        }
        Field[] poiFields = poi.getClass().getDeclaredFields();
        for (Field poiField : poiFields) {
          if (success) {
            break;
          }
          Class<?> clazz = poiField.getType();
          Object formFieldObj;
          try {
            poiField.setAccessible(true);
            formFieldObj = clazz.cast(poiField.get(poi));
          } catch (IllegalArgumentException | IllegalAccessException e) {
            // ERROR
            e.printStackTrace();
            throw new RuntimeException(e);
          }
          if (formFieldObj instanceof FormField<?>) {
            FormField<?> formField = (FormField<?>) formFieldObj;
            if (formFieldId.equals(formField.getId())) {
              User currentUser = (User) req.getSession(false).getAttribute("currentUser");
              newComment = new Comment(currentUser, commentText);
              formField.addComment(newComment);
              ds.save(poiVersion);
              success = true;
              break;
            }
          } else if (formFieldObj instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) formFieldObj;
            for (Object obj : collection) {
              if (obj instanceof Person) {
                Person person = (Person) obj;
                for (Identity<?> identity : person.getIdentities()) {
                  if (formFieldId.equals(identity.getId())) {
                    User currentUser = (User) req.getSession(false).getAttribute("currentUser");
                    newComment = new Comment(currentUser, commentText);
                    identity.addComment(newComment);
                    ds.save(poiVersion);
                    success = true;
                    break;
                  }
                }
              } else if (obj instanceof FormField<?>) {
                FormField<?> formField = (FormField<?>) obj;
                if (formFieldId.equals(formField.getId())) {
                  User currentUser = (User) req.getSession(false).getAttribute("currentUser");
                  newComment = new Comment(currentUser, commentText);
                  formField.addComment(newComment);
                  ds.save(poiVersion);
                  success = true;
                  break;
                }
              }
            }
          }
        }
      }
    }

    if (!success || newComment == null) {
      throw new RuntimeException("no success");
    } else {
      Servlets.sendSuccess(newComment, HttpServletResponse.SC_OK, req, resp, "text/json");
      return;
    }
  }

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    Map<String, String> data = Servlets.parseData(req);
    String poiId = req.getParameter("id");
    String formFieldId = data.get("formFieldId");
    String commentId = data.get("commentId");
    if (poiId == null) {
      throw new RuntimeException("poiId");
    }
    if (formFieldId == null) {
      throw new RuntimeException("formFieldId");
    }
    if (commentId == null) {
      throw new RuntimeException("commentId");
    }

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    POIWikiPage poiW = ds.get(POIWikiPage.class, poiId);
    if (poiW == null) {
      Servlets.sendError(Servlets.Error.ID_NOT_FOUND, req, resp);
      return;
    }

    Field[] fields = poiW.getClass().getDeclaredFields();
    boolean success = false;
    for (Field field : fields) {
      if ("current".equals(field.getName())) {
        POIVersion poiVersion;
        POI poi;
        try {
          field.setAccessible(true);
          poiVersion = (POIVersion) field.get(poiW);
          poi = poiVersion.getData();
        } catch (IllegalArgumentException | IllegalAccessException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          throw new IllegalArgumentException(e1);
        }
        Field[] poiFields = poi.getClass().getDeclaredFields();
        for (Field poiField : poiFields) {
          if (success) {
            break;
          }
          Class<?> clazz = poiField.getType();
          Object formFieldObj;
          try {
            poiField.setAccessible(true);
            formFieldObj = clazz.cast(poiField.get(poi));
          } catch (IllegalArgumentException | IllegalAccessException e) {
            // ERROR
            e.printStackTrace();
            throw new RuntimeException(e);
          }
          if (formFieldObj instanceof FormField<?>) {
            FormField<?> formField = (FormField<?>) formFieldObj;
            if (formFieldId.equals(formField.getId())) {
              boolean removed = formField.removeComment(commentId);
              ds.save(poiVersion);
              success = removed;
              break;
            }
          } else if (formFieldObj instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) formFieldObj;
            for (Object obj : collection) {
              if (obj instanceof Person) {
                Person person = (Person) obj;
                for (Identity<?> identity : person.getIdentities()) {
                  if (formFieldId.equals(identity.getId())) {
                    boolean removed = identity.removeComment(commentId);
                    ds.save(poiVersion);
                    success = removed;
                    break;
                  }
                }
              } else if (obj instanceof FormField<?>) {
                FormField<?> formField = (FormField<?>) obj;
                if (formFieldId.equals(formField.getId())) {
                  boolean removed = formField.removeComment(commentId);
                  ds.save(poiVersion);
                  success = removed;
                  break;
                }
              }
            }
          }
        }
      }
    }

    if (!success) {
      throw new RuntimeException("no success");
    } else {
      Servlets.sendSuccess(poiId, HttpServletResponse.SC_OK, req, resp, "text/json");
      return;
    }
  }
}
