/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package cn.edu.thssdb.benchmark.executor;

import cn.edu.thssdb.benchmark.common.Client;
import cn.edu.thssdb.benchmark.common.TableSchema;
import cn.edu.thssdb.benchmark.generator.BaseDataGenerator;
import cn.edu.thssdb.benchmark.generator.ConcurrentDataGenerator;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ConcurrentTestExecutor extends TestExecutor {

  private BaseDataGenerator dataGenerator;
  private Map<String, TableSchema> schemaMap;
  private static int successStatusCode = 0;
  private Client client1;
  private Client client2;

  public ConcurrentTestExecutor() throws TException {
    dataGenerator = new ConcurrentDataGenerator();
    schemaMap = dataGenerator.getSchemaMap();
    client1 = new Client("127.0.0.1", 6667);
    client2 = new Client("127.0.0.1", 6667);
  }

  public void createAndUseDB() throws TException {
    ExecuteStatementResp resp = client1.executeStatement("create database db_concurrent;");
    if (resp.status.code == successStatusCode) {
      System.out.println("Create database db_concurrent finished");
    }
    ExecuteStatementResp resp1 = client1.executeStatement("use db_concurrent;");
    ExecuteStatementResp resp2 = client2.executeStatement("use db_concurrent;");
    if (resp1.status.code == successStatusCode && resp2.status.code == successStatusCode) {
      System.out.println("Use db_concurrent finished");
    }
  }

  public void concurrentCreateTable() {
    CompletableFuture.allOf(
            CompletableFuture.runAsync(
                () -> {
                  try {
                    System.out.println("Create concurrent_table_1 started");
                    createTable(schemaMap.get("concurrent_table_1"), client1);
                    System.out.println("Create concurrent_table_1 finished");
                  } catch (TException e) {
                    e.printStackTrace();
                  }
                }),
            CompletableFuture.runAsync(
                () -> {
                  try {
                    System.out.println("Create concurrent_table_2 started");
                    createTable(schemaMap.get("concurrent_table_2"), client2);
                    System.out.println("Create concurrent_table_2 finished");
                  } catch (TException e) {
                    e.printStackTrace();
                  }
                }))
        .join();
  }

  public void concurrentInsertAndQuery() {
    CompletableFuture.allOf(
            CompletableFuture.runAsync(
                () -> {
                  try {
                    System.out.println("Insert and query concurrent_table_1 started");
                    insertData(schemaMap.get("concurrent_table_1"), client1);
                    queryAndCheckData(schemaMap.get("concurrent_table_1"), client1);
                    System.out.println("Insert and query concurrent_table_1 finished");
                  } catch (TException e) {
                    e.printStackTrace();
                  }
                }),
            CompletableFuture.runAsync(
                () -> {
                  try {
                    System.out.println("Insert and query concurrent_table_2 started");
                    insertData(schemaMap.get("concurrent_table_2"), client2);
                    queryAndCheckData(schemaMap.get("concurrent_table_2"), client2);
                    System.out.println("Insert and query concurrent_table_2 finished");
                  } catch (TException e) {
                    e.printStackTrace();
                  }
                }))
        .join();
  }

  private void insertData(TableSchema tableSchema, Client client) throws TException {
    String tableName = tableSchema.tableName;
    for (int rowid = 0; rowid < 1000; rowid++) {
      List<String> columns = new ArrayList<>();
      List<Object> datas = new ArrayList<>();
      for (int columnId = 0; columnId < tableSchema.columns.size(); columnId++) {
        Object dataItem = dataGenerator.generateValue(tableName, rowid, columnId);
        if (dataItem != null) {
          columns.add(tableSchema.columns.get(columnId));
          datas.add(dataItem);
        }
      }
      String sql =
          "Insert into "
              + tableName
              + "("
              + StringUtils.join(columns, ",")
              + ")"
              + " values("
              + StringUtils.join(datas, ",")
              + ");";
      client.executeStatement(sql);
    }
  }

  private void queryAndCheckData(TableSchema tableSchema, Client client) throws TException {
    String querySql = "select * from " + tableSchema.tableName + ";";
    ExecuteStatementResp resp = client.executeStatement(querySql);
    if (resp.status.code == successStatusCode) {
      System.out.println("Query all rows finished");
      return;
    }
    // check row size
    if (resp.rowList.size() != 1000) {
      System.out.println("Row size not equal!");
      return;
    }
    // check column
    List<Integer> resultColumnToSchemaColumnIndex = new ArrayList<>();
    if (resp.getColumnsList().size() != tableSchema.columns.size()) {
      System.out.println("Column not equal!");
    } else {
      for (int i = 0; i < tableSchema.columns.size(); i++) {
        if (!tableSchema.columns.contains(resp.getColumnsList().get(i))) {
          System.out.printf("Column [%s] not in schema!%n", resp.getColumnsList().get(i));
          return;
        }
        resultColumnToSchemaColumnIndex.add(
            tableSchema.columns.indexOf(resp.getColumnsList().get(i)));
      }
    }
    // check result set
    for (int i = 0; i < resp.rowList.size(); i++) {
      for (int j = 0; j < tableSchema.columns.size(); j++) {
        Object dataItem = dataGenerator.generateValue(tableSchema.tableName, i, j);
        boolean equal = false;
        if (dataItem == null) {
          equal = resp.rowList.get(i).get(j).equals("null");
        } else {
          equal = resp.rowList.get(i).get(j).equals(dataItem.toString());
        }
        if (!equal) {
          System.out.println("Result set not equal!");
          return;
        }
      }
    }
  }

  @Override
  public void close() {
    client1.close();
    client2.close();
  }
}
