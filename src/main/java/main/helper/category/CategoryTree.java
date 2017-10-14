/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.helper.category;

import java.util.ArrayList;
import java.util.Arrays;
import main.db.model.Category;

/**
 *
 * @author root
 */
public class CategoryTree {

  private final ArrayList<Integer> show = new ArrayList<>();
  private final ArrayList<CategoryNode> sortedNodes = new ArrayList<CategoryNode>();
  private final ArrayList<CategoryNode> categories = new ArrayList<CategoryNode>();

  public CategoryTree(ArrayList<Category> categories) {
    for (Category cat : categories) {
      this.categories.add(new CategoryNode(cat));
    }
  }

  public void print() {
    for (CategoryNode category : sortedNodes) {
      for (int i = 0; i <= category.getLevel(); i++) {
        System.out.print(" ");
      }
      System.out.println(category.getCategory().getName() + " id: " + category.getCategory().getId() + " level: " + category.getLevel());
    }
  }

  public ArrayList<CategoryNode> getNodes() {
    ArrayList<CategoryNode> nodes = categories;

    for (CategoryNode node : nodes) {
      show.add(node.getCategory().getId());
    }

    for (CategoryNode category : categories) {
      for (CategoryNode node : nodes) {
        if (category.getCategory().getParentId() == node.getCategory().getId()) {
          node.setChild(category);
          category.upLevel(node.getLevel());
        }
      }
    }

    sort(nodes);

    return sortedNodes;
  }

  private void sort(ArrayList<CategoryNode> nodes) {
    for (CategoryNode node : nodes) {
      if (show.contains(node.getCategory().getId())) {
        show.removeAll(Arrays.asList(node.getCategory().getId()));
        sortedNodes.add(node);
      }
      if (node.hasChildren()) {
        sort(node.getChildren());
      }
    }
  }

}
