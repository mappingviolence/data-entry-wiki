package org.mappingviolence.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.database.DatabaseConnection;
import org.mappingviolence.database.User;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;

@SuppressWarnings("serial")
public class UpdateUserPermissionsServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      List<User> users = DatabaseConnection.getDatabase("users").find(User.class).asList();
      req.setAttribute("users", users);
      req.getRequestDispatcher("/WEB-INF/webapp/admin/user-settings.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return;
  }

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    InputStream is = req.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
    char[] charBuffer = new char[128];
    int bytesRead = -1;
    StringBuilder sb = new StringBuilder();
    while ((bytesRead = br.read(charBuffer)) > 0) {
      sb.append(charBuffer, 0, bytesRead);
    }
    String data = sb.toString();

    data = URLDecoder.decode(data, "UTF-8");
    Map<String, String> dataMap = Splitter.on("&").withKeyValueSeparator("=").split(data);
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
              "{ \"success\" : { \"msg\" : \"" + user.getEmail() + " was successfully deleted.\" } }");
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
    ID_NOT_FOUND("The provided id was not found in the database.", HttpServletResponse.SC_BAD_REQUEST),
    EMAIL_MISSING("The request did not contain an email parameter.", HttpServletResponse.SC_BAD_REQUEST),
    EMAIL_NOT_FOUND("The provided email was not found in the database.", HttpServletResponse.SC_BAD_REQUEST),
    DELETE_NOT_SUCCESSFUL(
        "The delete was not successful. Please try again.",
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

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
