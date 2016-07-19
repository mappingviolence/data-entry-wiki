// rough draft of view servlet:
package org.mappingviolence.servlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.form.ValidationError;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIVersion;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.poi.attribute.Location;
import org.mappingviolence.poi.date.Date;
import org.mappingviolence.user.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.geo.Point;

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

    Collection<ValidationError> errors = new ArrayList<>();

    String title = data.get("title");
    String description = data.get("description");
    String dateStr = data.get("date");
    if (!Date.isValidDate(dateStr)) {
      // TODO: Eventually put error messages into a file
      errors.add(new ValidationError("date", "Date does not match the specified gramar."));
    }
    String latStr = data.get("lat");
    Double lat;
    try {
      lat = Double.parseDouble(latStr);
    } catch (NumberFormatException e) {
      errors.add(new ValidationError("lat", "Latitude is not a valid number."));
      lat = null;
    }
    String lngStr = data.get("lng");
    Double lng;
    try {
      lng = Double.parseDouble(lngStr);
    } catch (NumberFormatException e) {
      errors.add(new ValidationError("lng", "Longitude is not a valid number."));
      lng = null;
    }
    if (!Location.isValidLocation(lat, lng)) {
      errors.add(new ValidationError("location", "Location is not valid."));
    }
    String locationRationale = data.get("locationRationale");

    if (!errors.isEmpty()) {
      // TODO rename this method
      Servlets.sendSuccess(errors, HttpServletResponse.SC_BAD_REQUEST, req, resp, "text/json");
      return;
    }

    POI poi = new POI();

    if (title != null) {
      poi.setTitle(title);
    }

    if (description != null) {
      poi.setDescription(description);
    }

    Date date = new Date(dateStr);
    if (date != null) {
      poi.setDate(date);
    }

    Point location = Location.buildPoint(lat, lng);
    if (location != null) {
      poi.setLocation(location);
    }

    if (locationRationale != null) {
      poi.setLocationRationale(locationRationale);
    }

    User currentUser = (User) req.getSession().getAttribute("currentUser");

    POIVersion poiV = new POIVersion(poi, currentUser);

    POIWikiPage poiW = new POIWikiPage(currentUser, poiV);

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    String id = (String) ds.save(poiW).getId();

    Servlets.sendSuccess(id, HttpServletResponse.SC_OK, req, resp, "text/json");
  }
}
