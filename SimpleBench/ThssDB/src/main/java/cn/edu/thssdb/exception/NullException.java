package cn.edu.thssdb.exception;

/** 非空约束 */
public class NullException extends RuntimeException {
  private String mColumnName;

  public NullException(String column_name) {
    super();
    this.mColumnName = column_name;
  }

  @Override
  public String getMessage() {
    return "Exception: the column named " + mColumnName + " should not be null!";
  }
}
