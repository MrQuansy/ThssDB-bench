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
package cn.edu.thssdb.benchmark;

import cn.edu.thssdb.benchmark.executor.TransactionTestExecutor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionTest.class);

  private static TransactionTestExecutor transactionTestExecutor;

  @BeforeClass
  public static void setUp() throws Exception {
    transactionTestExecutor = new TransactionTestExecutor();
    LOGGER.info("======================== Create database  ======================== ");
    transactionTestExecutor.createAndUseDB();
    LOGGER.info("======================== Prepare adata  ======================== ");
    transactionTestExecutor.prepareData();
  }

  @AfterClass
  public static void tearDown() {
    transactionTestExecutor.close();
  }

  @Test
  public void testLostUpdate() throws Exception {
    LOGGER.info("======================== Lost update ========================");
    transactionTestExecutor.testLostUpdate();
  }

  @Test
  public void testDirtyRead() throws Exception {
    LOGGER.info("======================== Dirty read ========================");
    transactionTestExecutor.testDirtyRead();
  }
}
