package org.mappingviolence.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Servlets {
  public static final Gson GSON = new Gson();

  public static void sendError(Error err, HttpServletRequest req, HttpServletResponse resp) {
    resp.setStatus(err.getHttpStatusCode());
    PrintWriter pw = getWriter(resp);
    pw.print("{\"error\" : 1, \"data\" : " + err.getErrorMessage() + " }");
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
    public static final Error INTERNAL_SERVER_ERROR = new Error(
        "There was an error. We have been notified and are working on fixing it. Please try your request again.",
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

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
    pw.print("{\"success\" : 1, \"data\" : " + dataJson + " }");
    pw.close();
    return;
  }

  public static <T> void sendSuccess(T data, int httpStatusCode, HttpServletRequest req,
      HttpServletResponse resp, String type) {
    resp.setContentType(type);
    sendSuccess(data, httpStatusCode, req, resp);
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

  public static void forward(String path, HttpServletRequest req, HttpServletResponse resp) {
    try {
      req.getRequestDispatcher(path).forward(req, resp);
      return;
    } catch (ServletException | IOException e) {
      e.printStackTrace();
      sendError(Error.INTERNAL_SERVER_ERROR, req, resp);
    }
  }

  public static Map<String, String> parseData(HttpServletRequest req) {
    InputStream is;
    try {
      is = req.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
    char[] charBuffer = new char[128];
    int bytesRead = -1;
    StringBuilder sb = new StringBuilder();
    try {
      while ((bytesRead = br.read(charBuffer)) > 0) {
        sb.append(charBuffer, 0, bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    String data = sb.toString();
    try {
      data = URLDecoder.decode(data, "UTF-8");
      System.err.println(data);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
    if (!data.contains("=")) {
      return new HashMap<>();
    }
    return Splitter.on("&").withKeyValueSeparator("=").split(data);
  }

  public static <T> T parseData(HttpServletRequest req, Class<T> clazz) {
    InputStream is;
    try {
      is = req.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
    char[] charBuffer = new char[128];
    int bytesRead = -1;
    StringBuilder sb = new StringBuilder();
    try {
      while ((bytesRead = br.read(charBuffer)) > 0) {
        sb.append(charBuffer, 0, bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    String jsonData = sb.toString();
    try {
      return GSON.fromJson(jsonData, clazz);
    } catch (JsonSyntaxException e) {
      return null;
    }
  }
}
