package org.mappingviolence.servlet.ajax;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.poi.date.Date;
import org.mappingviolence.servlet.Servlets;

@SuppressWarnings("serial")
public class ValidDate extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String dateStr = req.getParameter("dateStr");
    Date date;
    try {
      date = Date.buildDate(dateStr);
    } catch (IllegalArgumentException e) {
      date = null;
    }
    if (date == null) {
      Servlets.sendError(
          new Servlets.Error("Invalid date string", HttpServletResponse.SC_BAD_REQUEST),
          req,
          resp);
    } else {
      Servlets.sendSuccess(date, HttpServletResponse.SC_OK, req, resp, "text/json");
    }
  }
}
