/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.helper.category;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import main.db.model.Category;

/**
 *
 * @author root
 */
public class CategoryTree {

  private final ArrayList<CategoryNode> sortedNodes = new ArrayList<>();
  private final TreeMap<Integer, CategoryNode> categories;

  public CategoryTree(ArrayList<Category> categories)
  {
    this.categories = new TreeMap<Integer, CategoryNode>();
    for (Category cat : categories)
      this.categories.put(cat.getId(), new CategoryNode(cat));
  }

  public TreeMap<Integer, CategoryNode> getNodes()
  {
    for (Map.Entry<Integer, CategoryNode> entry : categories.entrySet()) {
      Integer key = entry.getKey();
      CategoryNode node = entry.getValue();

      int parentId = node.getCategory().getParentId();
      if (categories.get(parentId) == null)
        continue;
      CategoryNode parentNode = categories.get(parentId);
      parentNode.setChild(node);
    }

    ArrayList<Integer> toRemove = new ArrayList<>();

    for (Map.Entry<Integer, CategoryNode> entry : categories.entrySet()) {
      Integer key = entry.getKey();
      CategoryNode node = entry.getValue();
      if (node.getCategory().getParentId() != 0)
        toRemove.add(key);
    }

    for (Integer l : toRemove)
      categories.remove(l);

    sort();

    return categories;
  }

  public ArrayList<CategoryNode> getListNodes()
  {
    return sortedNodes;
  }

  private void sort()
  {
    byte level = 0;
    sort(categories, level);
  }

  private void sort(TreeMap<Integer, CategoryNode> nodes, byte level)
  {
    for (Map.Entry<Integer, CategoryNode> entry : nodes.entrySet()) {
      CategoryNode node = entry.getValue();

      node.increaseLevel(level);

      sortedNodes.add(node);

      if (node.hasChildren())
        sort(node.getChildren(), node.getLevel());
    }
  }
}
