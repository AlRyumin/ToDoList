/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.helper.category;

import java.util.TreeMap;
import main.db.model.Category;

/**
 *
 * @author root
 */
public class CategoryNode {

  private Category category;
  private final TreeMap<Integer, CategoryNode> children = new TreeMap<Integer, CategoryNode>();
  private byte level = 0;

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

  public TreeMap<Integer, CategoryNode> getChildren() {
    return children;
  }

  public boolean hasChildren() {
    return children.isEmpty() == false;
  }

  public void setChild(CategoryNode child) {
    children.put(child.getCategory().getId(), child);
  }

  public void upLevel(byte level) {
    this.level = (byte) (level + 1);
  }

}
