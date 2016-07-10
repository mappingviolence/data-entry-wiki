package org.mappingviolence.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.User;

@SuppressWarnings("serial")
public class UpdateUserPermissionsServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      req.getRequestDispatcher("/WEB-INF/webapp/admin/user-settings.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return;
  }

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String email = req.getParameter("email");
    if (email != null && !email.equals("")) {
      User user = User.getUserByEmail(email);
      if (user == null) {
        sendError(req, resp, Error.EMAIL_NOT_FOUND);
        return;
      } else {
        if (user.delete()) {
          resp.getWriter().print("{ success : { msg : " + email + " was successfully deleted. } }");
        } else {
          sendError(req, resp, Error.DELETE_NOT_SUCCESSFUL);
        }
      }
    } else {
      sendError(req, resp, Error.EMAIL_MISSING);
      return;
    }
  }

  private void sendError(HttpServletRequest req, HttpServletResponse resp, Error err)
      throws IOException, ServletException {
    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    ServletOutputStream o = resp.getOutputStream();
    o.print("{ error : { msg : " + err.getMessage() + " } }");
    return;
  }

  private enum Error {
    EMAIL_MISSING("The request did not contain an email parameter."),
    EMAIL_NOT_FOUND("The provided email was not found in the database."),
    DELETE_NOT_SUCCESSFUL("The delete was not successful. Please try again.");

    private String msg;

    private Error(String msg) {
      this.msg = msg;
    }

    public String getMessage() {
      return msg;
    }

    @Override
    public String toString() {
      return getMessage();
    }
  }
}
