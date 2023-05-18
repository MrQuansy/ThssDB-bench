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
package cn.edu.thssdb.benchmark.common;

import cn.edu.thssdb.benchmark.generator.BaseDataGenerator;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TableReadWriteUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TableReadWriteUtil.class);

  public static void insertData(
      TableSchema tableSchema, Client client, BaseDataGenerator dataGenerator, int rowNum)
      throws TException {
    String tableName = tableSchema.tableName;
    for (int rowid = 0; rowid < rowNum; rowid++) {
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

  public static void queryAndCheckData(
      TableSchema tableSchema, Client client, BaseDataGenerator dataGenerator, int rowNum)
      throws TException {
    String querySql = "select * from " + tableSchema.tableName + ";";
    ExecuteStatementResp resp = client.executeStatement(querySql);
    LOGGER.info(querySql);
    Assert.assertEquals(Constants.SUCCESS_STATUS_CODE, resp.status.code);
    // check row size
    Assert.assertEquals(rowNum, resp.rowList.size());
    // check column
    List<Integer> resultColumnToSchemaColumnIndex = new ArrayList<>();
    Assert.assertEquals(tableSchema.columns.size(), resp.getColumnsList().size());
    for (int i = 0; i < tableSchema.columns.size(); i++) {
      String[] columnSplitByDot = tableSchema.columns.get(i).split("\\.");
      String column = columnSplitByDot[columnSplitByDot.length - 1];
      if (!tableSchema.columns.contains(column)) {
        Assert.fail(String.format("Column [%s] not in schema!%n", resp.getColumnsList().get(i)));
      }
      resultColumnToSchemaColumnIndex.add(tableSchema.columns.indexOf(column));
    }
    // check result set
    for (int i = 0; i < resp.rowList.size(); i++) {
      for (int j = 0; j < tableSchema.columns.size(); j++) {
        Object dataItem = dataGenerator.generateValue(tableSchema.tableName, i, j);
        if (dataItem == null) {
          Assert.assertEquals("null", resp.rowList.get(i).get(j));
        } else {
          Assert.assertEquals(dataItem.toString(), resp.rowList.get(i).get(j));
        }
      }
    }
  }
}
