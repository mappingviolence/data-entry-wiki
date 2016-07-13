package org.mappingviolence.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mappingviolence.google_api.GoogleHelperObjects;
import org.mappingviolence.user.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

public class SecurityFilter implements Filter {

  @Override
  public void destroy() {
    return;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    if (req.getRequestURI().startsWith("/mapviz/signin.html")) {
      chain.doFilter(req, resp);
      return;
    }

    if (req.getRequestURI().startsWith("/eddie")) {
      chain.doFilter(req, resp);
      return;
    }

    HttpSession session = req.getSession(false);
    if (session == null) {
      Cookie[] cookies = req.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("id_token".equals(cookie.getName())) {

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                GoogleHelperObjects.getHttpTransport(),
                GoogleHelperObjects.getJsonFactory())
                    .setAudience(
                        Arrays.asList(
                            "1031670491058-iveiphljv5dr3fgnr3apb4supm2h3rs2.apps.googleusercontent.com"))
                    .setIssuer("accounts.google.com")
                    .build();

            GoogleIdToken idToken;
            try {
              idToken = verifier.verify(cookie.getValue());
            } catch (GeneralSecurityException e) {
              e.printStackTrace();
              resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
              resp.setContentType("application/xml");
              resp.getOutputStream().println(
                  "<errors><error>Oops! We dropped the ball, please try again.</error></errors>");
              return;
            }
            if (idToken != null) {
              Payload payload = idToken.getPayload();
              if (User.isAuthorizedUser(payload)) {
                HttpSession newSession = req.getSession();
                newSession.setAttribute("userAccessToken", payload.getAccessTokenHash());
                User curentUser = User.getUserByEmail(payload.getEmail());
                newSession.setAttribute("currentUser", curentUser);
                Cookie respCookie = new Cookie(cookie.getName(), "");
                respCookie.setMaxAge(0);
                respCookie.setPath("/");
                respCookie.setDomain("localhost");
                resp.addCookie(respCookie);
                resp.setContentType("text/html");
                chain.doFilter(req, resp);
                return;
              } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.setContentType("application/xml");
                resp.getOutputStream().println(
                    "<errors><error>" + payload.getEmail() + "NOT IN DATABASE</error></errors>");
                return;
              }
            } else {
              resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              resp.setContentType("application/xml");
              resp.getOutputStream().println("<errors><error>ID TOKEN WAS FORGED</error></errors>");
              return;
            }
          }
        }
      }
      resp.sendRedirect("/mapviz/signin.html");
      return;
    } else {
      if (session.getAttribute("userAccessToken") == null) {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/xml");
        resp.getOutputStream().println("<errors><error>SESSION FORGED</error></errors>");
        return;
      } else {
        chain.doFilter(req, resp);
        return;
      }
    }
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    return;
  }

}
