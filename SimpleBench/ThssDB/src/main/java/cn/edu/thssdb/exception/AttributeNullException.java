package cn.edu.thssdb.exception;

public class AttributeNullException extends RuntimeException {
  private String key;

  public AttributeNullException() {
    super();
    this.key = null;
  }

  public AttributeNullException(String key) {
    super();
    this.key = key;
  }

  @Override
  public String getMessage() {
    if (key == null) return "Exception: Attribute cannot be null exception!";
    else return "Exception: Attribute \"" + this.key + "\" cannot be null exception!";
  }
}
