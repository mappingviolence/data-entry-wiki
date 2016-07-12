package org.mappingviolence.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.database.User;
import org.mappingviolence.database.User.Role;
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
        throw new IllegalArgumentException("whoa! asdf 1234 as");
      }
      req.setAttribute("currentUser", currentUser);
      req.getRequestDispatcher("/WEB-INF/webapp/admin/user-settings.jsp").forward(req, resp);
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
    User u1 = DatabaseConnection.getDatabase("data-entry-wiki").get(u);
    resp.getWriter().println(u1);
  }

  // Update user role
  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Map<String, String> dataMap = ServletUtils.parseData(req);
    String id = dataMap.get("id");
    String newRoleStr = dataMap.get("role");
    if (id == null || id.equals("")) {
      sendError(req, resp, Error.ID_MISSING);
      return;
    } else if (newRoleStr == null || newRoleStr.equals("")) {
      sendError(req, resp, Error.ROLE_MISSING);
    } else {
      User user = User.getUser(id);
      // TODO: Check for invalid role
      Role newRole = User.Role.valueOf(newRoleStr);
      if (user == null) {
        sendError(req, resp, Error.ID_NOT_FOUND);
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
    Map<String, String> dataMap = ServletUtils.parseData(req);
    String id = dataMap.get("id");

    if (id != null && !id.equals("")) {
      User user = User.getUser(id);
      if (user == null) {
        sendError(req, resp, Error.ID_NOT_FOUND);
        return;
      } else {
        if (user.delete()) {
          resp.setStatus(HttpServletResponse.SC_OK);
          resp.getWriter().print(
              "{ \"success\" : { \"msg\" : \"" + user.getEmail()
                  + " was successfully deleted.\" } }");
        } else {
          sendError(req, resp, Error.DELETE_NOT_SUCCESSFUL);
        }
      }

    } else {
      sendError(req, resp, Error.ID_MISSING);
      return;
    }
  }

  private void sendError(HttpServletRequest req, HttpServletResponse resp, Error err)
      throws IOException, ServletException {
    resp.setStatus(err.getStatus());
    PrintWriter pw = resp.getWriter();
    pw.print("{ \"error\" : { \"msg\" : \"" + err.getMessage() + "\" } }");
    pw.close();
    return;
  }

  private enum Error {
    ID_MISSING("The request did not contain an id parameter.", HttpServletResponse.SC_BAD_REQUEST),
    ID_NOT_FOUND(
        "The provided id was not found in the database.",
        HttpServletResponse.SC_BAD_REQUEST),
    EMAIL_MISSING(
        "The request did not contain an email parameter.",
        HttpServletResponse.SC_BAD_REQUEST),
    EMAIL_NOT_FOUND(
        "The provided email was not found in the database.",
        HttpServletResponse.SC_BAD_REQUEST),
    DELETE_NOT_SUCCESSFUL(
        "The delete was not successful. Please try again.",
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    ROLE_MISSING(
        "The request did not contain a role paramater.",
        HttpServletResponse.SC_BAD_REQUEST);

    private String msg;
    private int status;

    private Error(String msg, int status) {
      this.msg = msg;
      this.status = status;
    }

    public String getMessage() {
      return msg;
    }

    public int getStatus() {
      return status;
    }

    @Override
    public String toString() {
      return getMessage();
    }
  }
}
