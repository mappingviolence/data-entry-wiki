package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIVersion;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.user.User;
import org.mappingviolence.poi.Query;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class DashboardServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    Query<POIWikiPage> query = ds.createQuery(POIWikiPage.class);
    User user = (User) req.getSession(false).getAttribute("currentUser"); //this gets the current user, which is stored in the session 
    List<POIWikiPage> draftPOIs = query.filter("creator =", user).filter("status =", Status.DRAFT).asList();
    List<POIWikiPage> reviewPOIs = query.filter("creator =", user).filter("status =", Status.IN_POOL).asList();
    List<POIWikiPage> publishedPOIs = query.filter("creator =", user).filter("status =", Status.PUBLISHED).asList();

    req.setAttribute("draftPOIs", draftPOIs);
    req.setAttribute("reviewPOIs", reviewPOIs);
    req.setAttribute("pubishedPOIs", publishedPOIs);
    req.setAttribute("user", user);
    try {
      req.getRequestDispatcher("/WEB-INF/webapp/dashboard.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}