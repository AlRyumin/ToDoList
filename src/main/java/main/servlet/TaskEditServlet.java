/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.db.model.Category;
import main.db.model.Task;
import main.db.model.TaskPriority;
import main.db.model.TaskStatus;
import main.db.model.TaskType;
import main.db.model.User;
import main.db.service.CategoryServiceImpl;
import main.db.service.TaskServiceImpl;
import main.helper.RedirectHelper;

import static main.utils.Constants.*;
import main.utils.Utils;

/**
 *
 * @author root
 */
@WebServlet(name = "TaskEditServlet", urlPatterns = {URL_TASK_EDIT})
public class TaskEditServlet extends HttpServlet {

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
    try {
      Connection connection = Utils.getConnection(request);
      TaskServiceImpl taskService = new TaskServiceImpl(connection);
      HttpSession session = request.getSession();
      User user = Utils.getUserSession(session);
      CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
      ArrayList<Category> categories = categoryService.getCategories(user.getId());
      TaskPriority[] priorities = TaskPriority.values();
      TaskStatus[] statuses = TaskStatus.values();
      TaskType[] types = TaskType.values();
      Task task = taskService.get(id);

      request.setAttribute("categories", categories);
      request.setAttribute("priorities", priorities);
      request.setAttribute("types", types);
      request.setAttribute("statuses", statuses);
      request.setAttribute("task", task);

      request.getRequestDispatcher("/WEB-INF/views/pages/taskEdit.jsp").forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
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
    try {
      HttpSession session = request.getSession();
      User user = Utils.getUserSession(session);
      Connection connection = Utils.getConnection(request);
      TaskServiceImpl taskService = new TaskServiceImpl(connection);

      int id = Integer.parseInt(request.getParameter("id"));
      String name = request.getParameter("name");
      String description = request.getParameter("description");
      String category = request.getParameter("category");
      String priority = request.getParameter("priority");
      String type = request.getParameter("type");
      String status = request.getParameter("status");
      String dueDate = request.getParameter("due_date");
      String previous_url = request.getParameter("previous_url");

      int categoryId = Integer.parseInt(category);
      TaskPriority taskPriority = TaskPriority.valueOf(priority);
      TaskType taskType = TaskType.valueOf(type);
      TaskStatus taskStatus = TaskStatus.valueOf(status);

      Task task = new Task(id, name, description, user.getId(), categoryId, taskPriority, taskType, taskStatus, dueDate);

      taskService.update(task);

      response.sendRedirect(request.getContextPath() + previous_url);
    } catch (Exception e) {
      e.printStackTrace();
      RedirectHelper.continueWithError(request, response, e.getMessage(), "/WEB-INF/views/pages/taskAdd.jsp");
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
}
