package org.mappingviolence.servlet.ajax;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.comment.Comment;
import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.form.FormField;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIVersion;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.servlet.Servlets;
import org.mappingviolence.user.User;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class CommentServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String poiId = req.getParameter("id");
    Map<String, String> data = Servlets.parseData(req);
    String formFieldId = data.get("id");
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
}
