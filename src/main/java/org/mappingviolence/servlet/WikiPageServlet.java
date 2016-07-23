// rough draft of view servlet:
package org.mappingviolence.servlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.poi.POI;
import org.mappingviolence.poi.POIVersion;
import org.mappingviolence.poi.POIWikiPage;
import org.mappingviolence.servlet.Servlets.Error;
import org.mappingviolence.user.User;
import org.mappingviolence.wiki.Version;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class WikiPageServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");

    // Servlets.sendSuccess(new POI(), 200, req, resp, "text/json");

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
    if (currentUser == null) {
      // TODO: Update error message
      Servlets.sendError(Error.ID_NOT_FOUND, req, resp);
    }

    POIWikiPage poiW = new POIWikiPage(currentUser);

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    String id = (String) ds.save(poiW).getId();

    Servlets.sendSuccess(id, HttpServletResponse.SC_OK, req, resp, "text/json");
    /*
     * POI poi = new POI();
     * poi.setTitle("Sample Title: Test 1");
     * poi.setDate(new Date("2015"));
     * poi.setDescription("This is a");
     * poi.setLocation(15.0, 23.54);
     * poi.setLocationRationale(
     * "This is where it was located according to a newspaper article from the Brownsville Daily Herald"
     * );
     * Collection<Person> victims = new ArrayList<>();
     * Person p1 = new Person();
     * p1.addIdentity(new Race("White"));
     * Person p2 = new Person();
     * p2.addIdentity(new Race("Black"));
     * p2.addIdentity(new Age(14));
     * victims.add(p1);
     * victims.add(p2);
     * poi.setVictims(victims);
     * Collection<Person> aggressors = new ArrayList<>();
     * Person p3 = new Person();
     * p3.addIdentity(new Race("Black"));
     * p3.addIdentity(new Age(54));
     * Person p4 = new Person();
     * p4.addIdentity(new Race("White"));
     * aggressors.add(p3);
     * aggressors.add(p4);
     * poi.setAggressors(aggressors);
     * Collection<StringSimpleFormField> tags = new ArrayList<>();
     * tags.add(new StringSimpleFormField("Tag", "Murder"));
     * tags.add(new StringSimpleFormField("Tag", "Texas Rangers"));
     * tags.add(new StringSimpleFormField("Tag", "Congressional Investigation"
     * ));
     * poi.setTags(tags);
     * Collection<StringSimpleFormField> primarySources = new ArrayList<>();
     * primarySources.add(new StringSimpleFormField("Primary Source",
     * "Primary Source 1"));
     * poi.setPrimarySources(primarySources);
     * Collection<StringSimpleFormField> secondarySources = new ArrayList<>();
     * secondarySources.add(new StringSimpleFormField("Secondary Source",
     * "Secondary Source 1"));
     * secondarySources.add(new StringSimpleFormField("Secondary Source",
     * "Secondary Source 1"));
     * poi.setSecondarySources(secondarySources);
     * poi.setResearchNotes("My notes will go here.");
     * 
     * User user = (User) req.getSession(false).getAttribute("currentUser");
     * if (user == null) {
     * Servlets.sendError(Error.INTERNAL_SERVER_ERROR, req, resp);
     * return;
     * }
     * POIWikiPage poiW = new POIWikiPage(user);
     * try {
     * Thread.sleep(1000);
     * } catch (InterruptedException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * }
     * poiW.addVersion(user, poi);
     * 
     * Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
     * String id = (String) ds.save(poiW).getId();
     * Servlets.sendSuccess(id, HttpServletResponse.SC_OK, req, resp,
     * "text/json");
     */

  }

  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");
    if (id == null) {
      Servlets.sendError(Error.ID_MISSING, req, resp);
      return;
    }

    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");

    POIWikiPage poiWikiPage = ds.get(POIWikiPage.class, id);

    if (poiWikiPage == null) {
      Servlets.sendError(Error.ID_NOT_FOUND, req, resp);
      return;
    }

    POI poi = Servlets.parseData(req, POI.class);
    if (poi == null) {
      Servlets.sendError(
          new Error("Error deserializing json", HttpServletResponse.SC_BAD_REQUEST),
          req,
          resp);
      return;
    }

    User currentUser = (User) req.getSession(false).getAttribute("currentUser");
    if (currentUser == null) {
      // TODO: Update error message
      Servlets.sendError(Error.ID_NOT_FOUND, req, resp);
    }

    poiWikiPage.addVersion(currentUser, poi);
    String poiWikiPageId = (String) ds.save(poiWikiPage).getId();
    Servlets.sendSuccess(poiWikiPageId, HttpServletResponse.SC_OK, req, resp, "text/json");

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

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    Map<String, String> map = Servlets.parseData(req);
    String id = map.get("id");
    Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
    POIWikiPage poiW = ds.get(POIWikiPage.class, id);
    ds.delete(poiW.getCurrentVersion());
    Collection<Version<POI>> previous = poiW.getPreviousVersions();
    Collection<String> previousIds = new ArrayList<>();
    for (Version<POI> poiV : previous) {
      previousIds.add(poiV.getId());
    }
    ds.delete(POIVersion.class, previousIds);
    ds.delete(poiW);
    Servlets.sendSuccess("success", 200, req, resp, "text/json");
    return;
  }
}
