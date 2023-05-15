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
import cn.edu.thssdb.benchmark.common.Constants;
import cn.edu.thssdb.benchmark.common.TableSchema;
import cn.edu.thssdb.benchmark.generator.BaseDataGenerator;
import cn.edu.thssdb.benchmark.generator.SimpleDataGenerator;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementResp;
import com.clearspring.analytics.stream.quantile.TDigest;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PerformanceTestExecutor extends TestExecutor {
  private static final int SUCCESS_CODE = 0;
  private static final int CONCURRENT_NUMBER = Constants.tableCount;
  private static final int WRITE_ROW_NUMBER = 10;
  private static final int COMPRESSION = 3000;
  private static final int DATA_SEED = 667;
  // 性能测试执行次数
  public static final int LOOP = 10;
  // 读写混合比例
  public static final double WRITE_RATIO = 0.5;
  private BaseDataGenerator dataGenerator;
  private Map<String, TableSchema> schemaMap;
  private List<Client> clients;
  private Random random = new Random(DATA_SEED);
  private static final Map<Operation, TDigest> operationLatencyDigest =
      new EnumMap<>(Operation.class);
  private Map<Integer, Measurement> measurements = new HashMap<>();

  public PerformanceTestExecutor() throws TException {
    dataGenerator = new SimpleDataGenerator();
    schemaMap = dataGenerator.getSchemaMap();
    clients = new ArrayList<>();
    for (Operation operation : Operation.values()) {
      operationLatencyDigest.put(operation, new TDigest(COMPRESSION, new Random(DATA_SEED)));
    }
    for (int number = 0; number < CONCURRENT_NUMBER; number++) {
      clients.add(new Client("127.0.0.1", 6667));
      measurements.put(number, new Measurement());
    }
  }

  public void test() throws TException {
    // create database and use database
    Client schemaClient = clients.get(0);
    ExecuteStatementResp resp1 = schemaClient.executeStatement("create database db_performance;");
    if (resp1.status.code == SUCCESS_CODE) {
      System.out.println("Create database db_concurrent finished");
    }
    for (int i = 0; i < CONCURRENT_NUMBER; i++) {
      Client client = clients.get(i);
      ExecuteStatementResp resp2 = client.executeStatement("use db_performance;");
      if (resp2.status.code == SUCCESS_CODE) {
        System.out.println("Client-" + i + " use db_performance finished");
      }
      createTable(schemaMap.get("test_table" + i), client);
    }
    // performance test
    List<CompletableFuture<Void>> futures = new ArrayList<>();
    for (int i = 0; i < CONCURRENT_NUMBER; i++) {
      int index = i;
      CompletableFuture<Void> completableFuture =
          CompletableFuture.runAsync(
              () -> {
                try {
                  System.out.println("Start Performance Test for Client-" + index);
                  Client client = clients.get(index);
                  Measurement measurement = measurements.get(index);
                  TableSchema tableSchema = schemaMap.get("test_table" + index);
                  for (int m = 0; m < LOOP; m++) {
                    double randomValue = random.nextDouble();
                    if (randomValue < WRITE_RATIO) {
                      doWriteOperation(measurement, m, tableSchema, client);
                    } else {
                      doQueryOperation(measurement, tableSchema, client);
                    }
                  }
                  System.out.println("Finish Performance Test for Client-" + index);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              });
      futures.add(completableFuture);
    }
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    // calculate measurement
    List<Double> quantiles = Arrays.asList(0.0, 0.25, 0.5, 0.75, 0.9, 0.99, 1.0);
    for (Operation operation : Operation.values()) {
      TDigest digest = operationLatencyDigest.get(operation);
      double totalLatency = 0.0;
      long totalNumber = 0;
      long totalPoint = 0;
      for (Double quantile : quantiles) {
        System.out.println(
            operation + "-" + quantile + ": " + (digest.quantile(quantile) / 1e6) + " ms");
      }
      for (Measurement measurement : measurements.values()) {
        totalLatency += measurement.getOperationLatencySumThisClient().get(operation);
        totalPoint += measurement.getOkPointNumMap().get(operation);
        totalNumber += measurement.getOkOperationNumMap().get(operation);
      }
      System.out.println(operation + " per second: " + (totalNumber / (totalLatency / 1e9)));
      System.out.println(operation + " points per second: " + (totalPoint / (totalLatency / 1e9)));
    }
  }

  private void doWriteOperation(
      Measurement measurement, int loop, TableSchema tableSchema, Client client) throws TException {
    String tableName = tableSchema.tableName;
    for (int rowid = 0; rowid < WRITE_ROW_NUMBER; rowid++) {
      List<String> columns = new ArrayList<>();
      List<Object> data = new ArrayList<>();
      for (int columnId = 0; columnId < tableSchema.columns.size(); columnId++) {
        Object dataItem =
            dataGenerator.generateValue(tableName, loop * WRITE_ROW_NUMBER + rowid, columnId);
        if (dataItem != null) {
          columns.add(tableSchema.columns.get(columnId));
          data.add(dataItem);
        }
      }
      String sql =
          "Insert into "
              + tableName
              + "("
              + StringUtils.join(columns, ",")
              + ")"
              + " values("
              + StringUtils.join(data, ",")
              + ");";
      long startTime = System.nanoTime();
      ExecuteStatementResp resp = client.executeStatement(sql);
      long finishTime = System.nanoTime();
      if (resp.status.code == SUCCESS_CODE) {
        measurement.record(
            Operation.WRITE, WRITE_ROW_NUMBER * tableSchema.columns.size(), finishTime - startTime);
      } else {
        System.out.println("Insert into " + tableName + " failed");
      }
    }
  }

  private void doQueryOperation(Measurement measurement, TableSchema tableSchema, Client client)
      throws TException {
    String querySql = "select * from " + tableSchema.tableName + ";";
    long startTime = System.nanoTime();
    ExecuteStatementResp resp = client.executeStatement(querySql);
    long finishTime = System.nanoTime();
    if (resp.status.code == SUCCESS_CODE) {
      measurement.record(
          Operation.QUERY,
          resp.rowList.size() * tableSchema.columns.size(),
          finishTime - startTime);
    } else {
      System.out.println("query " + tableSchema.tableName + " failed");
    }
  }

  @Override
  public void close() {
    if (clients != null) {
      for (Client client : clients) {
        client.close();
      }
    }
  }

  private class Measurement {
    private final Map<Operation, Double> operationLatencySumThisClient;
    private final Map<Operation, Long> okOperationNumMap;
    private final Map<Operation, Long> okPointNumMap;

    public Measurement() {
      operationLatencySumThisClient = new EnumMap<>(Operation.class);
      okOperationNumMap = new EnumMap<>(Operation.class);
      okPointNumMap = new EnumMap<>(Operation.class);
      for (Operation operation : Operation.values()) {
        operationLatencySumThisClient.put(operation, 0.0);
        okOperationNumMap.put(operation, 0L);
        okPointNumMap.put(operation, 0L);
      }
    }

    public void record(Operation operation, int size, long latency) {
      synchronized (operationLatencyDigest.get(operation)) {
        operationLatencyDigest.get(operation).add(latency);
      }
      operationLatencySumThisClient.put(
          operation, operationLatencySumThisClient.get(operation) + latency);
      okOperationNumMap.put(operation, okOperationNumMap.get(operation) + 1);
      okPointNumMap.put(operation, okPointNumMap.get(operation) + size);
    }

    public Map<Operation, Double> getOperationLatencySumThisClient() {
      return operationLatencySumThisClient;
    }

    public Map<Operation, Long> getOkOperationNumMap() {
      return okOperationNumMap;
    }

    public Map<Operation, Long> getOkPointNumMap() {
      return okPointNumMap;
    }
  }

  private enum Operation {
    WRITE,
    QUERY
  }
}
