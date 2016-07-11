package org.mappingviolence.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PoolServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    List<Map<String, String>> pois = new ArrayList<>();
    Map<String, String> poi1 = new HashMap<>();
    poi1.put("title", "POI 1");
    poi1.put("creator", "edward_jiao@brown.edu");
    Map<String, String> poi2 = new HashMap<>();
    poi2.put("title", "POI 2");
    poi2.put("creator", "cole_hansen@brown.edu");
    pois.add(poi1);
    pois.add(poi2);
    req.setAttribute("allPOIs", pois);
    try {
      req.getRequestDispatcher("/WEB-INF/webapp/pool.jsp").forward(req, resp);
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
