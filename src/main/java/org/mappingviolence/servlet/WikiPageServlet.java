// rough draft of view servlet:
package org.mappingviolence.servlet;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.user.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

@SuppressWarnings("serial")
public class WikiPageServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");

    if (id == null) {
      req.setAttribute("errorMessage", "There was no id parameter found in the request.");
      Servlets.forward("/WEB-INF/webapp/error.jsp", req, resp);
      return;
    }

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    POIWikiPage poiWikiPage = ds.get(POIWikiPage.class, id);

    if (poiWikiPage == null) {
      req.setAttribute("errorMessage", "The id that was sent not found in database.");
      Servlets.forward("/WEB-INF/webapp/error.jsp", req, resp);
      return;
    }

    req.setAttribute("thisPOI", poiWikiPage.getCurrentData());

    Servlets.forward("/WEB-INF/webapp/view.jsp", req, resp);
    return;
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) {
    Map<String, String> data = Servlets.parseData(req);

    String title = data.get("title");

    POI poi = new POI();
    poi.setTitle(title);
    poi.setDescription("this is a description");
    poi.setResearchNotes("research notes");

    User user = (User) req.getSession(false).getAttribute("currentUser");

    POIWikiPage wiki = new POIWikiPage(user);
    wiki.addVersion(poi, user);

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    Key<POIWikiPage> key = ds.save(wiki);
    String id = (String) key.getId();

    Servlets.sendSuccess(id, HttpServletResponse.SC_OK, req, resp, "text/json");
  }
}
