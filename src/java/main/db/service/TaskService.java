/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.service;

import java.util.List;
import main.db.model.Task;
import main.db.model.User;

public interface TaskService {
  public void create(Task task);
  public void update(Task task);
  public Task get(int id);
  public List<Task> get(User user);
  public void delete(int id);
}
