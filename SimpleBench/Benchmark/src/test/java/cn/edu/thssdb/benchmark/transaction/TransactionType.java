package cn.edu.thssdb.benchmark.transaction;

public enum TransactionType {
  T1("T1"),
  T2("T2"),
  T3("T3"),
  T4("T4");

  private final String type;

  TransactionType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
