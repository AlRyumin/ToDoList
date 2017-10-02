/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.helper.category;

import java.util.ArrayList;
import main.db.model.Category;

/**
 *
 * @author root
 */
public class CategoryNode {

  private Category category;
  private ArrayList<CategoryNode> children = new ArrayList<>();
  private int level = 0;

  public CategoryNode(Category category) {
    super();
    this.category = category;
  }

  public int getLevel() {
    return level;
  }

  public Category getCategory() {
    return category;
  }

  public ArrayList<CategoryNode> getChildren() {
    return children;
  }

  public boolean hasChildren() {
    return children.isEmpty() == false;
  }

  public void setChild(CategoryNode child) {
    children.add(child);
  }

  public void upLevel(int level) {
    this.level = level + 1;
  }

}
