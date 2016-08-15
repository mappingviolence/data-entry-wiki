package org.mappingviolence.data_entry_wiki.servlet.ajax;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.data_entry_wiki.servlet.Servlets;
import org.mappingviolence.poi.date.Date;

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
