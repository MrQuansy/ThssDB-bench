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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  private static List<Class<?>> RC_TEST =
      Arrays.asList(
          cn.edu.thssdb.benchmark.CRUDTest.class,
          cn.edu.thssdb.benchmark.ConcurrentTest.class,
          cn.edu.thssdb.benchmark.TransactionTest.class);
  private static List<Class<?>> S_TEST =
      Arrays.asList(
          cn.edu.thssdb.benchmark.CRUDTest.class,
          cn.edu.thssdb.benchmark.ConcurrentTest.class,
          cn.edu.thssdb.benchmark.SerializableTest.class);
  private static Map<String, Integer> RC_MAP = new HashMap<>();
  private static Map<String, Integer> S_MAP = new HashMap<>();

  static {
    RC_MAP.put("crudTest(cn.edu.thssdb.benchmark.CRUDTest)", 1);
    RC_MAP.put("concurrentTest(cn.edu.thssdb.benchmark.ConcurrentTest)", 2);
    RC_MAP.put("testLostUpdate(cn.edu.thssdb.benchmark.TransactionTest)", 3);
    RC_MAP.put("testDirtyRead(cn.edu.thssdb.benchmark.TransactionTest)", 4);
    S_MAP.put("crudTest(cn.edu.thssdb.benchmark.CRUDTest)", 5);
    S_MAP.put("concurrentTest(cn.edu.thssdb.benchmark.ConcurrentTest)", 6);
    ;
    S_MAP.put("testLostUpdate(cn.edu.thssdb.benchmark.SerializableTest)", 7);
    S_MAP.put("testDirtyRead(cn.edu.thssdb.benchmark.SerializableTest)", 8);
    S_MAP.put("testRepeatRead(cn.edu.thssdb.benchmark.SerializableTest)", 9);
    S_MAP.put("testPhantomRead(cn.edu.thssdb.benchmark.SerializableTest)", 10);
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

    List<String> failTest = run(level);
    System.out.println("==============================================");
    failTest.forEach(System.out::println);
    updateCsvFile(groupNumber, level, failTest);
  }

  /** @return 失败的测试用例 */
  private static List<String> run(String level) {
    List<String> failTest = new ArrayList<>();
    List<Class<?>> classList = level.equals("RC") ? RC_TEST : S_TEST;
    for (Class<?> clazz : classList) {
      System.out.println("run test " + clazz.getName());
      // Run your JUnit test class
      Result result = JUnitCore.runClasses(clazz);
      // Print the test results
      if (result.wasSuccessful()) {
        System.out.println("All tests passed successfully.");
      } else {
        System.out.println("Test failures:");
        for (Failure failure : result.getFailures()) {
          System.out.println(failure.toString());
          failTest.add(failure.getDescription().toString());
        }
      }
    }
    return failTest;
  }

  private static void updateCsvFile(int groupNumber, String level, List<String> failTest)
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
          for (int index : RC_MAP.values()) {
            parts[index] = "1";
          }
        } else if (level.equals("S")) {
          for (int index : S_MAP.values()) {
            parts[index] = "1";
          }
        }
        for (String fail : failTest) {
          if (level.equals("RC")) {
            parts[RC_MAP.get(fail)] = "0";
          } else if (level.equals("S")) {
            parts[S_MAP.get(fail)] = "0";
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
