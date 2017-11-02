/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import main.db.model.Category;
import main.db.model.Task;
import main.db.model.TaskPriority;
import main.db.model.TaskStatus;
import main.db.model.TaskType;
import main.db.model.User;
import main.helper.DateHelper;
import static main.helper.FieldValidation.*;

public class TaskServiceImpl implements TaskService {

  private Connection connection;
  private static final String TABLE_NAME = "task";

  public TaskServiceImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(Task task) {
    try {
      String query = "INSERT INTO " + TABLE_NAME + " (user_id, name, description, category_id, priority, type, status, due_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement prepState = connection.prepareStatement(query);

      String dueDate = task.getDueDate();
      Date date = DateHelper.dateStringToSqlDate(dueDate);

      prepState.setInt(1, task.getUserId());
      prepState.setString(2, task.getName());
      prepState.setString(3, task.getDescription());
      prepState.setInt(4, task.getCategoryId());
      prepState.setString(5, task.getPriority().toString());
      prepState.setString(6, task.getType().toString());
      prepState.setString(7, task.getStatus().toString());
      prepState.setDate(8, date);

      prepState.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Task task) {
    try {
      String query = "UPDATE " + TABLE_NAME + " SET user_id = ?, category_id = ?, priority = ?, type = ?, status = ?, due_date = ?, name = ?, description = ? WHERE id = ?";

      PreparedStatement prepState = connection.prepareStatement(query);

      String dueDate = task.getDueDate();
      Date date = DateHelper.dateStringToSqlDate(dueDate);

      prepState.setInt(1, task.getUser());
      prepState.setInt(2, task.getCategoryId());
      prepState.setString(3, task.getPriority().toString().toLowerCase());
      prepState.setString(4, task.getType().toString().toLowerCase());
      prepState.setString(5, task.getStatus().toString().toLowerCase());
      prepState.setDate(6, date);
      prepState.setString(7, task.getName());
      prepState.setString(8, task.getDescription());
      prepState.setInt(9, task.getId());

      prepState.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Task get(int id) {
    Task task = null;
    try {
      String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

      PreparedStatement prepState = connection.prepareStatement(query);

      prepState.setInt(1, id);

      ResultSet result = prepState.executeQuery();

      if (result.next()) {
        int taskId = result.getInt("id");
        String name = result.getString("name");
        String description = result.getString("description");
        int userId = result.getInt("user_id");
        int categoryId = result.getInt("category_id");
        TaskPriority priority = TaskPriority.valueOf(result.getString("priority").toUpperCase());
        TaskType type = TaskType.valueOf(result.getString("type").toUpperCase());
        TaskStatus status = TaskStatus.valueOf(result.getString("status").toUpperCase());
        Date dueDate = result.getDate("due_date");

        String date = DateHelper.sqlDateToString(dueDate);

        task = new Task(taskId, name, description, userId, categoryId, priority, type, status, date);

        CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
        Category category = categoryService.getCategory(task.getCategoryId());
        task.setCategory(category);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return task;
  }

  /*
  TreeMap<String, String> params
  userId
  categoryId
  priority
  type
  status
  dueDate
   */
  public ArrayList<Task> get(TreeMap<String, String> params) {
    ArrayList<Task> tasks = new ArrayList<>();
    String queryParams = "";
    try {
      if (isFieldEmpty(params.get("priority")) == false)
        queryParams += " AND priority = ?";
      if (isFieldEmpty(params.get("type")) == false)
        queryParams += " AND type = ?";
      if (isFieldEmpty(params.get("status")) == false)
        queryParams += " AND status = ?";
      if (isFieldEmpty(params.get("categoryId")) == false) {
        queryParams += " AND category_id = ?";
        CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
        ArrayList<Integer> categoriesIds = categoryService.getCategoryChildrenIds(Integer.parseInt(params.get("categoryId")));
        queryParams += getQueryCategoriesParams(categoriesIds);
      }

      String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? AND due_date = ?" + queryParams;

      int countParams = 3;

      PreparedStatement prepState = connection.prepareStatement(query);

      prepState.setInt(1, Integer.parseInt(params.get("userId")));
      prepState.setDate(2, DateHelper.dateStringToSqlDate(params.get("dueDate")));
      if (isFieldEmpty(params.get("priority")) == false) {
        prepState.setString(countParams, params.get("priority"));
        countParams++;
      }
      if (isFieldEmpty(params.get("type")) == false) {
        prepState.setString(countParams, params.get("type"));
        countParams++;
      }
      if (isFieldEmpty(params.get("status")) == false) {
        prepState.setString(countParams, params.get("status"));
        countParams++;
      }
      if (isFieldEmpty(params.get("categoryId")) == false) {
        prepState.setInt(countParams, Integer.parseInt(params.get("categoryId")));
        countParams++;
      }

      boolean hasResult = prepState.execute();

      while (hasResult) {
        ResultSet result = prepState.getResultSet();
        while (result.next()) {
          int taskId = result.getInt("id");
          String name = result.getString("name");
          String description = result.getString("description");
          int userId = result.getInt("user_id");
          int categoryId = result.getInt("category_id");
          TaskPriority priority = TaskPriority.valueOf(result.getString("priority").toUpperCase());
          TaskType type = TaskType.valueOf(result.getString("type").toUpperCase());
          TaskStatus status = TaskStatus.valueOf(result.getString("status").toUpperCase());
          Date dueDate = result.getDate("due_date");

          String date = DateHelper.sqlDateToString(dueDate);

          Task task = new Task(taskId, name, description, userId, categoryId, priority, type, status, date);

          CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
          Category category = categoryService.getCategory(task.getCategoryId());
          task.setCategory(category);

          tasks.add(task);
        }
        hasResult = prepState.getMoreResults();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return tasks;
  }

  @Override
  public ArrayList<Task> get(User user) {
    ArrayList<Task> tasks = new ArrayList<>();
    try {
      String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";

      PreparedStatement prepState = connection.prepareStatement(query);

      prepState.setInt(1, user.getId());

      boolean hasResult = prepState.execute();

      while (hasResult) {
        ResultSet result = prepState.getResultSet();
        while (result.next()) {
          int taskId = result.getInt("id");
          String name = result.getString("name");
          String description = result.getString("description");
          int userId = result.getInt("user_id");
          int categoryId = result.getInt("category_id");
          TaskPriority priority = TaskPriority.valueOf(result.getString("priority").toUpperCase());
          TaskType type = TaskType.valueOf(result.getString("type").toUpperCase());
          TaskStatus status = TaskStatus.valueOf(result.getString("status").toUpperCase());
          Date dueDate = result.getDate("due_date");

          String date = DateHelper.sqlDateToString(dueDate);

          Task task = new Task(taskId, name, description, userId, categoryId, priority, type, status, date);

          CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
          Category category = categoryService.getCategory(task.getCategoryId());
          task.setCategory(category);

          tasks.add(task);
        }
        hasResult = prepState.getMoreResults();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return tasks;
  }

  @Override
  public void delete(int id) {
    try {
      String query = "DELETE FROM  " + TABLE_NAME + " WHERE id = ?";

      PreparedStatement prepState = connection.prepareStatement(query);

      prepState.setInt(1, id);

      prepState.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private String getQueryCategoriesParams(ArrayList<Integer> ids) {
    String queryParams = "";
    for (Integer id : ids)
      queryParams += " OR category_id = " + id;

    return queryParams;
  }
}
