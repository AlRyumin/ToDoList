/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import main.db.model.Category;
import main.db.model.Task;
import main.db.model.TaskPriority;
import main.db.model.TaskStatus;
import main.db.model.TaskType;
import main.db.model.User;
import main.helper.DateHelper;

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
      Timestamp date = DateHelper.dateStringToTimeStamp(dueDate);

      prepState.setInt(1, task.getUserId());
      prepState.setString(2, task.getName());
      prepState.setString(3, task.getDescription());
      prepState.setInt(4, task.getCategoryId());
      prepState.setString(5, task.getPriority().toString());
      prepState.setString(6, task.getType().toString());
      prepState.setString(7, task.getStatus().toString());
      prepState.setTimestamp(8, date);

      prepState.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Task task) {
    try {
      String query = "UPDATE " + TABLE_NAME + " SET user_id = ?, category_id = ?, priority = ?, type = ?, status = ?, due_date = ? WHERE id = ?";

      PreparedStatement prepState = connection.prepareStatement(query);

      String dueDate = task.getDueDate();
      Timestamp date = DateHelper.dateStringToTimeStamp(dueDate);

      prepState.setInt(1, task.getUser());
      prepState.setInt(2, task.getCategoryId());
      prepState.setString(3, task.getPriority().toString().toLowerCase());
      prepState.setString(4, task.getType().toString().toLowerCase());
      prepState.setString(5, task.getStatus().toString().toLowerCase());
      prepState.setTimestamp(6, date);
      prepState.setInt(7, task.getId());

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

      int userId = result.getInt("user_id");
      String name = result.getString("name");
      String description = result.getString("description");
      int categoryId = result.getInt("category_id");
      TaskPriority priority = TaskPriority.valueOf(result.getString("priority").toUpperCase());
      TaskType type = TaskType.valueOf(result.getString("type").toUpperCase());
      TaskStatus status = TaskStatus.valueOf(result.getString("status").toUpperCase());
      String dueDate = result.getString("due_date");

      task = new Task(id, name, description, userId, categoryId, priority, type, status, dueDate);

      CategoryServiceImpl categoryService = new CategoryServiceImpl(connection);
      Category category = categoryService.getCategory(task.getCategoryId());
      task.setCategory(category);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return task;
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
          Timestamp dueDate = result.getTimestamp("due_date");

          String date = DateHelper.timeStapmToString(dueDate);

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

}
