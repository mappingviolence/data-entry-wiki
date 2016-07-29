package org.mappingviolence.servlet.ajax;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.servlet.Servlets;
import org.mappingviolence.wiki.Status;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class UpdateStatusServlet extends HttpServlet {
  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp) {
    Map<String, String> data = Servlets.parseData(req);
    String id = data.get("id");
    if (id == null || "".equals(id)) {
      Servlets.sendError(Servlets.Error.ID_MISSING, req, resp);
      return;
    }

    String statusStr = data.get("status");
    if (statusStr == null || "".equals(statusStr)) {
      Servlets.sendError(
          new Servlets.Error("No status found in request", HttpServletResponse.SC_OK),
          req,
          resp);
      return;
    }
    Status status;
    try {
      status = Enum.valueOf(Status.class, statusStr);
    } catch (IllegalArgumentException e) {
      Servlets.sendError(
          new Servlets.Error("Status not valid status", HttpServletResponse.SC_OK),
          req,
          resp);
      return;
    }

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    POIWikiPage poiW = ds.get(POIWikiPage.class, id);
    poiW.setStatus(status);
    String poiWId = (String) ds.save(poiW).getId();
    Servlets.sendSuccess(poiWId, HttpServletResponse.SC_OK, req, resp, "application/json");
    return;
  }
}
