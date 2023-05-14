package cn.edu.thssdb.exception;

public class NotIntException extends RuntimeException {
  private String numericLiteral;

  public NotIntException(String numericLiteral) {
    super();
    this.numericLiteral = numericLiteral;
  }

  @Override
  public String getMessage() {
    if (numericLiteral == null) return "Exception: numeric Literal in String doesn't exist!";
    else
      return "Exception: numeric Literal \""
          + this.numericLiteral
          + "\" in String is not a integer!";
  }
}
