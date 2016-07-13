package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.OldPOI;
import org.mappingviolence.poi.OldPOI1;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class PoolServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    List<OldPOI1> pois = new ArrayList<>();
    ds.find(OldPOI.class).forEach((OldPOI poi) -> {
      pois.add(poi.getCurrentVersion());
    });

    req.setAttribute("allPOIs", pois);
    try {
      req.getRequestDispatcher("/WEB-INF/webapp/pool.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
