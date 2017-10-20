/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.TreeMap;
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
import main.helper.category.CategoryNode;
import main.helper.category.CategoryTree;

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

      CategoryTree categoriesTree = new CategoryTree(categories);
      TreeMap<Integer, CategoryNode> nodes = categoriesTree.getNodes();
      ArrayList<CategoryNode> listNodes = categoriesTree.getListNodes();


      request.setAttribute("nodes", nodes);
      request.setAttribute("listNodes", listNodes);
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
    String parent = request.getParameter("parent");
    int parentId = Integer.parseInt(parent);

    if(isFieldEmpty(name))
      continueWithError(request, response, "Name field is required");
    try {
      Connection connection = Utils.getConnection(request);
      User user = Utils.getUserSession(session);
      CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
      Category category = new Category(user.getId(), parentId, name);
      categoryService.createCategory(category);
      response.sendRedirect(URL_CATEGORIES);
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
