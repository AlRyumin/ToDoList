/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.servlet;

//import com.mysql.jdbc.Util;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import main.db.service.CategoryService;
import main.db.service.CategoryServiceImpl;
import main.utils.Utils;

import static main.helper.FieldValidation.*;
import static main.utils.Constants.*;

/**
 *
 * @author root
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {URL_CATEGORY})
public class CategoryServlet extends HttpServlet {

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
    int id = Integer.parseInt(request.getParameter("id"));

    if (id == 0) {
      continueWithError(request, response, "Id is 0");
    }

    HttpSession session = request.getSession();

    try {
      Connection connection = Utils.getConnection(request);
      User user = Utils.getUserSession(session);
      CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
      Category category = categoryService.getCategory(id);
      ArrayList<Category> categories = categoryService.getCategories(user.getId(), category.getId());

      request.setAttribute("categories", categories);
      request.setAttribute("category", category);

      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/category.jsp");
      dispatcher.forward(request, response);

    } catch (SQLException e) {
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
    String action = request.getParameter("action");
    HttpSession session = request.getSession();

    if (isFieldEmpty(action)) {
      doGet(request, response);
    } else if (action.equals("update")) {
      try {
        int id = Integer.parseInt(request.getParameter("id"));
        int parentId = Integer.parseInt(request.getParameter("parent"));
        String name = request.getParameter("name");
        Connection connection = Utils.getConnection(request);
        User user = Utils.getUserSession(session);
        CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
        categoryService.update(id, user.getId(), parentId, name);

        response.sendRedirect(request.getContextPath() + URL_CATEGORIES);
      } catch (Exception e) {
        e.printStackTrace();
        continueWithError(request, response, e.getMessage());
      }
    } else if (action.equals("delete")) {
      try {
        int id = Integer.parseInt(request.getParameter("id"));
        Connection connection = Utils.getConnection(request);
        CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
        categoryService.delete(id);

        response.sendRedirect(request.getContextPath() + URL_CATEGORIES);
      } catch (Exception e) {
        e.printStackTrace();
        continueWithError(request, response, e.getMessage());
      }
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

  protected void continueWithError(HttpServletRequest request, HttpServletResponse response, String error) throws ServletException, IOException {
    request.setAttribute("error", error);
    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/category.jsp");
    dispatcher.forward(request, response);
  }
}
