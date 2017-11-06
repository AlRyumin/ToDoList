/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.db.model.User;
import main.db.service.UserServiceImpl;
import main.utils.Utils;

import static main.helper.FieldValidation.*;
import static main.utils.Constants.*;
import main.helper.HashPassword;

/**
 *
 * @author root
 */
@WebServlet(name = "LoginServlet", urlPatterns = {URL_LOGIN})
public class LoginServlet extends HttpServlet {

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/login.jsp");
    dispatcher.forward(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Connection connection = Utils.getConnection(request);
    String errorLogin = "";
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    if (isFieldEmpty(email) || isFieldEmpty(password)) {
      errorLogin += "Please, fill all required fields.<br>";
      request.setAttribute("errorLogin", errorLogin);

      proceedWithError(request, response);
    }

    try {
      String passwordHashed = HashPassword.generateHash(password);
      UserServiceImpl userService = new UserServiceImpl(connection);
      User user = userService.getUser(email, passwordHashed);

      if (user == null) {
        errorLogin += "Email or password not valid.";
        request.setAttribute("errorLogin", errorLogin);

        proceedWithError(request, response);
      }
      HttpSession session = request.getSession();
      Utils.setUserSession(session, user);

      if (request.getParameter("remember") != null)
        Utils.setUserCookie(response, user);
      else
        Utils.deleteUserCookie(response);

      response.sendRedirect(request.getContextPath() + URL_HOME);
    } catch (SQLException e) {
      errorLogin += e.getMessage();
      request.setAttribute("errorLogin", errorLogin);

      proceedWithError(request, response);
    } catch (Exception e) {
      errorLogin += e.getMessage();
      request.setAttribute("errorLogin", errorLogin);

      proceedWithError(request, response);
    }

  }

  protected void proceedWithError(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    //            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/login.jsp");
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/login.jsp");

    dispatcher.forward(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }

}
