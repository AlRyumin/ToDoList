/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.utils;

import main.db.model.User;
import java.sql.Connection;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author root
 */
public class Utils {

  public static final String DB_CONNECTION = "DB_CONNECTION";
  public static final String USER_INFO = "USER_INFO";

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
}
