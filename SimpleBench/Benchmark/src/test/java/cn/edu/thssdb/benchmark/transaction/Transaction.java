package cn.edu.thssdb.benchmark.transaction;

import cn.edu.thssdb.benchmark.common.Client;
import org.apache.thrift.TException;

public class Transaction {
  String[] sqlList;

  public Transaction(String[] sqlList) {
    this.sqlList = sqlList;
  }

  public void execute(Client client) throws TException {
    // todo merge these statements in one rpc
    client.executeStatement("begin transaction;");
    for (String sql : sqlList) {
      client.executeStatement(sql);
    }
    client.executeStatement("commit;");
  }

  public int getSize() {
    return sqlList.length;
  }
}
