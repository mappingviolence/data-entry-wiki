package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.user.User;
import org.mappingviolence.user.User.Role;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("serial")
public class UpdateUserPermissionsServlet extends HttpServlet {

  // Load the user-settings.jsp page
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      List<User> users = DatabaseConnection
          .getDatabase("data-entry-wiki")
          .find(User.class)
          .asList();
      req.setAttribute("users", users);
      HttpSession session = req.getSession(false);
      User currentUser = (User) session.getAttribute("currentUser");
      if (currentUser == null) {
        throw new IllegalArgumentException("Please login to access this page");
      }
      req.setAttribute("currentUser", currentUser);
      req.getRequestDispatcher("/WEB-INF/webapp/user-settings.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      try {
        e.printStackTrace(resp.getWriter());
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      e.printStackTrace();
    } catch (NullPointerException e) {
      try {
        resp.getWriter().println("fuck the null");
        e.printStackTrace(resp.getWriter());
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
    return;
  }

  // Create new user
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    User u = new User("hansen.cole.e@gmail.com", Role.ADMIN);
    DatabaseConnection.getDatabase("data-entry-wiki").save(u);
    User u1 = new User("edward_jiao@brown.edu", Role.ADMIN);
    DatabaseConnection.getDatabase("data-entry-wiki").save(u1);
  }

  // Update user role
  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Map<String, String> dataMap = Servlets.parseData(req);
    String id = dataMap.get("id");
    String newRoleStr = dataMap.get("role");
    if (id == null || id.equals("")) {
      Servlets.sendError(Servlets.Error.ID_MISSING, req, resp);
      return;
    } else if (newRoleStr == null || newRoleStr.equals("")) {
      Servlets.sendError(
          new Servlets.Error(
              "A role was not specified in the request",
              HttpServletResponse.SC_BAD_REQUEST),
          req,
          resp);
    } else {
      User user = User.getUser(id);
      // TODO: Check for invalid role
      Role newRole = User.Role.valueOf(newRoleStr);
      if (user == null) {
        Servlets.sendError(Servlets.Error.ID_NOT_FOUND, req, resp);
        return;
      } else {
        user.setRole(newRole);

        Datastore ds = DatabaseConnection.getDatabase("data-entry-wiki");
        ds.save(user);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(
            "{ \"success\" : { \"msg\" : \"" + user.getEmail()
                + "'s permissions were succesfully changed.\" } }");

      }
    }
  }

  // Delete users
  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Map<String, String> dataMap = Servlets.parseData(req);
    String id = dataMap.get("id");

    if (id != null && !id.equals("")) {
      User user = User.getUser(id);
      if (user == null) {
        Servlets.sendError(Servlets.Error.ID_NOT_FOUND, req, resp);
        return;
      } else {
        if (user.delete()) {
          resp.setStatus(HttpServletResponse.SC_OK);
          resp.getWriter().print(
              "{ \"success\" : { \"msg\" : \"" + user.getEmail()
                  + " was successfully deleted.\" } }");
        } else {
          Servlets.sendError(
              new Servlets.Error(
                  "This delete did not succeed. Either an error occured mid-delete, "
                      + "or this resource has already been deleted.",
                  HttpServletResponse.SC_NOT_FOUND),
              req,
              resp);
        }
      }

    } else {
      Servlets.sendError(Servlets.Error.ID_MISSING, req, resp);
      return;
    }
  }
}
