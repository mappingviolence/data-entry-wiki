package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POI;
import org.mappingviolence.user.User;
import org.mappingviolence.wiki.Status;
import org.mappingviolence.wiki.WikiPage;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

@SuppressWarnings("serial")
public class DashboardServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    @SuppressWarnings("unchecked")
    Class<WikiPage<POI>> clazz = (Class<WikiPage<POI>>) (Object) (WikiPage.class);
    Query<WikiPage<POI>> query = ds.createQuery(clazz);
    User user = (User) req.getSession(false).getAttribute("currentUser");
    /*
     * this gets the current user, which is stored in the session
     */
    List<WikiPage<POI>> draftPOIs = query
        .filter("creator =", user)
        .filter("status =", Status.DRAFT)
        .asList();
    List<WikiPage<POI>> reviewPOIs = query
        .filter("creator =", user)
        .filter("status =", Status.IN_POOL)
        .asList();
    List<WikiPage<POI>> publishedPOIs = query
        .filter("creator =", user)
        .filter("status =", Status.PUBLISHED)
        .asList();

    req.setAttribute("draftPOIs", draftPOIs);
    req.setAttribute("reviewPOIs", reviewPOIs);
    req.setAttribute("pubishedPOIs", publishedPOIs);
    try {
      req.getRequestDispatcher("/WEB-INF/webapp/dashboard.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}