package cn.edu.thssdb.benchmark.executor;

import cn.edu.thssdb.benchmark.common.Client;
import cn.edu.thssdb.benchmark.common.Constants;
import cn.edu.thssdb.benchmark.common.DataType;
import cn.edu.thssdb.benchmark.common.TableSchema;
import cn.edu.thssdb.benchmark.generator.BaseDataGenerator;
import cn.edu.thssdb.benchmark.generator.SimpleDataGenerator;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementResp;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CRUDTestExecutor extends TestExecutor {

  private BaseDataGenerator dataGenerator;
  private Map<String, TableSchema> schemaMap;
  private Map<String, Set<List<Object>>> dataMap; // index by primary key
  private static int successStatusCode = 0;
  public int points = 100;

  private Client client;

  public CRUDTestExecutor() throws TException {
    dataGenerator = new SimpleDataGenerator();
    schemaMap = dataGenerator.getSchemaMap();
    dataMap = new HashMap<>();
    client = new Client("127.0.0.1", 6667);
  }

  public void createAndUseDB() throws TException {
    ExecuteStatementResp resp = client.executeStatement("create database db1;");
    if (resp.status.code == successStatusCode) {
      System.out.println("Create database db1 finished");
    }
    resp = client.executeStatement("use db1;");
    if (resp.status.code == successStatusCode) {
      System.out.println("Use db1 finished");
    }
  }

  /*
   CREATE TABLE test_table1 (
   column1 int,
   column2 long PRIMARY KEY,
   column3 double,
   column4 float,
   column5 string,
   column6 int,
   column7 long,
   column8 double,
   column9 float,
   column10 string
     );
  */
  public void createTable() throws TException {
    for (TableSchema tableSchema : schemaMap.values()) {
      createTable(tableSchema, client);
    }
  }

  public void insertData() throws TException {
    for (Map.Entry<String, TableSchema> tableSchemaEntry : schemaMap.entrySet()) {
      String tableName = tableSchemaEntry.getKey();
      Set<List<Object>> tableData = new HashSet<>();
      for (int rowid = 0; rowid < Constants.rowCount; rowid++) {
        List<Object> rowData = new ArrayList<>();
        StringBuilder sb = new StringBuilder("Insert into ");
        sb.append(tableName);
        sb.append(" values(");

        for (int columnId = 0; columnId < Constants.columnCount; columnId++) {
          Object dataItem = dataGenerator.generateValue(tableName, rowid, columnId);
          rowData.add(dataItem);
          sb.append(dataItem);
          if (columnId != Constants.columnCount - 1) {
            sb.append(',');
          }
        }
        sb.append(");");
        client.executeStatement(sb.toString());
        tableData.add(rowData);
      }
      dataMap.put(tableName, tableData);
    }
  }

  public void queryData() throws TException {
    // test 1: query all rows
    String querySql = "select * from test_table1;";
    ExecuteStatementResp resp = client.executeStatement(querySql);
    TableSchema tableSchema = dataGenerator.getTableSchema("test_table1");
    Set<List<Object>> queryResult = convertData(resp.rowList, tableSchema.types);
    Set<List<Object>> tableData = dataMap.get("test_table1");

    if (!equals(queryResult, tableData)) {
      System.out.println("Error!");
    }

    // test2: query with filter for non primary key
    querySql = "select * from test_table2 where column0 = 5;";
    resp = client.executeStatement(querySql);
    tableSchema = dataGenerator.getTableSchema("test_table2");
    queryResult = convertData(resp.rowList, tableSchema.types);
    tableData = dataMap.get("test_table2");

    Set<List<Object>> expectedResult = new HashSet<>();
    for (List<Object> rowData : tableData) {
      if (Objects.equals(rowData.get(0), 5)) {
        expectedResult.add(rowData);
      }
    }

    if (!equals(queryResult, expectedResult)) {
      System.out.println("Error!");
    }

    // test3: query with filter for primary key
    querySql = "select * from test_table3 where column3 < 5;";
    resp = client.executeStatement(querySql);
    tableSchema = dataGenerator.getTableSchema("test_table3");
    queryResult = convertData(resp.rowList, tableSchema.types);
    tableData = dataMap.get("test_table3");

    expectedResult = new HashSet<>();
    for (List<Object> rowData : tableData) {
      if ((int) rowData.get(0) < 5) {
        expectedResult.add(rowData);
      }
    }

    if (!equals(queryResult, expectedResult)) {
      System.out.println("Error!");
    }

    //
    //        querySql = "select column1,column3 from test_table2 where column2 = 100;";
    //        resp = client.executeStatement(querySql);
    //
    //        //query join on column1
    //        querySql = "select column1,test_table3.column2,test_table4.column2 from test_table3
    // join test_table4 on column1;";
    //        resp = client.executeStatement(querySql);
    //
    //        //query join on column1 where column1 = 100;
    //        querySql = "select column1,test_table3.column2,test_table4.column2 from test_table3
    // join test_table4 on column1 where column1 = 100;";
    //        resp = client.executeStatement(querySql);
    //
    //        //query join on column1 where column3 = 100;
    //        querySql = "select column1,test_table3.column2,test_table4.column2 from test_table3
    // join test_table4 on column1 where column3 = 100;";
    //        resp = client.executeStatement(querySql);
  }

  public void updateAndQueryData() throws TException {
    // 1. 更新整列值
    String updateSql = "update test_table1 set column5 = 100;";
    client.executeStatement(updateSql);

    Set<List<Object>> tableData = dataMap.get("test_table1");
    for (List<Object> rowData : tableData) {
      rowData.set(5, 100);
    }

    // test
    String querySql = "select * from test_table1;";
    ExecuteStatementResp resp = client.executeStatement(querySql);
    TableSchema tableSchema = dataGenerator.getTableSchema("test_table1");
    Set<List<Object>> queryResult = convertData(resp.rowList, tableSchema.types);
    tableData = dataMap.get("test_table1");

    if (!equals(queryResult, tableData)) {
      System.out.println("Error!");
    }

    // update column2 to 100 where column2 = 50;
    updateSql = "update test_table2 set column2 = 100 where column2 = 50;";
    client.executeStatement(updateSql);

    tableData = dataMap.get("test_table2");
    for (List<Object> rowData : tableData) {
      if ((double) rowData.get(2) == 50) {
        rowData.set(2, 100);
      }
    }

    // test
    querySql = "select * from test_table2;";
    resp = client.executeStatement(querySql);
    tableSchema = dataGenerator.getTableSchema("test_table2");
    queryResult = convertData(resp.rowList, tableSchema.types);
    tableData = dataMap.get("test_table2");

    if (!equals(queryResult, tableData)) {
      System.out.println("Error!");
    }

    // update column3 to 100 where column2 = 200;
    updateSql = "update test_table3 set column3 = 100 where column2 = 50;";
    client.executeStatement(updateSql);

    tableData = dataMap.get("test_table3");
    for (List<Object> rowData : tableData) {
      if ((double) rowData.get(2) == 50) {
        rowData.set(3, 100);
      }
    }

    // test
    querySql = "select * from test_table3;";
    resp = client.executeStatement(querySql);
    tableSchema = dataGenerator.getTableSchema("test_table3");
    queryResult = convertData(resp.rowList, tableSchema.types);
    tableData = dataMap.get("test_table3");

    if (!equals(queryResult, tableData)) {
      System.out.println("Error!");
    }
  }

  public static void deleteAndQueryData() {
    // delete where column1=100
    String deleteSql = "delete from test_table4 where column1 = 100;";
    //        //drop table
    //        String dropSql = "drop "
  }

  private boolean check(String type, Object expectValue, String actualValue) {
    switch (type) {
      case "int":
        return expectValue == Integer.valueOf(actualValue);
      case "long":
        return expectValue == Long.valueOf(actualValue);
      case "float":
        return Objects.equals(expectValue, Float.valueOf(actualValue));
      case "double":
        return Objects.equals(expectValue, Double.valueOf(actualValue));
      default:
        // string
        return Objects.equals(expectValue, actualValue);
    }
  }

  private static Set<List<Object>> convertData(List<List<String>> data, List<DataType> type) {
    Set<List<Object>> result = new HashSet<>();
    int rowSize = data.size();
    int colSize = type.size();

    for (int rowId = 0; rowId < rowSize; rowId++) {
      List<String> stringRowData = data.get(rowId);
      List<Object> rowData = new ArrayList<>();
      for (int columnId = 0; columnId < colSize; columnId++) {
        switch (type.get(columnId)) {
          case INT:
            rowData.add(Integer.valueOf(stringRowData.get(columnId)));
            break;
          case LONG:
            rowData.add(Long.valueOf(stringRowData.get(columnId)));
            break;
          case FLOAT:
            rowData.add(Float.valueOf(stringRowData.get(columnId)));
            break;
          case DOUBLE:
            rowData.add(Double.valueOf(stringRowData.get(columnId)));
            break;
          case STRING:
            rowData.add(stringRowData.get(columnId));
            break;
        }
      }
      result.add(rowData);
    }
    return result;
  }

  public static boolean equals(Set<List<Object>> set1, Set<List<Object>> set2) {
    if (set1 == null || set2 == null) return set1 == set2;

    if (set1.size() != set2.size()) return false;

    for (List<Object> list : set1) {
      boolean found = false;

      for (List<Object> anotherList : set2) {
        if (list.size() == anotherList.size()
            && list.containsAll(anotherList)
            && anotherList.containsAll(list)) {
          found = true;
          break;
        }
      }

      if (!found) {
        return false;
      }
    }

    return true;
  }

  @Override
  public void close() {
    if (client != null) {
      client.close();
    }
  }
}
