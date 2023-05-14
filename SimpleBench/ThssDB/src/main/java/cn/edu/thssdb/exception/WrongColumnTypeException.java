package cn.edu.thssdb.exception;

public class WrongColumnTypeException extends RuntimeException {
  private String type;

  public WrongColumnTypeException() {
    super();
    this.type = null;
  }

  public WrongColumnTypeException(String type) {
    super();
    this.type = type;
  }

  @Override
  public String getMessage() {
    if (type == null) return "Exception: Wrong column type!";
    else return "Exception: Wrong column type \"" + this.type + "\"!";
  }
}
