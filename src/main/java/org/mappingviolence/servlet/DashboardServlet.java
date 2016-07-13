package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIVersion;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class PoolServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    Query<WikiPage> query = ds.createQuery(wikiPage.class)
    User user = (User) req.getSession(false).getAttribute("currentUser") //this gets the current user, which is stored in the session 
    List<POI> draftPOIs = query.filter("creator =", user).filter("status =", Status.DRAFT)
    List<POI> reviewPOIs = query.filter("creator =", user).filter("status =", Status.IN_POOL)
    List<POI> publishedPOIs = query.filter("creator =", user).filter("status =", Status.PUBLISHED)

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