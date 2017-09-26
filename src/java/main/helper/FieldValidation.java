/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.helper;

public class FieldValidation {

  public static boolean isFieldEmpty(String field) {
    return field == null || field.isEmpty();
  }
}
