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
import main.db.model.User;
import main.db.service.UserServiceImpl;
import main.helper.HashPassword;
import main.utils.Utils;

import static main.helper.FieldValidation.*;
import static main.utils.Constants.*;

/**
 *
 * @author root
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {URL_REGISTER})
public class RegisterServlet extends HttpServlet {

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

    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/register.jsp");
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
    String errorRegister = null;

    User user = checkFields(request, response);

    try {
      UserServiceImpl userService = new UserServiceImpl(connection);
      userService.createUser(user);
    } catch (SQLException e) {
      errorRegister = e.getMessage();
    } catch (Exception e) {
      e.printStackTrace();
      errorRegister = e.getMessage();
    }

    request.setAttribute("errorRegister", errorRegister);
    request.setAttribute("user", user);

    if (errorRegister != null) {
      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/register.jsp");
      dispatcher.forward(request, response);
    } else {
//      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/home.jsp");
//      dispatcher.forward(request, response);
      response.sendRedirect(request.getContextPath() + "/");
    }
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

  protected User checkFields(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String errorRegister = null;
    User user = null;
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String passwordRepeat = request.getParameter("password_repeat");

    if (isFieldEmpty(name) || isFieldEmpty(email) || isFieldEmpty(password) || isFieldEmpty(passwordRepeat)) {
      errorRegister += "Please fill all required fields!<br>";
    }
    if (password.length() < 3) {
      errorRegister += "Minimum password length is 3!<br>";
    } else if (password.equals(passwordRepeat) != true) {
      errorRegister += "Passwords don't match!<br>";
    }

    if (errorRegister == null) {
      String hashedPasword = HashPassword.generateHash(password);
      user = new User(name, email, hashedPasword);
    } else {
      request.setAttribute("errorRegister", errorRegister);
      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/register.jsp");
      dispatcher.forward(request, response);
    }

    return user;
  }

}
