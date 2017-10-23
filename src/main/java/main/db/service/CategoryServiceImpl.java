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
import java.util.ArrayList;
import main.db.model.Category;

/**
 *
 * @author root
 */
public class CategoryServiceImpl implements CategoryService {

  private final Connection connection;
  private static final String TABLE_NAME = "category";

  public CategoryServiceImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void createCategory(Category category) throws SQLException {
    String query = "INSERT INTO " + TABLE_NAME + "(user_id, parent_id, name) VALUES(?, ?, ?)";
    int userId = category.getUserId();
    int parentId = category.getParentId();
    String name = category.getName();

    PreparedStatement prepState = connection.prepareStatement(query);

    prepState.setInt(1, userId);
    prepState.setInt(2, parentId);
    prepState.setString(3, name);

    prepState.execute();
  }

  @Override
  public Category getCategory(int id) throws SQLException {
    Category category = null;
    String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

    PreparedStatement prepState = connection.prepareStatement(query);

    prepState.setInt(1, id);

    ResultSet result = prepState.executeQuery();

    if (result.next()) {
      int userId = result.getInt("user_id");
      int parentId = result.getInt("parent_id");
      String name = result.getString("name");
      category = new Category(id, userId, parentId, name);
    }

    return category;
  }

  @Override
  public ArrayList<Category> getCategories(int userId) throws SQLException {
    return getCategories(userId, 0);
  }

  public ArrayList<Category> getCategories(int userId, int exclude) throws SQLException {
    ArrayList<Category> categories = new ArrayList<Category>();
    String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? AND id != ?";

    PreparedStatement prepState = connection.prepareStatement(query);

    prepState.setInt(1, userId);
    prepState.setInt(2, exclude);

    boolean hasResult = prepState.execute();

    while (hasResult) {
      ResultSet result = prepState.getResultSet();
      while (result.next()) {
        int id = result.getInt("id");
        int parentId = result.getInt("parent_id");
        String name = result.getString("name");
        Category category = new Category(id, userId, parentId, name);
        categories.add(category);
      }
      result.close();
      hasResult = prepState.getMoreResults();
    }

    prepState.close();

    return categories;
  }

  public ArrayList<Integer> getCategoryChildrenIds(int id) {
    final ArrayList<Integer> childrenIds = new ArrayList<>();

    class Children {
      public void getChildren(int parentId) {
        try {
          String query = "SELECT id FROM " + TABLE_NAME + " WHERE parent_id = ?";

          PreparedStatement prepState = connection.prepareStatement(query);

          prepState.setInt(1, parentId);

          boolean hasResult = prepState.execute();

          while (hasResult) {
            ResultSet result = prepState.getResultSet();
            while (result.next()) {
              int id = result.getInt("id");
              childrenIds.add(id);
              getChildren(id);
            }
            result.close();
            hasResult = prepState.getMoreResults();
          }

          prepState.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    Children children = new Children();
    children.getChildren(id);

    return childrenIds;
  }

  public boolean update(int id, int userId, int parentId, String name) throws SQLException {
    String query = "UPDATE " + TABLE_NAME + " SET user_id = ?, parent_id = ?, name = ? WHERE id = ?";
    PreparedStatement prepState = connection.prepareStatement(query);

    prepState.setInt(1, userId);
    prepState.setInt(2, parentId);
    prepState.setString(3, name);
    prepState.setInt(4, id);

    return prepState.execute();
  }

  @Override
  public void delete(int id) throws SQLException {
    String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
    PreparedStatement prepState = connection.prepareStatement(query);

    prepState.setInt(1, id);

    prepState.execute();
  }

  protected String setQueryCategoriesParams(ArrayList<Integer> ids, String queryParams) {
    for (Integer id : ids)
      queryParams += " OR category_id = " + id;

    return queryParams;
  }
}
