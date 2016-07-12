package org.mappingviolence.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;

public class ServletUtils {
  public static Map<String, String> parseData(HttpServletRequest req) throws IOException {
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
    return Splitter.on("&").withKeyValueSeparator("=").split(data);
  }
}
