/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.utils;

import main.db.model.User;
import java.sql.Connection;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author root
 */
public class Utils {

  public static final String DB_CONNECTION = "DB_CONNECTION";
  public static final String USER_INFO = "USER_INFO";
  public static final String USER_COOKIE = "USER_COOKIE";

  public static void setConnection(ServletRequest request, Connection connection) {
    request.setAttribute(DB_CONNECTION, connection);
  }

  public static Connection getConnection(ServletRequest request) {
    return (Connection) request.getAttribute(DB_CONNECTION);
  }

  public static void setUserSession(HttpSession session, User user) {
    session.setAttribute(USER_INFO, user);
  }

  public static User getUserSession(HttpSession session) {
    return (User) session.getAttribute(USER_INFO);
  }

  public static void setUserCookie(HttpServletResponse response, User user) {
    Cookie cookie = new Cookie(USER_COOKIE, String.valueOf(user.getId()));
    cookie.setMaxAge(24 * 60 * 60);
    response.addCookie(cookie);
  }

  public static int getUserCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (USER_COOKIE.equals(cookie.getName())) {
          return Integer.parseInt(cookie.getValue());
        }
      }
    }
    return 0;
  }

  public static void deleteUserCookie(HttpServletResponse response){
    Cookie cookie = new Cookie(USER_COOKIE, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
