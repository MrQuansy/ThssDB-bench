package cn.edu.thssdb.benchmark;

import cn.edu.thssdb.benchmark.executor.CRUDTestExecutor;
import org.apache.thrift.TException;

public class TestEntrance {

  public static void main(String args[]) throws TException {

    System.out.println("\n--- Start simple bench! ---");

    try (CRUDTestExecutor CRUDTestExecutor = new CRUDTestExecutor()) {
      // create database
      CRUDTestExecutor.createAndUseDB();
      System.out.println("Create database finished!");
      // create table
      CRUDTestExecutor.createTable();
      System.out.println("Create table finished!");
      // insert data:
      CRUDTestExecutor.insertData();
      System.out.println("Insert data finished!");
      // query data:
      CRUDTestExecutor.queryData();
      System.out.println("Query data finished!");
      // update and query data
      System.out.println("Update and re-query data finished!");
      // delete and query data
      System.out.println("Delete and re-query data finished!");
    } catch (Exception e) {
      System.out.println("Failed to init cn.edu.thssdb.benchmark.executor.CRUDTestExecutor");
    }

    System.out.println("\n--- Finish testing! ---");
  }
}
