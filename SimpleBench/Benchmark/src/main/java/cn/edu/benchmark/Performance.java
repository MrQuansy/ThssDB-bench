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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Performance {

  private static Map<Integer, Integer> MEMORY_INDEX = new HashMap<>();
  private static Map<Integer, Integer> MEMORY_TIME = new HashMap<>();
  private static int DEFAULT_MAX = 999999;

  static {
    MEMORY_INDEX.put(50, 11);
    MEMORY_INDEX.put(100, 12);
    MEMORY_INDEX.put(500, 13);
    MEMORY_TIME.put(50, 5);
    MEMORY_TIME.put(100, 5);
    MEMORY_TIME.put(500, 10);
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.println("Please provide GroupNumber and Level as command-line arguments.");
      return;
    }
    int groupNumber;
    int memory;
    try {
      groupNumber = Integer.parseInt(args[0]);
      memory = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.out.println("Invalid GroupNumber. Please provide a valid integer value.");
      return;
    }
    // Perform your test here

    long testResult = run(memory);
    System.out.println("==============================================");
    System.out.println("Test time cost: " + testResult);
    updateCsvFile(groupNumber, memory, testResult);
  }

  /** @return 运行时间 */
  private static long run(int memory) {
    CompletableFuture<Result> future =
        CompletableFuture.supplyAsync(
            () -> {
              // Run your JUnit test class
              return JUnitCore.runClasses(PerformanceTest.class);
            });
    // set time out
    try {
      Result testResult = future.get(MEMORY_TIME.get(memory), TimeUnit.MINUTES);
      if (testResult.wasSuccessful()) {
        System.out.println("All tests passed successfully.");
        return testResult.getRunTime();
      } else {
        System.out.println("Test failures:");
        return DEFAULT_MAX;
      }
    } catch (Exception e) {
      e.printStackTrace();
      // time out
      System.out.println("Time out");
    }
    return DEFAULT_MAX;
  }

  private static void updateCsvFile(int groupNumber, int memory, long testResult)
      throws IOException {
    //    String workDir = "/opt/test/";
    String workDir = "/Users/chenyanze/Downloads/test/";
    File csvFile = new File(workDir, "path_to_your_csv_file.csv");
    File tempFile = new File(workDir, "temp.csv");
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
        // int i
        parts[MEMORY_INDEX.get(memory)] = String.valueOf(testResult / 1000.0);
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
