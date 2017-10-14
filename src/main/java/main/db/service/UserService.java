/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import main.db.model.User;

/**
 *
 * @author root
 */
public interface UserService {
  User getUser(int id) throws SQLException;
  void createUser(User user) throws SQLException;
  void updateUser(int id, User user) throws SQLException;
  void deleteUser(int id) throws SQLException;
}
