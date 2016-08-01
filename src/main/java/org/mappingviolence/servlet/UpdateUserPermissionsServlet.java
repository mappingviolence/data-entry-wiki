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
    Map<String, String> data = Servlets.parseData(req);
    String email = data.get("email");
    if (email == null || "".equals(email) || !email.contains("@")) {
      Servlets.sendError(
          new Servlets.Error("email not valid", HttpServletResponse.SC_BAD_REQUEST),
          req,
          resp);
      return;
    }
    String roleStr = data.get("role");
    if (roleStr == null || "".equals(roleStr)) {
      Servlets.sendError(
          new Servlets.Error("role not valid", HttpServletResponse.SC_BAD_REQUEST),
          req,
          resp);
      return;
    }
    Role role;
    try {
      role = Enum.valueOf(User.Role.class, roleStr);
    } catch (IllegalArgumentException | NullPointerException e) {
      Servlets.sendError(
          new Servlets.Error("role not valid", HttpServletResponse.SC_BAD_REQUEST),
          req,
          resp);
      return;
    }
    new User(email, role);
    Servlets.sendSuccess("User successfully added", 200, req, resp, "text/json");
    return;
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
      return;
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

        // req.getSession(false).setAttribute("currentUser", user);

        Servlets.sendSuccess(
            "User permissions successfully updated",
            HttpServletResponse.SC_OK,
            req,
            resp,
            "text/json");
        return;

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
          Servlets.sendSuccess(
              "User succesfully deleted",
              HttpServletResponse.SC_OK,
              req,
              resp,
              "text/json");
          return;
        } else {
          Servlets.sendError(
              new Servlets.Error(
                  "This delete did not succeed. Either an error occured mid-delete, "
                      + "or this resource has already been deleted.",
                  HttpServletResponse.SC_NOT_FOUND),
              req,
              resp);
          return;
        }
      }
    } else {
      Servlets.sendError(Servlets.Error.ID_MISSING, req, resp);
      return;
    }
  }
}
