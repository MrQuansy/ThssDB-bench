package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.DuplicateTableException;
import cn.edu.thssdb.exception.FileException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.query.QueryTable;
import cn.edu.thssdb.type.ColumnType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static cn.edu.thssdb.utils.Global.DATA_DIRECTORY;

public class Database {

  private String name;
  private HashMap<String, Table> tables;
  ReentrantReadWriteLock lock;

  public Database(String name) {
    this.name = name;
    this.tables = new HashMap<>();
    this.lock = new ReentrantReadWriteLock();
    recover();
  }

  public String get_name() {
    return name;
  }

  public String[] getAllTableNames() {
    return tables.keySet().toArray(new String[0]);
  }

  public void persist() {
    for (Table tb : tables.values()) {
      ArrayList<Column> columns = tb.columns;
      try {
        FileOutputStream file =
            new FileOutputStream(DATA_DIRECTORY + "meta_" + name + "_" + tb.tableName + ".data");
        OutputStreamWriter wt = new OutputStreamWriter(file);
        for (Column column : columns) {
          wt.write(column.toString() + "\n");
        }
        wt.close();
        file.close();
      } catch (Exception e) {
        throw new FileException();
      }
      tb.persist();
    }
  }

  public void create(String name, Column[] columns) {
    try {
      lock.writeLock().lock();
      if (tables.containsKey(name)) {
        throw new DuplicateTableException(name);
      }

      Table table_new = new Table(this.name, name, columns);
      tables.put(name, table_new);
      persist();
    } finally {
      lock.writeLock().unlock();
    }
  }

  public void drop(String tbname) {
    try {
      lock.writeLock().lock();
      if (!tables.containsKey(tbname)) {
        throw new TableNotExistException(tbname);
      }
      tables.get(tbname).deleteAll();
      File metaFile = new File(DATA_DIRECTORY + "meta_" + name + "_" + tbname + ".data");
      if (metaFile.isFile()) metaFile.delete();
      File dataFile = new File(DATA_DIRECTORY + name + "_" + tbname);
      if (dataFile.isFile()) dataFile.delete();
      tables.remove(tbname);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public String select(QueryTable[] queryTables) {
    return null;
  }

  public void dropSelf() {
    try {
      lock.writeLock().lock();
      for (Table table : tables.values()) {
        File metaFile = new File(DATA_DIRECTORY + "meta_" + name + "_" + table.tableName + ".data");
        if (metaFile.isFile()) metaFile.delete();
        table.deleteAll();
      }
    } finally {
      lock.writeLock().unlock();
    }
    tables.clear();
    tables = null;
  }

  private void recover() {
    try {
      File dir = new File(DATA_DIRECTORY);
      File[] all_files = dir.listFiles();
      if (all_files == null) return;
      for (File f : all_files) {
        if (f.isFile()) {
          try {
            // 表格的元数据信息
            String[] filename = f.getName().split("\\.")[0].split("_");
            if (filename.length == 3
                && filename[0].equals("meta")
                && filename[1].equals(this.name)) {
              String tbname = filename[2];
              // 建表
              ArrayList<Column> columns = new ArrayList<>();
              InputStreamReader rd = new InputStreamReader(new FileInputStream(f));
              BufferedReader buff = new BufferedReader(rd);
              String line;
              while ((line = buff.readLine()) != null) {
                String[] col = line.split(",");
                columns.add(
                    new Column(
                        col[0],
                        ColumnType.valueOf(col[1]),
                        Integer.parseInt(col[2]),
                        Boolean.parseBoolean(col[3]),
                        Integer.parseInt(col[4])));
              }
              Table tb = new Table(this.name, tbname, columns.toArray(new Column[0]));
              tables.put(tbname, tb);
              buff.close();
              rd.close();
            }
          } catch (Exception e) {
            throw new FileException();
          }
        }
      }
    } catch (Exception e) {
      throw new FileException();
    }
  }

  public Table get(String tbname) {
    if (tables.containsKey(tbname)) {
      return tables.get(tbname);
    }
    return null;
  }

  public void quit() {
    // TODO
  }
}
