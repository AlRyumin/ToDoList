/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db.model;

/**
 *
 * LOW
 * NORMAL
 * HIGH
 * IMPORTANT
 *
 */
public enum TaskPriority {
  LOW, NORMAL, HIGH, IMPORTANT;

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
