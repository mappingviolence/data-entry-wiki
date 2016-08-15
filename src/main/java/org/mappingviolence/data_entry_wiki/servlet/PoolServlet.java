package org.mappingviolence.data_entry_wiki.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POIWikiPage;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class PoolServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    List<POIWikiPage> poiPages = new ArrayList<>();
    poiPages = ds.find(POIWikiPage.class).filter("status !=", "DRAFT").asList();

    req.setAttribute("allPOIs", poiPages);

    Servlets.forward("/WEB-INF/webapp/pool.jsp", req, resp);
    return;
  }
}
