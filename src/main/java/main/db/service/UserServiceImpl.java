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
import main.db.model.User;

/**
 *
 * @author root
 */
public class UserServiceImpl implements UserService {

  private Connection connection;

  public UserServiceImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public User getUser(int id) throws SQLException {
    User user = null;
    String query = "SELECT * FROM user WHERE id = ?";

    PreparedStatement prepState = connection.prepareStatement(query);

    prepState.setInt(1, id);

    ResultSet result = prepState.executeQuery();

    if (result.next()) {
      String name = result.getString("name");
      String email = result.getString("email");
      String password = result.getString("password");
      String role = result.getString("role");
      user = new User(id, name, email, password, role);
    }

    return user;
  }

  public User getUser(String userEmail, String userPassword) throws SQLException {
    User user = null;
    String query = "SELECT * FROM user WHERE email = ? AND password = ?";

    PreparedStatement prepState = connection.prepareStatement(query);

    prepState.setString(1, userEmail);
    prepState.setString(2, userPassword);

    ResultSet result = prepState.executeQuery();

    if (result.next()) {
      int id = result.getInt("id");
      String name = result.getString("name");
      String email = result.getString("email");
      String password = result.getString("password");
      String role = result.getString("role");
      user = new User(id, name, email, password, role);
    }

    return user;
  }

  @Override
  public void createUser(User user) throws SQLException {
    String sql = "Insert into user(name, email, password) values (?,?,?)";

    PreparedStatement prepState = connection.prepareStatement(sql);

    prepState.setString(1, user.getName());
    prepState.setString(2, user.getEmail());
    prepState.setString(3, user.getPassword());

    prepState.executeUpdate();
  }

  @Override
  public void updateUser(int id, User user) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void deleteUser(int id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
