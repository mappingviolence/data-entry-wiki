package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.user.User;
import org.mappingviolence.wiki.Status;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

@SuppressWarnings("serial")
public class DashboardServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    User user = (User) req.getSession(false).getAttribute("currentUser");

    List<POIWikiPage> draftPOIs = ds.createQuery(POIWikiPage.class)
        .filter("creator.email =", user.getEmail())
        .filter("status =", Status.DRAFT)
        .asList();
    List<POIWikiPage> reviewPOIs = ds.createQuery(POIWikiPage.class)
        .filter("creator.email =", user.getEmail())
        .filter("status =", Status.IN_POOL)
        .asList();
    List<POIWikiPage> publishedPOIs = ds.createQuery(POIWikiPage.class)
        .filter("creator.email =", user.getEmail())
        .filter("status =", Status.PUBLISHED)
        .asList();

    req.setAttribute("draftPOIs", draftPOIs);
    req.setAttribute("reviewPOIs", reviewPOIs);
    req.setAttribute("pubishedPOIs", publishedPOIs);
    req.setAttribute("currentUser", user);

    try {
      req.getRequestDispatcher("/WEB-INF/webapp/dashboard.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}