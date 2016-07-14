package org.mappingviolence.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class Servlets {
  public static final Gson GSON = new Gson();

  public static void sendError(Error err, HttpServletRequest req, HttpServletResponse resp) {
    resp.setStatus(err.getHttpStatusCode());
    PrintWriter pw = getWriter(resp);
    pw.print("{ \"error\" : 1, \"data\" : \"" + err.getErrorMessage() + "\" }");
    pw.close();
    return;
  }

  public static class Error {
    public static final Error ID_MISSING = new Error(
        "The request did not contain an id parameter.",
        HttpServletResponse.SC_BAD_REQUEST);
    public static final Error ID_NOT_FOUND = new Error(
        "The provided id was not found in the database.",
        HttpServletResponse.SC_NOT_FOUND);

    private final String errorMessage;

    private final int httpStatusCode;

    public Error(String errorMessage, int httpStatusCode) {
      this.errorMessage = errorMessage;
      this.httpStatusCode = httpStatusCode;
    }

    public String getErrorMessage() {
      return errorMessage;
    }

    public int getHttpStatusCode() {
      return httpStatusCode;
    }

    @Override
    public String toString() {
      return getErrorMessage();
    }
  }

  public static <T> void sendSuccess(T data, int httpStatusCode, HttpServletRequest req,
      HttpServletResponse resp) {
    resp.setStatus(httpStatusCode);
    PrintWriter pw = getWriter(resp);
    String dataJson = GSON.toJson(data);
    pw.print("{ \"success\" : 1, \"data\" : " + dataJson + " }");
    pw.close();
    return;
  }

  public static PrintWriter getWriter(HttpServletResponse resp) {
    try {
      return resp.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return null;
    }
  }
}