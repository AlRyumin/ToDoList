/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.service;

import java.sql.SQLException;
import java.util.List;
import main.db.model.Category;

public interface CategoryService {
  public void createCategory(Category category) throws SQLException;
  public Category getCategory(int id) throws SQLException;
  public List<Category> getCategories(int userId) throws SQLException;
  public boolean update(int id, int userId, int parentId, String name) throws SQLException;
  public void delete(int id) throws SQLException;
}
