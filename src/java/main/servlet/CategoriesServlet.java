/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.db.model.Category;
import main.db.model.User;
import main.db.service.CategoryServiceImpl;
import main.utils.Utils;

import static main.utils.Constants.*;
import static main.helper.FieldValidation.*;

/**
 *
 * @author root
 */
@WebServlet(name = "CategoriesServlet", urlPatterns = {URL_CATEGORIES})
public class CategoriesServlet extends HttpServlet {

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
    HttpSession session = request.getSession();

    checkUserLoggedIn(session, response);

    try {
      User user = Utils.getUserSession(session);
      Connection connection = Utils.getConnection(request);
      CategoryServiceImpl category = new CategoryServiceImpl(connection);
      ArrayList<Category> categories = category.getCategories(user.getId());
      request.setAttribute("categories", categories);
      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/categories.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
      continueWithError(request, response, e.getMessage());
    }
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
    HttpSession session = request.getSession();
    String error = "";

    checkUserLoggedIn(session, response);

    String name = request.getParameter("name");
    if(isFieldEmpty(name))
      continueWithError(request, response, "Name field is required");
    try {
      Connection connection = Utils.getConnection(request);
      User user = Utils.getUserSession(session);
      CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
      Category category = new Category(user.getId(), 0, name);
      categoryService.createCategory(category);
    } catch (Exception e) {
      e.printStackTrace();
      continueWithError(request, response, e.getMessage());
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

  protected void checkUserLoggedIn(HttpSession session, HttpServletResponse response) throws IOException {
    if (Utils.getUserSession(session) == null) {
      session.setAttribute("error", "Login required.");
      response.sendRedirect("/");
    }
  }

  protected void continueWithError(HttpServletRequest request, HttpServletResponse response, String error) throws ServletException, IOException{
    request.setAttribute("error", error);
    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/categories.jsp");
    dispatcher.forward(request, response);
  }

}
