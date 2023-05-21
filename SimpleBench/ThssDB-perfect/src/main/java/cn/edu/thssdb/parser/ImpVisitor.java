package cn.edu.thssdb.parser;

// TODO: add logic for some important cases, refer to given implementations and SQLBaseVisitor.java
// for structures

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.parser.SQLParser.*;
import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.query.QueryTable;
import cn.edu.thssdb.schema.*;
import cn.edu.thssdb.type.ColumnType;

import java.math.BigDecimal;
import java.util.*;

/**
 * When use SQL sentence, e.g., "SELECT avg(A) FROM TableX;" the parser will generate a grammar tree
 * according to the rules defined in SQL.g4. The corresponding terms, e.g., "select_stmt" is a root
 * of the parser tree, given the rules "select_stmt : K_SELECT ( K_DISTINCT | K_ALL )? result_column
 * ( ',' result_column )* K_FROM table_query ( ',' table_query )* ( K_WHERE multiple_condition )? ;"
 *
 * <p>This class "ImpVisit" is used to convert a tree rooted at e.g. "select_stmt" into the
 * collection of tuples inside the database.
 *
 * <p>We give you a few examples to convert the tree, including create/drop/quit. You need to finish
 * the codes for parsing the other rooted trees marked TODO.
 */
public class ImpVisitor extends SQLBaseVisitor<Object> {
  private Manager manager;
  private long session;

  public ImpVisitor(Manager manager, long session) {
    super();
    this.manager = manager;
    this.session = session;
  }

  private Database GetCurrentDB() {
    Database currentDB = manager.getCurrentDatabase();
    if (currentDB == null) {
      throw new DatabaseNotExistException();
    }
    return currentDB;
  }

  public QueryResult visitSql_stmt(SQLParser.Sql_stmtContext ctx) {
    if (ctx.create_db_stmt() != null)
      return new QueryResult(visitCreate_db_stmt(ctx.create_db_stmt()));
    if (ctx.drop_db_stmt() != null) return new QueryResult(visitDrop_db_stmt(ctx.drop_db_stmt()));
    if (ctx.use_db_stmt() != null) return new QueryResult(visitUse_db_stmt(ctx.use_db_stmt()));
    if (ctx.create_table_stmt() != null)
      return new QueryResult(visitCreate_table_stmt(ctx.create_table_stmt()));
    if (ctx.drop_table_stmt() != null)
      return new QueryResult(visitDrop_table_stmt(ctx.drop_table_stmt()));
    if (ctx.insert_stmt() != null) return new QueryResult(visitInsert_stmt(ctx.insert_stmt()));
    if (ctx.delete_stmt() != null) return new QueryResult(visitDelete_stmt(ctx.delete_stmt()));
    if (ctx.update_stmt() != null) return new QueryResult(visitUpdate_stmt(ctx.update_stmt()));
    if (ctx.select_stmt() != null) return visitSelect_stmt(ctx.select_stmt());
    if (ctx.quit_stmt() != null) return new QueryResult(visitQuit_stmt(ctx.quit_stmt()));
    if (ctx.show_meta_stmt() != null)
      return new QueryResult(visitShow_meta_stmt(ctx.show_meta_stmt()));
    if (ctx.show_db_stmt() != null) return new QueryResult(visitShow_db_stmt(ctx.show_db_stmt()));
    if (ctx.show_table_stmt() != null)
      return new QueryResult(visitShow_table_stmt(ctx.show_table_stmt()));
    return null;
  }

  /** 创建数据库 */
  @Override
  public String visitCreate_db_stmt(SQLParser.Create_db_stmtContext ctx) {
    try {
      manager.createDatabaseIfNotExists(ctx.database_name().getText().toLowerCase());
      manager.persist();
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Create database " + ctx.database_name().getText() + ".";
  }

  /** 删除数据库 */
  @Override
  public String visitDrop_db_stmt(SQLParser.Drop_db_stmtContext ctx) {
    try {
      manager.deleteDatabase(ctx.database_name().getText().toLowerCase());
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Drop database " + ctx.database_name().getText() + ".";
  }

  /** 显示表格结构 */
  @Override
  public String visitShow_meta_stmt(SQLParser.Show_meta_stmtContext ctx) {
    try {
      String table_name = ctx.table_name().getText().toLowerCase();
      Table table = GetCurrentDB().get(table_name);
      if (table == null) {
        return String.format("Table %s not exists.", table_name);
      }
      String meta = "";
      ArrayList<Column> columns = table.columns;
      for (Column column : columns) {
        String name = column.getColumnName();
        String type = column.getColumnType().toString();
        String primary = column.getPrimary() + "";
        String notNull = column.cantBeNull() + "";
        String maxLength = column.getMaxLength() + "";
        String colStructure =
            String.format("(%s,%s,%s,%s,%s)", name, type, primary, notNull, maxLength);
        meta += colStructure;
        meta += "\n";
      }
      return meta;
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  /** 显示数据库 */
  @Override
  public String visitShow_db_stmt(SQLParser.Show_db_stmtContext ctx) {
    try {
      HashMap<String, Database> databases = manager.getDatabases();
      Set<String> db_names = databases.keySet();
      String db = "";
      for (String name : db_names) {
        db += name;
        db += "\n";
      }
      return db;
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  /** 显示表格 */
  @Override
  public String visitShow_table_stmt(SQLParser.Show_table_stmtContext ctx) {
    try {
      String database_name = ctx.database_name().getText();
      Database database = manager.get(database_name);
      HashMap<String, Table> tableMap = database.getTableMap();
      Set<String> table_names = tableMap.keySet();
      String table = "";
      for (String name : table_names) {
        table += name;
        table += "\n";
      }
      return table;
    } catch (Exception e) {
      return e.getMessage();
    }
  }
  /** 切换数据库 */
  @Override
  public String visitUse_db_stmt(SQLParser.Use_db_stmtContext ctx) {
    try {
      manager.switchDatabase(ctx.database_name().getText().toLowerCase());
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Switch to database " + ctx.database_name().getText() + ".";
  }

  /** 删除表格 */
  @Override
  public String visitDrop_table_stmt(SQLParser.Drop_table_stmtContext ctx) {
    try {
      GetCurrentDB().drop(ctx.table_name().getText().toLowerCase());
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Drop table " + ctx.table_name().getText() + ".";
  }

  /** TODO 创建表格 */
  @Override
  public String visitCreate_table_stmt(SQLParser.Create_table_stmtContext ctx) {

    try {
      List<SQLParser.Column_defContext> columns_def = ctx.column_def();
      Column[] columns = new Column[columns_def.size()];
      for (int i = 0; i < columns_def.size(); i++) {
        SQLParser.Column_defContext column_def = columns_def.get(i);

        Column column =
            new Column(column_def.column_name().getText().toLowerCase(), null, 0, false, -1);

        String type_name = column_def.type_name().getText().toUpperCase();
        if (type_name.split("\\(")[0].equals("STRING")) {

          int max_len = Integer.parseInt(type_name.substring(7, type_name.length() - 1));
          type_name = "STRING";

          column.setMaxLength(max_len);
        }
        column.setColumnType(ColumnType.valueOf(type_name));

        for (SQLParser.Column_constraintContext column_constraintContext :
            column_def.column_constraint()) {

          String constraint = column_constraintContext.getText().toUpperCase();
          if (constraint.equals("PRIMARYKEY")) {
            column.setPrimary(1);
            column.setNotNull(true);
          }
          if (constraint.equals("NOTNULL")) {
            column.setNotNull(true);
          }
        }
        columns[i] = column;
      }

      if (ctx.table_constraint() != null) {

        for (SQLParser.Column_nameContext column_nameContext :
            ctx.table_constraint().column_name()) {

          String column_name = column_nameContext.getText().toLowerCase();
          for (Column column : columns) {
            if (column.getColumnName().equals(column_name)) {
              column.setPrimary(1);
              column.setNotNull(true);
            }
          }
        }
      }
      GetCurrentDB().create(ctx.table_name().getText().toLowerCase(), columns);

    } catch (Exception e) {
      //            return "error";
      return e.getMessage();
    }
    return "create table " + ctx.table_name().getText().toLowerCase() + '.';
  }

  /** TODO 表格项插入 */
  @Override
  public String visitInsert_stmt(SQLParser.Insert_stmtContext ctx) {
    Table table = null;
    try {
      String table_name = ctx.table_name().getText().toLowerCase();
      table = GetCurrentDB().get(table_name);

      if (table == null) {
        return String.format("Table %s not exists.", table_name);
      }
      while (table.takeXLock(session) == -1) {
        try {
          Thread.sleep(300);
        } catch (Exception e) {

        }
      }
      ArrayList<String> table_list = manager.x_lockDict.get(session);
      table_list.add(table_name);
      manager.x_lockDict.put(session, table_list);
      List<Value_entryContext> value_entryContexts = ctx.value_entry();
      List<String> values = new ArrayList<>();
      for (Value_entryContext value_entryContext : value_entryContexts) {
        if (value_entryContext.literal_value() == null) {
          return "Invalid value format.";
        }

        for (SQLParser.Literal_valueContext literal_valueContext :
            value_entryContext.literal_value()) {

          String value_str = null;
          if (literal_valueContext.NUMERIC_LITERAL() != null) {
            value_str = literal_valueContext.NUMERIC_LITERAL().getText();
          } else if (literal_valueContext.STRING_LITERAL() != null) {
            value_str = literal_valueContext.STRING_LITERAL().getText();
            if (value_str.toCharArray()[0] == '\''
                && value_str.toCharArray()[value_str.length() - 1] == '\'') {
              value_str = value_str.substring(1, value_str.length() - 1);
            } else {
              return "Invalid value format.";
            }
          }
          values.add(value_str);
        }
      }

      if (ctx.column_name() == null || ctx.column_name().size() == 0) {
        if (values.size() != table.columns.size()) {
          return "Column count doesn't match value count.";
        }
        ArrayList<Cell> entries = new ArrayList<>();
        for (int i = 0; i < table.columns.size(); i++) {
          Cell cell = null;
          switch (table.columns.get(i).getColumnType()) {
            case INT:
              cell = new Cell(new Integer(values.get(i)));
              break;
            case LONG:
              cell = new Cell(new Long(values.get(i)));
              break;
            case FLOAT:
              cell = new Cell(new Float(values.get(i)));
              break;
            case DOUBLE:
              cell = new Cell(new Double(values.get(i)));
              break;
            case STRING:
              int max_len = table.columns.get(i).getMaxLength();
              cell =
                  new Cell(values.get(i).substring(0, Math.min(max_len, values.get(i).length())));
              break;
            default:
              break;
          }
          entries.add(cell);
        }
        Row row = new Row(entries);
        table.insert(row);
      } else {

        List<SQLParser.Column_nameContext> columns = ctx.column_name();

        if (values.size() != columns.size()) {
          return "Column count doesn't match value count.";
        }
        int[] index = new int[table.columns.size()];
        Arrays.fill(index, -1);
        for (int i = 0; i < columns.size(); i++) {
          String column_name = columns.get(i).getText().toLowerCase();
          for (int j = 0; j < table.columns.size(); j++) {
            if (table.columns.get(j).getColumnName().equals(column_name)) {
              if (index[j] != -1) {
                return "Duplicate column specified.";
              }
              index[j] = i;
              break;
            }
          }
        }

        ArrayList<Cell> entries = new ArrayList<>();
        for (int i = 0; i < table.columns.size(); i++) {
          Column column = table.columns.get(i);
          Cell cell = null;
          if (index[i] == -1) {
            if (column.isPrimary()) {
              return "Primary key should not be null.";
            }
            if (!column.canBeNull()) {
              return String.format("Column %s should not be null", column.getColumnName());
            }
            cell = new Cell(null);
          } else {
            switch (column.getColumnType()) {
              case INT:
                cell = new Cell(new Integer(values.get(index[i])));
                break;
              case LONG:
                cell = new Cell(new Long(values.get(index[i])));
                break;
              case FLOAT:
                cell = new Cell(new Float(values.get(index[i])));
                break;
              case DOUBLE:
                cell = new Cell(new Double(values.get(index[i])));
                break;
              case STRING:
                cell =
                    new Cell(
                        values
                            .get(index[i])
                            .substring(
                                0, Math.min(column.getMaxLength(), values.get(index[i]).length())));
                break;
              default:
                break;
            }
          }
          entries.add(cell);
        }
        Row row = new Row(entries);
        table.insert(row);
      }
    } catch (Exception e) {
      table.releaseXLock(session);
      return e.getMessage();
    }
    return "Insert into " + ctx.table_name().getText().toLowerCase() + '.';
  }

  /** TODO 表格项删除 */
  @Override
  public String visitDelete_stmt(SQLParser.Delete_stmtContext ctx) {
    String table_name = ctx.table_name().getText().toLowerCase();
    Table table = GetCurrentDB().get(table_name);
    try {
      while (table.takeXLock(session) == -1) {
        try {
          Thread.sleep(300);
        } catch (Exception e) {

        }
      }
      ArrayList<String> table_list = manager.x_lockDict.get(session);
      table_list.add(table_name);
      manager.x_lockDict.put(session, table_list);

      Multiple_conditionContext multiple_conditionContext = ctx.multiple_condition();
      if (table == null) {
        return String.format("Table %s not exists.", table_name);
      }
      Iterator<Row> it = table.iterator();

      int delete_num = 0;
      if (multiple_conditionContext == null) {
        // 删除所有数据
        while (it.hasNext()) {
          table.delete(it.next());
          //                    it.remove();
          delete_num++;
        }
      } else {
        if (multiple_conditionContext.AND() == null && multiple_conditionContext.OR() == null) {
          ConditionContext conditionContext = multiple_conditionContext.condition();
          String attr_name = conditionContext.expression().get(0).getText().toLowerCase();
          ExpressionContext e = conditionContext.expression().get(1);
          Comparable value = null;
          ColumnType attr_type = null;
          int index = -1;
          for (int i = 0; i < table.columns.size(); i++) {
            Column col = table.columns.get(i);
            if (col.getColumnName().equals(attr_name)) {
              attr_type = col.getColumnType();
              index = i;
              break;
            }
          }
          switch (attr_type) {
            case STRING:
              String s = e.getText();
              if (s.toCharArray()[0] == '\'' && s.toCharArray()[s.length() - 1] == '\'') {
                value = s.substring(1, s.length() - 1);
              } else {
                return "Invalid value format.";
              }
              break;
            default: // int long float double
              value = Compute(e);
              break;
          }
          ArrayList<Integer> check = new ArrayList<>();
          ComparatorContext comparator = conditionContext.comparator();
          if (comparator.EQ() != null) {
            check.add(0);
          } else if (comparator.GE() != null) {
            check.add(0);
            check.add(1);
          } else if (comparator.GT() != null) {
            check.add(1);
          } else if (comparator.LE() != null) {
            check.add(0);
            check.add(-1);
          } else if (comparator.LT() != null) {
            check.add(-1);
          } else if (comparator.NE() != null) {
            check.add(-1);
            check.add(1);
          }

          while (it.hasNext()) {
            Row row = it.next();
            Cell cell = row.getEntries().get(index);
            if (cell.value == null) {
              continue;
            }
            String value_type = cell.getValueType();
            int compare;
            if (value_type.equals("STRING")) {
              compare = cell.value.toString().compareTo(value.toString());
            } else {
              compare =
                  new BigDecimal(cell.value.toString()).compareTo(new BigDecimal(value.toString()));
            }
            if (check.contains(compare)) {
              table.delete(row);
              //                            it.remove();
              delete_num++;
            }
          }
        }
      }
      return String.format(
          "Delete %s tuple from %s.", delete_num, ctx.table_name().getText().toLowerCase());
    } catch (Exception e) {
      table.releaseXLock(session);
      return e.getMessage();
    }
  }

  /** TODO 表格项更新 */
  @Override
  public String visitUpdate_stmt(SQLParser.Update_stmtContext ctx) {
    Table table = null;
    try {
      String table_name = ctx.table_name().getText().toLowerCase();
      Multiple_conditionContext multiple_conditionContext = ctx.multiple_condition();
      table = GetCurrentDB().get(table_name);
      while (table.takeXLock(session) == -1) {
        try {
          Thread.sleep(300);
        } catch (Exception e) {

        }
      }
      ArrayList<String> table_list = manager.x_lockDict.get(session);
      table_list.add(table_name);
      manager.x_lockDict.put(session, table_list);
      if (table == null) {
        return String.format("Table %s not exists.", table_name);
      }
      Iterator<Row> it = table.iterator();

      int update_num = 0;
      if (multiple_conditionContext.AND() == null && multiple_conditionContext.OR() == null) {
        ConditionContext conditionContext = multiple_conditionContext.condition();
        String attr_name = conditionContext.expression().get(0).getText().toLowerCase();
        ExpressionContext e = conditionContext.expression().get(1);
        Comparable value = null;
        ColumnType attr_type = null;
        int index = -1;

        String update_attr_name = ctx.column_name().getText().toLowerCase();
        ExpressionContext update_e = ctx.expression();
        ColumnType update_attr_type = null;
        Comparable update_value = null;
        int update_index = -1;

        int primary_index = -1;
        for (int i = 0; i < table.columns.size(); i++) {
          Column col = table.columns.get(i);
          if (col.getColumnName().equals(attr_name)) {
            attr_type = col.getColumnType();
            index = i;
          }
          if (col.getColumnName().equals(update_attr_name)) {
            update_attr_type = col.getColumnType();
            update_index = i;
          }
          if (col.isPrimary()) {
            primary_index = i;
          }
        }
        if (update_index == -1 || index == -1) {
          return "Update " + table_name + ".";
        }

        switch (attr_type) {
          case STRING:
            String s = e.getText();
            if (s.toCharArray()[0] == '\'' && s.toCharArray()[s.length() - 1] == '\'') {
              value = s.substring(1, s.length() - 1);
            } else {
              return "Invalid value format.";
            }
            break;
          default: // int long float double
            value = Compute(e);
            break;
        }
        switch (update_attr_type) {
          case STRING:
            String s = update_e.getText();
            if (s.toCharArray()[0] == '\'' && s.toCharArray()[s.length() - 1] == '\'') {
              update_value = s.substring(1, s.length() - 1);
            } else {
              return "Invalid value format.";
            }
            break;
          case INT:
            update_value = Compute(update_e).intValue();
            break;
          case LONG:
            update_value = Compute(update_e).longValue();
            break;
          case FLOAT:
            update_value = Compute(update_e).floatValue();
            break;
          case DOUBLE:
            update_value = Compute(update_e).doubleValue();
            break;
          default:
            break;
        }

        ArrayList<Integer> check = new ArrayList<>();
        ComparatorContext comparator = conditionContext.comparator();
        if (comparator.EQ() != null) {
          check.add(0);
        } else if (comparator.GE() != null) {
          check.add(0);
          check.add(1);
        } else if (comparator.GT() != null) {
          check.add(1);
        } else if (comparator.LE() != null) {
          check.add(0);
          check.add(-1);
        } else if (comparator.LT() != null) {
          check.add(-1);
        } else if (comparator.NE() != null) {
          check.add(-1);
          check.add(1);
        }

        while (it.hasNext()) {
          Row row = it.next();
          ArrayList<Cell> cells = row.getEntries();
          Cell cell = cells.get(index);
          if (cell.value == null) {
            continue;
          }
          String value_type = cell.getValueType();
          int compare = -2;
          if (value_type.equals("STRING")) {
            compare = cell.value.toString().compareTo(value.toString());
          } else {
            compare =
                new BigDecimal(cell.value.toString()).compareTo(new BigDecimal(value.toString()));
          }
          if (check.contains(compare)) {
            Cell old_cell = cells.get(primary_index);
            ArrayList<Cell> new_cells = new ArrayList<>(cells);
            new_cells.set(update_index, new Cell(update_value));
            table.update(old_cell, new Row(new_cells));
            update_num++;
          }
        }
      }
      return String.format(
          "Update %s tuple from %s.", update_num, ctx.table_name().getText().toLowerCase());
    } catch (Exception e) {
      table.releaseXLock(session);
      return e.getMessage();
    }
  }

  /** TODO 表格项查询 */
  @Override
  public QueryResult visitSelect_stmt(SQLParser.Select_stmtContext ctx) {
    List<SQLParser.Table_queryContext> tables = ctx.table_query();
    QueryTable Qtable = new QueryTable();
    //        return new QueryResult("no such column");
    Boolean flag;
    // join all the tables
    for (SQLParser.Table_queryContext table_query : tables) {
      QueryTable tmp = new QueryTable();
      flag = (table_query.K_ON() != null);
      if (flag) {
        Table tmp_table_0 = this.manager.currentDatabase.get(table_query.table_name(0).getText());
        Table tmp_table_1 = this.manager.currentDatabase.get(table_query.table_name(1).getText());
        while (tmp_table_1.takeSLock(session) == -1 && tmp_table_0.takeSLock(session) == -1) {
          try {
            Thread.sleep(300);
          } catch (Exception e) {

          }
        }

        tmp = new QueryTable(tmp_table_0, tmp_table_1, table_query.multiple_condition());
        tmp_table_0.releaseSLock(session);
        tmp_table_1.releaseSLock(session);

      } else {
        Table tmp_table = this.manager.currentDatabase.get(table_query.table_name(0).getText());
        while (tmp_table.takeSLock(session) == -1) {
          try {
            Thread.sleep(300);
          } catch (Exception e) {

          }
        }
        tmp = new QueryTable(tmp_table);
        tmp_table.releaseSLock(session);
      }
      if (Qtable.size == 0) {
        Qtable = tmp;
      } else {
        Qtable = Qtable.qappend(tmp);
      }
    }
    // filter for where condition
    if (ctx.multiple_condition() != null) {
      Qtable = Qtable.filter(ctx.multiple_condition());
    }
    // select columns
    List<SQLParser.Result_columnContext> result_column = ctx.result_column();
    Qtable = Qtable.select(result_column);
    if (Qtable == null) {
      return new QueryResult("No such Column,please check your SQL");
    }
    // distinct
    if (ctx.K_DISTINCT() != null) {
      Qtable = Qtable.distinct();
    }
    QueryTable a[] = {Qtable};
    return new QueryResult(a);
  }

  /** 退出 */
  @Override
  public String visitQuit_stmt(SQLParser.Quit_stmtContext ctx) {
    try {
      manager.quit();
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Quit.";
  }

  @Override
  public Object visitParse(SQLParser.ParseContext ctx) {
    return visitSql_stmt_list(ctx.sql_stmt_list());
  }

  public Object visitSql_stmt_list(SQLParser.Sql_stmt_listContext ctx) {
    ArrayList<QueryResult> ret = new ArrayList<>();
    for (SQLParser.Sql_stmtContext subctx : ctx.sql_stmt()) {
      ret.add(visitSql_stmt(subctx));
    }
    return ret;
  }

  private BigDecimal Compute(ExpressionContext e) {
    if (e == null) {
      return null;
    }
    if (e.comparer() != null) {
      return new BigDecimal(e.comparer().getText().toLowerCase());
    }
    BigDecimal v1 = Compute(e.expression(0));
    BigDecimal v2 = Compute(e.expression(1));
    if (e.ADD() != null) {
      v1 = v1.add(v2);
    } else if (e.DIV() != null && v2.compareTo(BigDecimal.ZERO) != 0) {
      v1 = v1.divide(v2);
    } else if (e.MUL() != null) {
      v1 = v1.multiply(v2);
    } else if (e.SUB() != null) {
      v1 = v1.subtract(v2);
    }
    return v1;
  }
}
