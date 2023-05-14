package cn.edu.thssdb.exception;

public class WrongColumnConstraintException extends RuntimeException {
  private String columnConstraint;

  public WrongColumnConstraintException(String columnConstraint) {
    super();
    this.columnConstraint = columnConstraint;
  }

  @Override
  public String getMessage() {
    if (columnConstraint == null) return "Exception: Wrong column constraint!";
    else return "Exception: Wrong column constraint \"" + this.columnConstraint + "\"!";
  }
}
