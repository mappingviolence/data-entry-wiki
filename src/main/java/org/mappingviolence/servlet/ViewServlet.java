// rough draft of view servlet:
package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class ViewServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    Map<String, String> poi1 = new HashMap<>();
    poi1.put("title", "POI 1");
    poi1.put("creator", "edward_jiao@brown.edu");
    poi1.put("description", "this is a test description");
    /*
     * List<String> list = new ArrayList<>();
     * list.add("A");
     * poi1.put("citations", list);
     */
    poi1.put("notes", "this is a test note");

    req.setAttribute("thisPOI", poi1);

    try {
      req.getRequestDispatcher("/WEB-INF/webapp/view.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      resp.setStatus(500);
      e.printStackTrace();
    }
  }
}
