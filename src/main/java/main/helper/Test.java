/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.helper;

import main.db.model.TaskPriority;

public class Test {

  public static void main(String[] args) {
    TaskPriority[] priorities = TaskPriority.values();
    for (TaskPriority priority : priorities) {
      System.out.println(priority);
    }
  }
}
