// rough draft of view servlet:
package org.mappingviolence.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.user.User;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class WikiPageServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");

    Servlets.sendSuccess(new POI(), 200, req, resp, "text/json");

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

    User currentUser = (User) req.getSession().getAttribute("currentUser");

    POIWikiPage poiW = new POIWikiPage(currentUser);

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    String id = (String) ds.save(poiW).getId();

    Servlets.sendSuccess(id, HttpServletResponse.SC_OK, req, resp, "text/json");
  }

  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp) {
    POI poi = Servlets.parseData(req, POI.class);

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    ds.save(poi);

    Servlets.sendSuccess(poi, 200, req, resp, "text/json");

    /*
     * Map<String, String> data = Servlets.parseData(req);
     * 
     * Collection<ValidationError> errors = new ArrayList<>();
     * 
     * String title = data.get("title");
     * String description = data.get("description");
     * String dateStr = data.get("date");
     * if (!Date.isValidDate(dateStr)) {
     * // TODO: Eventually put error messages into a file
     * errors.add(new ValidationError("date",
     * "Date does not match the specified gramar."));
     * }
     * String latStr = data.get("lat");
     * Double lat;
     * try {
     * lat = Double.parseDouble(latStr);
     * } catch (NumberFormatException e) {
     * errors.add(new ValidationError("lat", "Latitude is not a valid number."
     * ));
     * lat = null;
     * }
     * String lngStr = data.get("lng");
     * Double lng;
     * try {
     * lng = Double.parseDouble(lngStr);
     * } catch (NumberFormatException e) {
     * errors.add(new ValidationError("lng", "Longitude is not a valid number."
     * ));
     * lng = null;
     * }
     * if (!Location.isValidLocation(lat, lng)) {
     * errors.add(new ValidationError("location", "Location is not valid."));
     * }
     * String locationRationale = data.get("locationRationale");
     * 
     * String victimsStr = data.get("victims");
     * Iterable<String> victimIter = Splitter.on(";").split(victimsStr);
     * Collection<String> victimCol = new ArrayList<>();
     * Iterables.addAll(victimCol, victimIter);
     * Collection<Person> victims = new ArrayList<>();
     * for (String victimStr : victimCol) {
     * Person victim = new Person();
     * Map<String, String> identities =
     * Splitter.on(",").withKeyValueSeparator(":").split(victimStr);
     * for (Entry<String, String> identityEntry : identities.entrySet()) {
     * try {
     * Identity<?> identity = (Identity<?>) Class
     * .forName(identityEntry.getKey())
     * .getConstructor(String.class)
     * .newInstance(identityEntry.getValue());
     * victim.addIdentity(identity);
     * } catch (NoSuchMethodException | SecurityException |
     * ClassNotFoundException
     * | InstantiationException | IllegalAccessException |
     * IllegalArgumentException
     * | InvocationTargetException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * errors.add(new ValidationError("victims",
     * "Identity category given is not valid."));
     * throw new RuntimeException(e);
     * }
     * }
     * victims.add(victim);
     * }
     * 
     * 
     * if (!errors.isEmpty()) {
     * // TODO rename this method
     * Servlets.sendSuccess(errors, HttpServletResponse.SC_BAD_REQUEST, req,
     * resp, "text/json");
     * return;
     * }
     * 
     * POI poi = new POI();
     * 
     * if (title != null) {
     * poi.setTitle(title);
     * }
     * 
     * if (description != null) {
     * poi.setDescription(description);
     * }
     * 
     * Date date = new Date(dateStr);
     * if (date != null) {
     * poi.setDate(date);
     * }
     * 
     * Point location = Location.buildPoint(lat, lng);
     * if (location != null) {
     * poi.setLocation(location);
     * }
     * 
     * if (locationRationale != null) {
     * poi.setLocationRationale(locationRationale);
     * }
     * 
     * 
     * if (victims != null) {
     * poi.setVictims(victims);
     * }
     */
  }
}
