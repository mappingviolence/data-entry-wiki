// rough draft of view servlet:
package org.mappingviolence.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POIWikiPage;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class ViewServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    String id = req.getParameter("id");

    if (id == null || id.equals("")) {
      Servlets.sendError(Servlets.Error.ID_MISSING, req, resp);
      return;
    }

    POIWikiPage poi = ds.get(POIWikiPage.class, id);

    req.setAttribute("thisPOI", poi);

    try {
      req.getRequestDispatcher("/WEB-INF/webapp/view.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      Servlets.sendError(
          new Servlets.Error(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
          req,
          resp);
    }
  }
}
