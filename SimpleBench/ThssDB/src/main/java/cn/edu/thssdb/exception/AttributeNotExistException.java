package cn.edu.thssdb.exception;

public class AttributeNotExistException extends RuntimeException {
  private String key;

  public AttributeNotExistException() {
    super();
    this.key = null;
  }

  public AttributeNotExistException(String key) {
    super();
    this.key = key;
  }

  @Override
  public String getMessage() {
    if (key == null) return "Exception: attribute doesn't exist!";
    else return "Exception: attribute \"" + this.key + "\" doesn't exist!";
  }
}
