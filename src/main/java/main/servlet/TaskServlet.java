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
import javax.servlet.ServletOutputStream;
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
import main.helper.DateHelper;
import main.helper.RedirectHelper;
import main.utils.Utils;
import static main.helper.FieldValidation.isFieldEmpty;
import static main.utils.Constants.*;

/**
 *
 * @author root
 */
@WebServlet(name = "TaskServlet", urlPatterns = {URL_TASK})
public class TaskServlet extends HttpServlet {

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

    String add = request.getParameter("add");
    if (add != null)
      addTaskGet(request, response);
    else
      showTasks(request, response);
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
    String add = request.getParameter("add");

    if (add != null)
      addTaskPost(request, response);
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

  public void showTasks(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      User user = Utils.getUserSession(session);
      Connection connection = Utils.getConnection(request);
      TaskServiceImpl taskService = new TaskServiceImpl(connection);
      CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
      ArrayList<Category> categories = categoryService.getCategories(user.getId());
      TaskPriority[] priorities = TaskPriority.values();
      TaskType[] types = TaskType.values();
      TaskStatus[] statuses = TaskStatus.values();

      TreeMap<String, String> params = new TreeMap<>();

      String userId = Integer.toString(user.getId());

      String categoryId = request.getParameter("category");
      String priority = request.getParameter("priority");
      String type = request.getParameter("type");
      String status = request.getParameter("status");
      String dueDate = request.getParameter("due_date");
      if (dueDate == null)
        dueDate = DateHelper.getCurrentDateString();

      params.put("userId", userId);
      params.put("categoryId", categoryId);
      params.put("priority", priority);
      params.put("type", type);
      params.put("status", status);
      params.put("dueDate", dueDate);

      ArrayList<Task> tasks = taskService.get(params);

      request.setAttribute("currentCategory", isFieldEmpty(categoryId) ? "" : categoryId);
      request.setAttribute("currentPriority", isFieldEmpty(priority) ? "" : priority);
      request.setAttribute("currentType", isFieldEmpty(type) ? "" : type);
      request.setAttribute("currentStatus", isFieldEmpty(status) ? "" : status);

      request.setAttribute("tasks", tasks);
      request.setAttribute("categories", categories);
      request.setAttribute("priorities", priorities);
      request.setAttribute("types", types);
      request.setAttribute("statuses", statuses);
      request.setAttribute("due_date", dueDate);

      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/tasks.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {

    }
  }

  public void addTaskGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      User user = Utils.getUserSession(session);
      Connection connection = Utils.getConnection(request);
      CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
      ArrayList<Category> categories = categoryService.getCategories(user.getId());
      TaskPriority[] priorities = TaskPriority.values();
      TaskType[] types = TaskType.values();

      request.setAttribute("categories", categories);
      request.setAttribute("priorities", priorities);
      request.setAttribute("types", types);

      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pages/taskAdd.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {

    }
  }

  public void addTaskPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    ServletOutputStream out = response.getOutputStream();
    try {

      HttpSession session = request.getSession();
      User user = Utils.getUserSession(session);
      Connection connection = Utils.getConnection(request);
      TaskServiceImpl taskService = new TaskServiceImpl(connection);

      String name = request.getParameter("name");
      String description = request.getParameter("description");
      String category = request.getParameter("category");
      String priority = request.getParameter("priority");
      String type = request.getParameter("type");
      String dueDate = request.getParameter("due_date");

      int categoryId = Integer.parseInt(category);
      TaskPriority taskPriority = TaskPriority.valueOf(priority);
      TaskType taskType = TaskType.valueOf(type);

      Task task = new Task(name, description, user.getId(), categoryId, taskPriority, taskType, TaskStatus.UNDONE, dueDate);

      taskService.create(task);

      response.sendRedirect(URL_TASK);
    } catch (Exception e) {
      e.printStackTrace();
      RedirectHelper.continueWithError(request, response, e.getMessage(), "/WEB-INF/views/pages/taskAdd.jsp");
    }
  }
}
