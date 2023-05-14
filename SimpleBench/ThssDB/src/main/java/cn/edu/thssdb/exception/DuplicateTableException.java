package cn.edu.thssdb.exception;

public class DuplicateTableException extends RuntimeException {
  private String key;

  public DuplicateTableException() {
    super();
    key = null;
  }

  public DuplicateTableException(String key) {
    super();
    this.key = key;
  }

  @Override
  public String getMessage() {
    if (key == null) return "Exception: create table caused duplicated tables!";
    else return "Exception: create table \"" + key + "\" caused duplicated tables!";
  }
}
