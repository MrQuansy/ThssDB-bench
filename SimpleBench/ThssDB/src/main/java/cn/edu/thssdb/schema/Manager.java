package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.DuplicateDatabaseException;
import cn.edu.thssdb.exception.FileException;
import cn.edu.thssdb.parser.ErrorListener;
import cn.edu.thssdb.parser.SQLLexer;
import cn.edu.thssdb.parser.SQLParser;
import cn.edu.thssdb.parser.SimpleSQLVisitor;
import cn.edu.thssdb.query.QueryResult;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static cn.edu.thssdb.utils.Global.DATA_DIRECTORY;

public class Manager {
  private HashMap<String, Database> databases;
  private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private Database current_database;
  public HashMap<Long, ArrayList<String>> s_lock_list;
  public HashMap<Long, ArrayList<String>> x_lock_list;
  public ArrayList<Long> waiting_session;
  public ArrayList<Long> transaction_session;

  public static Manager getInstance() {
    return ManagerHolder.INSTANCE;
  }

  public Manager() {
    databases = new HashMap<>();
    current_database = null;
    s_lock_list = new HashMap<>();
    x_lock_list = new HashMap<>();
    waiting_session = new ArrayList<>();
    transaction_session = new ArrayList<>();
    try {
      recover();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public Database getCurrentDatabase() {
    return current_database;
  }

  public void createDatabaseIfNotExists(String dbname) {
    try {
      lock.writeLock().lock();
      if (!databases.containsKey(dbname)) {
        databases.put(dbname, new Database(dbname));
        persist();
      } else {
        throw new DuplicateDatabaseException(dbname);
      }
      if (current_database == null) {
        current_database = get(dbname);
      }
    } finally {
      lock.writeLock().unlock();
    }
  }

  public Database get(String dbname) {
    try {
      lock.readLock().lock();
      if (!databases.containsKey(dbname)) {
        throw new DatabaseNotExistException(dbname);
      }
      return databases.get(dbname);
    } finally {
      lock.readLock().unlock();
    }
  }

  public void deleteDatabase(String dbname) {
    try {
      lock.writeLock().lock();
      if (!databases.containsKey(dbname)) {
        throw new DatabaseNotExistException(dbname);
      }
      Database db = databases.get(dbname);
      db.dropSelf();
      // 正在使用
      if (db == current_database) current_database = null;
      // 重写文件
      databases.remove(dbname);
      // 持久化
      persist();
    } finally {
      lock.writeLock().unlock();
    }
  }

  public void switchDatabase(String dbname) {
    try {
      lock.readLock().lock();
      if (!databases.containsKey(dbname)) {
        throw new DatabaseNotExistException(dbname);
      }
      current_database = databases.get(dbname);
    } finally {
      lock.readLock().unlock();
    }
  }

  public void persist() {
    try {
      FileOutputStream file = new FileOutputStream(DATA_DIRECTORY + "meta_database.data");
      OutputStreamWriter wt = new OutputStreamWriter(file);
      for (String dbName : databases.keySet()) {
        wt.write(dbName + "\n");
        databases.get(dbName).persist();
      }
      wt.close();
      file.close();
    } catch (Exception e) {
      throw new FileException();
    }
  }

  private void recover() {
    File file = new File(DATA_DIRECTORY + "meta_database.data");
    if (!file.isFile()) {
      return;
    }
    try {
      InputStreamReader rd = new InputStreamReader(new FileInputStream(file));
      BufferedReader buff = new BufferedReader(rd);
      String line = null;
      while ((line = buff.readLine()) != null) {
        createDatabaseIfNotExists(line);
        read_log(line);
      }
      buff.close();
      rd.close();
    } catch (Exception e) {
      throw new FileException();
    }
  }

  private static class ManagerHolder {
    private static final Manager INSTANCE = new Manager();

    private ManagerHolder() {}
  }

  public void put_x_lock(Table tb, Long sessionId) {
    if (transaction_session.contains(sessionId)) {
      while (true) {
        int lock_flag = -1;
        if (!waiting_session.contains(sessionId)) {
          lock_flag = tb.put_x_lock(sessionId);
          switch (lock_flag) {
            case 1:
              x_lock_list.get(sessionId).add(tb.tableName);
              break;
            case 0:
              break;
            default:
              waiting_session.add(sessionId);
          }
        } else if (waiting_session.get(0) == sessionId) {
          lock_flag = tb.put_x_lock(sessionId);
          if (lock_flag == 1) {
            x_lock_list.get(sessionId).add(tb.tableName);
            waiting_session.remove(0);
          } else if (lock_flag == 0) {
            waiting_session.remove(0);
          }
        }
        if (lock_flag != -1) break;
        try {
          Thread.sleep(400);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }
  }

  public void put_s_lock(ArrayList<Table> tables, Long sessionId) {
    if (transaction_session.contains(sessionId)) {
      ArrayList<Table> tmp = new ArrayList<>();
      while (true) {
        int lock_flag = -1;
        if (!waiting_session.contains(sessionId)) {
          tmp.clear();
          for (Table tb : tables) {
            lock_flag = tb.put_s_lock(sessionId);
            if (lock_flag == -1) {
              for (Table tmp_tb : tmp) {
                tmp_tb.free_lock(sessionId, 1);
              }
              waiting_session.add(sessionId);
              tmp.clear();
              break;
            } else if (lock_flag == 1) {
              tmp.add(tb);
            }
          }
          if (tmp.size() > 0 || lock_flag != -1) break;
        } else if (waiting_session.get(0) == sessionId) {
          tmp.clear();
          for (Table tb : tables) {
            lock_flag = tb.put_s_lock(sessionId);
            if (lock_flag == -1) {
              for (Table tmp_tb : tmp) {
                tmp_tb.free_lock(sessionId, 1);
              }
              tmp.clear();
              break;
            } else if (lock_flag == 1) {
              tmp.add(tb);
            }
          }
          if (tmp.size() > 0 || lock_flag != -1) {
            waiting_session.remove(0);
            break;
          }
        }
        try {
          Thread.sleep(400);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      if (tmp.size() > 0) {
        for (Table tb : tmp) {
          s_lock_list.get(sessionId).add(tb.tableName);
        }
      }
    }
  }

  public ArrayList<QueryResult> handle(String s) {
    ArrayList<QueryResult> results = new ArrayList<>();
    ErrorListener errorListener = new ErrorListener();
    try {
      // 词法分析
      SQLLexer lexer = new SQLLexer(CharStreams.fromString(s));
      lexer.removeErrorListeners();
      lexer.addErrorListener(errorListener); // 添加错误提示
      CommonTokenStream tokens = new CommonTokenStream(lexer); // 转成token流
      // 句法分析
      SQLParser parser = new SQLParser(tokens);
      parser.removeErrorListeners();
      parser.addErrorListener(errorListener);
      SimpleSQLVisitor simpleSQLVisitor = new SimpleSQLVisitor(this, -999);

      results.addAll(simpleSQLVisitor.visitParse(parser.parse()));
      System.out.println("out");
      return results;
    } catch (Exception e) {
      System.out.println("parser error");
      QueryResult queryResult = new QueryResult(e.getMessage(), false);
      results.add(queryResult);
      return results;
    }
  }

  public void write_log(String statement) {
    String path = DATA_DIRECTORY + current_database.get_name() + ".log";
    try {
      File file = new File(path);
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter writer = new FileWriter(path, true);
      writer.write(statement + "\n");
      writer.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void read_log(String database_name) {
    String path = DATA_DIRECTORY + database_name + ".log";
    File file = new File(path);
    if (file.exists() && file.isFile()) {
      handle("use " + database_name);
      try {
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        ArrayList<String> statements = new ArrayList<>();
        int last_trans = -1;
        int index = 0;
        while ((str = bufferedReader.readLine()) != null) {
          if (str.equals("begin transaction")) {
            last_trans = index;
          } else if (str.equals("commit")) {
            last_trans = -1;
          }
          statements.add(str);
          ++index;
          System.out.println(str);
        }
        if (last_trans == -1) {
          last_trans = statements.size();
        }
        for (int i = 0; i < last_trans; ++i) {
          handle(statements.get(i));
        }
        inputStream.close();
        bufferedReader.close();
        if (last_trans != -1) {
          FileWriter writer = new FileWriter(path);
          writer.write("");
          writer.close();
          writer = new FileWriter(path, true);
          for (int i = 0; i < last_trans; ++i) {
            writer.write(statements.get(i) + "\n");
          }
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
