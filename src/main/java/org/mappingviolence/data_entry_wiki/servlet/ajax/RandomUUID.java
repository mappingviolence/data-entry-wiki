package org.mappingviolence.data_entry_wiki.servlet.ajax;

import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mappingviolence.data_entry_wiki.servlet.Servlets;

@SuppressWarnings("serial")
public class RandomUUID extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Servlets.sendSuccess(UUID.randomUUID().toString(), 200, req, resp, "text/json");
    return;
  }
}
