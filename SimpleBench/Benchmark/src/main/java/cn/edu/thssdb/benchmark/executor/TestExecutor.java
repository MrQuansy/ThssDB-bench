package cn.edu.thssdb.benchmark.executor;

import cn.edu.thssdb.benchmark.common.Client;
import cn.edu.thssdb.benchmark.common.TableSchema;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementResp;
import org.apache.thrift.TException;

public abstract class TestExecutor implements AutoCloseable {

  public abstract void close();

  protected void createTable(TableSchema tableSchema, Client client) throws TException {
    StringBuilder sb = new StringBuilder("CREATE TABLE " + tableSchema.tableName + " (");
    for (int columnId = 0; columnId < tableSchema.columns.size(); columnId++) {
      sb.append(tableSchema.columns.get(columnId))
          .append(" ")
          .append(tableSchema.types.get(columnId).getType());
      if (tableSchema.notNull.get(columnId)) {
        sb.append(" NOT NULL");
      }
      sb.append(",");
    }
    sb.append("primary key(")
        .append(tableSchema.columns.get(tableSchema.primaryKeyColumnIndex))
        .append(")");
    sb.append(");");
    System.out.println(sb);
    ExecuteStatementResp resp = client.executeStatement(sb.toString());
    if (resp.status.code == 0) {
      System.out.println("create table " + tableSchema.tableName + " finished!");
    }
  }
}
