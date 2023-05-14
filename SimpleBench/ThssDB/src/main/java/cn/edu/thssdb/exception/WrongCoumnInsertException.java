package cn.edu.thssdb.exception;

import cn.edu.thssdb.type.ColumnType;

public class WrongCoumnInsertException extends RuntimeException {
  private String type;
  private Comparable value;

  public WrongCoumnInsertException() {
    super();
    this.type = null;
  }

  public WrongCoumnInsertException(Comparable value, ColumnType type) {
    super();
    if (type == ColumnType.STRING) this.type = "STRING";
    else if (type == ColumnType.INT) this.type = "INT";
    else if (type == ColumnType.DOUBLE) this.type = "DOUBLE";
    else if (type == ColumnType.FLOAT) this.type = "FLOAT";
    else if (type == ColumnType.LONG) this.type = "LONG";
    this.value = value;
  }

  @Override
  public String getMessage() {
    if (type == null) return "Exception: Insert wrong column type!";
    else return "Exception: Insert into column type " + this.type + " with " + this.value + "!";
  }
}
