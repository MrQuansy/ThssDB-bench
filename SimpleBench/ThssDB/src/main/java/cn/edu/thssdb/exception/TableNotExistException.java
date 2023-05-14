package cn.edu.thssdb.exception;

public class TableNotExistException extends RuntimeException {
  private String tableName;

  public TableNotExistException(String tableName) {
    super();
    this.tableName = tableName;
  }

  @Override
  public String getMessage() {
    if (tableName == null) return "Exception: Table does not exist!";
    else return "Exception: Table \"" + this.tableName + "\" does not exist!";
  }
}
