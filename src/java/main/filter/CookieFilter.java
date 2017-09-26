/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.filter;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import main.db.model.User;
import main.db.service.UserService;
import main.db.service.UserServiceImpl;
import main.utils.Utils;

/**
 *
 * @author root
 */
public class CookieFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpSession session = httpRequest.getSession();

    User user = Utils.getUserSession(session);

    if (user != null) {
      session.setAttribute("COOKIE_CHECKED", "CHECKED");
      chain.doFilter(request, response);
      return;
    }

    Connection connection = Utils.getConnection(request);

    String checked = (String) session.getAttribute("COOKIE_CHECKED");

    if (checked == null && connection != null) {
      int userId = Utils.getUserCookie(httpRequest);
      try {
        UserService userService = new UserServiceImpl(connection);
        User userObj = userService.getUser(userId);
        Utils.setUserSession(session, userObj);
      } catch (Exception e) {
        e.printStackTrace();
      }

      session.setAttribute("COOKIE_CHECKED", "CHECKED");
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }

}
