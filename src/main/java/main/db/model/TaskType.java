package main.db.model;

public enum TaskType {
  DAY, WEEK, MONTH, YEAR;

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
