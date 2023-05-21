package cn.edu.thssdb.schema;

import cn.edu.thssdb.common.Global;
import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.FileIOException;
import cn.edu.thssdb.parser.SQLHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// TODO: add lock control
// TODO: complete readLog() function according to writeLog() for recovering transaction

public class Manager {
  private HashMap<String, Database> databases;
  private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  public Database currentDatabase;
  public ArrayList<Long> currentSessions;
  public ArrayList<Long> waitSessions;
  public static SQLHandler sqlHandler;
  public HashMap<Long, ArrayList<String>> x_lockDict;

  public static Manager getInstance() {
    return Manager.ManagerHolder.INSTANCE;
  }

  public Manager() {
    // TODO: init possible additional variables
    databases = new HashMap<>();
    currentDatabase = null;
    sqlHandler = new SQLHandler(this);
    x_lockDict = new HashMap<>();
    this.currentSessions = new ArrayList<Long>();

    File managerFolder = new File(Global.DBMS_DIR + File.separator + "data");
    if (!managerFolder.exists()) managerFolder.mkdirs();
    this.recover();
  }

  public HashMap<String, Database> getDatabases() {
    return databases;
  }

  public void deleteDatabase(String databaseName) {
    try {
      // TODO: add lock control
      if (!databases.containsKey(databaseName)) throw new DatabaseNotExistException(databaseName);
      Database database = databases.get(databaseName);
      database.dropDatabase();
      databases.remove(databaseName);

    } finally {
      // TODO: add lock control
    }
  }

  public void switchDatabase(String databaseName) {
    try {
      // TODO: add lock control
      if (!databases.containsKey(databaseName)) throw new DatabaseNotExistException(databaseName);
      currentDatabase = databases.get(databaseName);
    } finally {
      // TODO: add lock control
    }
  }

  private static class ManagerHolder {
    private static final Manager INSTANCE = new Manager();

    private ManagerHolder() {}
  }

  public Database getCurrentDatabase() {
    return currentDatabase;
  }

  // utils:

  // Lock example: quit current manager
  public void quit() {
    try {
      lock.writeLock().lock();
      for (Database database : databases.values()) database.quit();
      persist();
      databases.clear();
    } finally {
      lock.writeLock().unlock();
    }
  }

  public Database get(String databaseName) {
    try {
      // TODO: add lock control
      if (!databases.containsKey(databaseName)) throw new DatabaseNotExistException(databaseName);
      return databases.get(databaseName);
    } finally {
      // TODO: add lock control
    }
  }

  public void createDatabaseIfNotExists(String databaseName) {
    try {
      // TODO: add lock control
      if (!databases.containsKey(databaseName))
        databases.put(databaseName, new Database(databaseName));
      if (currentDatabase == null) {
        try {
          // TODO: add lock control
          if (!databases.containsKey(databaseName))
            throw new DatabaseNotExistException(databaseName);
          currentDatabase = databases.get(databaseName);
        } finally {
          // TODO: add lock control
        }
      }
    } finally {
      // TODO: add lock control
    }
  }

  public void persist() {
    try {
      FileOutputStream fos = new FileOutputStream(Manager.getManagerDataFilePath());
      OutputStreamWriter writer = new OutputStreamWriter(fos);
      for (String databaseName : databases.keySet()) writer.write(databaseName + "\n");
      writer.close();
      fos.close();
    } catch (Exception e) {
      throw new FileIOException(Manager.getManagerDataFilePath());
    }
  }

  public void persistDatabase(String databaseName) {
    try {
      // TODO: add lock control
      Database database = databases.get(databaseName);
      database.quit();
      persist();
    } finally {
      // TODO: add lock control
    }
  }

  // Log control and recover from logs.
  public void writeLog(String statement) {
    String logFilename = this.currentDatabase.getDatabaseLogFilePath();
    try {
      FileWriter writer = new FileWriter(logFilename, true);
      writer.write(statement + "\n");
      writer.close();
    } catch (Exception e) {
      throw new FileIOException(logFilename);
    }
  }

  // TODO: read Log in transaction to recover.
  public void readLog(String databaseName) {
    switchDatabase(databaseName);
    File tableDatafile = new File(Manager.getTableDataFilePath(databaseName));
    if (!tableDatafile.isFile()) {
      return;
    }
    try {
      System.out.println("??!! try to read log");
      InputStreamReader reader = new InputStreamReader(new FileInputStream(tableDatafile));
      BufferedReader bufferedReader = new BufferedReader(reader);
      String line;
      ArrayList<String> lines = new ArrayList<>();
      ArrayList<Integer> transactionList = new ArrayList<>();

      int index = 0;
      int lastTransaction = -1;
      int lastCommit = -1;

      while ((line = bufferedReader.readLine()) != null) {
        if (line.equals(Global.LOG_BEGIN_TRANSACTION)) {
          lastTransaction = index;
          transactionList.add(index);
        } else if (line.equals(Global.LOG_COMMIT)) {
          lastCommit = index;
        }
        lines.add(line);
        index++;
      }
      int lastCmd = 0;
      if (lastTransaction <= lastCommit) {
        lastCmd = lines.size() - 1;
      } else {
        for (int i : transactionList) {
          if (i > lastCommit) {
            lastCmd = i - 1;
          }
        }
      }
      for (int i = 0; i <= lastCmd; i++) {
        sqlHandler.evaluatelog(lines.get(i), 0);
      }
      bufferedReader.close();
      reader.close();

      if (lastTransaction > lastCommit) {
        FileWriter writer = new FileWriter(Manager.getTableDataFilePath(databaseName));
        writer.write("");
        writer.close();
        FileWriter writer2 = new FileWriter(Manager.getTableDataFilePath(databaseName));
        for (int i = 0; i <= lastCmd; i++) {
          writer2.write(lines.get(i) + "\n");
        }
        writer2.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void recover() {
    File managerDataFile = new File(Manager.getManagerDataFilePath());
    if (!managerDataFile.isFile()) return;
    try {
      System.out.println("??!! try to recover manager");
      InputStreamReader reader = new InputStreamReader(new FileInputStream(managerDataFile));
      BufferedReader bufferedReader = new BufferedReader(reader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        System.out.println("??!!" + line);
        createDatabaseIfNotExists(line);
        readLog(line);
      }
      bufferedReader.close();
      reader.close();
    } catch (Exception e) {
      throw new FileIOException(managerDataFile.getName());
    }
  }

  // Get positions
  public static String getManagerDataFilePath() {
    return Global.DBMS_DIR + File.separator + "data" + File.separator + "manager";
  }

  public static String getTableDataFilePath(String databaseName) {
    return Global.DBMS_DIR
        + File.separator
        + "data"
        + File.separator
        + databaseName
        + File.separator
        + "log";
  }
}
