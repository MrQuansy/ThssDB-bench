package cn.edu.thssdb.schema;

import cn.edu.thssdb.type.ColumnType;

import java.util.Objects;

public class Column implements Comparable<Column> {
  private String name;
  private ColumnType type;
  private int primary;
  private boolean notNull;
  private int maxLength;

  public Column(String name, ColumnType type, int primary, boolean notNull, int maxLength) {
    this.name = name;
    this.type = type;
    this.primary = primary;
    this.notNull = notNull;
    this.maxLength = maxLength;
  }

  public Column(String name, ColumnType type, int primary, boolean notNull) {
    this.name = name;
    this.type = type;
    this.primary = primary;
    this.notNull = notNull;
  }

  /** --- GET methods --- */
  public String getName() {
    return this.name;
  }

  public ColumnType getType() {
    return this.type;
  }

  public int isPrimary() {
    return this.primary;
  }

  public boolean isNotNull() {
    return this.notNull;
  }

  public int getMaxLength() {
    return this.maxLength;
  }

  /** --- SET methods --- */
  public void setPrimary(int new_primary) {
    this.primary = new_primary;
  }

  public void setNotNull(boolean new_notNull) {
    this.notNull = new_notNull;
  }

  @Override
  public int compareTo(Column e) {
    return name.compareTo(e.name);
  }

  public String toString() {
    return name + ',' + type + ',' + primary + ',' + notNull + ',' + maxLength;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name)
        + Objects.hash(type)
        + Objects.hash(primary)
        + Objects.hash(notNull)
        + Objects.hash(maxLength);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Column)) {
      return false;
    }
    Column col = (Column) obj;
    return Objects.equals(name, col.name)
        && Objects.equals(type, col.type)
        && Objects.equals(primary, col.primary)
        && Objects.equals(notNull, col.notNull)
        && Objects.equals(maxLength, col.maxLength);
  }
}
