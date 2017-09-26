/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.model;

import java.util.ArrayList;

public class Category {

  private int id, parentId;
  private final int userId;
  private String name;

  public Category(int userId, String name) {
    this.userId = userId;
    this.name = name;
  }

  public Category(int userId, int parentId, String name) {
    this.userId = userId;
    this.parentId = parentId;
    this.name = name;
  }

  public Category(int id, int userId, int parentId, String name) {
    this.id = id;
    this.userId = userId;
    this.parentId = parentId;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public int getUserId() {
    return userId;
  }

  public int getParentId() {
    return parentId;
  }

  public String getName() {
    return name;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

  public void setName(String name) {
    this.name = name;
  }

}
