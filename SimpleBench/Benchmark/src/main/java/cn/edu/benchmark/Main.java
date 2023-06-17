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
package cn.edu.benchmark;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Main {
  private static Map<Class<?>, Map<String, Integer>> RC_TEST = new HashMap<>();
  private static Map<Class<?>, Map<String, Integer>> S_TEST = new HashMap<>();

  static {
    RC_TEST.put(
        CRUDTest.class,
        new HashMap<String, Integer>() {
          {
            put("crudTest(cn.edu.benchmark.CRUDTest)", 1);
          }
        });
    RC_TEST.put(
        ConcurrentTest.class,
        new HashMap<String, Integer>() {
          {
            put("concurrentTest(cn.edu.benchmark.ConcurrentTest)", 2);
          }
        });
    RC_TEST.put(
        TransactionTest.class,
        new HashMap<String, Integer>() {
          {
            put("testLostUpdate(cn.edu.benchmark.TransactionTest)", 3);
            put("testDirtyRead(cn.edu.benchmark.TransactionTest)", 4);
          }
        });
    S_TEST.put(
        CRUDTest.class,
        new HashMap<String, Integer>() {
          {
            put("crudTest(cn.edu.benchmark.CRUDTest)", 5);
          }
        });
    S_TEST.put(
        ConcurrentTest.class,
        new HashMap<String, Integer>() {
          {
            put("concurrentTest(cn.edu.benchmark.ConcurrentTest)", 6);
          }
        });
    S_TEST.put(
        TransactionTest.class,
        new HashMap<String, Integer>() {
          {
            put("testLostUpdate(cn.edu.benchmark.SerializableTest)", 7);
            put("testDirtyRead(cn.edu.benchmark.SerializableTest)", 8);
            put("testRepeatRead(cn.edu.benchmark.SerializableTest)", 9);
            put("testPhantomRead(cn.edu.benchmark.SerializableTest)", 10);
          }
        });
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.println("Please provide GroupNumber and Level as command-line arguments.");
      return;
    }
    int groupNumber;
    String level;
    try {
      groupNumber = Integer.parseInt(args[0]);
      level = args[1].toUpperCase();
    } catch (NumberFormatException e) {
      System.out.println("Invalid GroupNumber. Please provide a valid integer value.");
      return;
    }
    if (groupNumber < 1 || groupNumber > 39) {
      System.out.println("GroupNumber should be between 1 and 39.");
      return;
    }
    if (!level.equals("S") && !level.equals("RC")) {
      System.out.println("Level should be either 'S' or 'RC'.");
      return;
    }
    // Perform your test here

    List<Integer> testResult = run(level);
    System.out.println("==============================================");
    updateCsvFile(groupNumber, level, testResult);
  }

  /** @return 失败的测试用例 */
  private static List<Integer> run(String level) {
    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < 10 + 1; i++) {
      res.add(0);
    }
    Map<Class<?>, Map<String, Integer>> classMapMap = level.equals("RC") ? RC_TEST : S_TEST;
    for (Map.Entry<Class<?>, Map<String, Integer>> entry : classMapMap.entrySet()) {
      System.out.println("run test " + entry.getKey());
      CompletableFuture<Result> future =
          CompletableFuture.supplyAsync(
              () -> {
                // Run your JUnit test class
                Result result = JUnitCore.runClasses(entry.getKey());
                return result;
              });
      // set time out
      try {
        Result testResult = future.get(10, TimeUnit.SECONDS);
        // Print the test results
        for (int index : entry.getValue().values()) {
          res.set(index, 1);
        }
        if (testResult.wasSuccessful()) {
          System.out.println("All tests passed successfully.");
        } else {
          System.out.println("Test failures:");
          for (Failure failure : testResult.getFailures()) {
            System.out.println(failure.toString());
            res.set(entry.getValue().get(failure.getDescription().toString()), 0);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        // time out
        System.out.println("Time out");
      }
    }
    return res;
  }

  private static void updateCsvFile(int groupNumber, String level, List<Integer> testResult)
      throws IOException {
    File csvFile = new File("path_to_your_csv_file.csv");
    File tempFile = new File("temp.csv");
    BufferedReader reader = new BufferedReader(new FileReader(csvFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    writer.write(reader.readLine());
    writer.newLine();
    while ((line = reader.readLine()) != null) {
      String[] parts = line.split(",");
      int currentGroupNumber = Integer.parseInt(parts[0]);
      if (currentGroupNumber == groupNumber) {
        // Update the test results
        if (level.equals("RC")) {
          for (Map<String, Integer> entry : RC_TEST.values()) {
            for (int index : entry.values()) {
              parts[index] = testResult.get(index).toString();
            }
          }
        } else if (level.equals("S")) {
          for (Map<String, Integer> entry : S_TEST.values()) {
            for (int index : entry.values()) {
              parts[index] = testResult.get(index).toString();
            }
          }
        }
      }
      writer.write(String.join(",", parts));
      writer.newLine();
    }
    reader.close();
    writer.close();
    csvFile.delete();
    tempFile.renameTo(csvFile);
    System.out.println("CSV file updated successfully.");
  }
}
