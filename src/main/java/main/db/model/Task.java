/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.model;

import java.time.LocalDateTime;
import main.db.service.CategoryServiceImpl;

public class Task {

  private final int id;
  private String name;
  private String description = "";
  private int userId, categoryId, parentId;
  private TaskPriority priority;
  private TaskType type;
  private TaskStatus status;
  private String dueDate;
  private Category category = null;

  public Task(int id, String name, String description, int userId, int categoryId, int parentId, TaskPriority priority, TaskType type, TaskStatus status, String dueDate)
  {
    this.id = id;
    this.name = name;
    this.description = description;
    this.userId = userId;
    this.categoryId = categoryId;
    this.parentId = parentId;
    this.priority = priority;
    this.type = type;
    this.status = status;
    this.dueDate = dueDate;
  }
  
  public Task(int id, String name, String description, int userId, int categoryId, TaskPriority priority, TaskType type, TaskStatus status, String dueDate)
  {
    this(id, name, description, userId, categoryId, 0, priority, type, status, dueDate);
  }

  public Task(int id)
  {
    this.id = id;
  }

  public Task(String name, String description, int userId, int categoryId, TaskPriority priority, TaskType type, TaskStatus status, String dueDate)
  {
    this(0, name, description, userId, categoryId, priority, type, status, dueDate);
  }

  public int getId()
  {
    return id;
  }

  public int getUser()
  {
    return userId;
  }

  public Category getCategory()
  {
    return category;
  }

  public TaskPriority getPriority()
  {
    return priority;
  }

  public TaskType getType()
  {
    return type;
  }

  public TaskStatus getStatus()
  {
    return status;
  }

  public String getDueDate()
  {
    return dueDate;
  }

  public int getUserId()
  {
    return userId;
  }

  public int getCategoryId()
  {
    return categoryId;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public int getParentId()
  {
    return parentId;
  }

  public void setCategory(Category category)
  {
    this.category = category;
  }

  public void setPriority(TaskPriority priority)
  {
    this.priority = priority;
  }

  public void setType(TaskType type)
  {
    this.type = type;
  }

  public void setStatus(TaskStatus status)
  {
    this.status = status;
  }

  public void setDueDate(String dueDate)
  {
    this.dueDate = dueDate;
  }

  public void setUserId(int userId)
  {
    this.userId = userId;
  }

  public void setCategoryId(int categoryId)
  {
    this.categoryId = categoryId;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public void setParentId(int parentId)
  {
    this.parentId = parentId;
  }

}
