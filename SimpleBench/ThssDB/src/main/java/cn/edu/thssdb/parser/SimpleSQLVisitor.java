package cn.edu.thssdb.parser;

import cn.edu.thssdb.exception.EmptyColumnTypeException;
import cn.edu.thssdb.exception.NotIntException;
import cn.edu.thssdb.exception.TableNotExistException;
import cn.edu.thssdb.exception.WrongColumnConstraintException;
import cn.edu.thssdb.query.*;
import cn.edu.thssdb.schema.*;
import cn.edu.thssdb.type.*;
import cn.edu.thssdb.utils.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.edu.thssdb.type.ColumnType.*;
import static cn.edu.thssdb.utils.Global.DATA_DIRECTORY;

public class SimpleSQLVisitor extends SQLBaseVisitor {
  private Manager manager;
  private long sessionId;

  public SimpleSQLVisitor(Manager manager, long sessionId) {
    super();
    this.manager = manager;
    this.sessionId = sessionId;
  }

  public ArrayList<QueryResult> visitParse(SQLParser.ParseContext ctx) {
    return visitSql_stmt_list(ctx.sql_stmt_list());
  }

  public ArrayList<QueryResult> visitSql_stmt_list(SQLParser.Sql_stmt_listContext ctx) {
    ArrayList<QueryResult> results = new ArrayList<QueryResult>();
    QueryResult queryResult;
    for (SQLParser.Sql_stmtContext sql_stmtContext : ctx.sql_stmt()) {
      queryResult = visitSql_stmt(sql_stmtContext);
      //            queryResult.setSql(sql_stmtContext.getText().toLowerCase());
      results.add(queryResult);
    }
    return results;
  }

  public QueryResult visitSql_stmt(SQLParser.Sql_stmtContext ctx) {
    if (ctx.begin_transaction_stmt() != null) {
      return visitBegin_transaction_stmt();
    } else if (ctx.commit_stmt() != null) {
      return visitCommit_stmt();
    } else if (ctx.create_table_stmt() != null) {
      return visitCreate_table_stmt(ctx.create_table_stmt());
    } else if (ctx.create_db_stmt() != null) {
      return visitCreate_db_stmt(ctx.create_db_stmt());
    } else if (ctx.drop_db_stmt() != null) {
      return visitDrop_db_stmt(ctx.drop_db_stmt());
    } else if (ctx.delete_stmt() != null) {
      return visitDelete_stmt(ctx.delete_stmt());
    } else if (ctx.drop_table_stmt() != null) {
      return visitDrop_table_stmt(ctx.drop_table_stmt());
    } else if (ctx.select_stmt() != null) {
      return visitSelect_stmt(ctx.select_stmt());
    } else if (ctx.use_db_stmt() != null) {
      return visitUse_db_stmt(ctx.use_db_stmt());
    } else if (ctx.show_table_stmt() != null) {
      return visitShow_table_stmt(ctx.show_table_stmt());
    } else if (ctx.show_meta_stmt() != null) {
      return visitShow_meta_stmt(ctx.show_meta_stmt());
    } else if (ctx.quit_stmt() != null) {
      return visitQuit_stmt(ctx.quit_stmt());
    } else if (ctx.update_stmt() != null) {
      return visitUpdate_stmt(ctx.update_stmt());
    } else if (ctx.insert_stmt() != null) {
      return visitInsert_stmt(ctx.insert_stmt());
    }
    return new QueryResult("Wrong SQL.", false);
  }

  public QueryResult visitBegin_transaction_stmt() {
    try {
      if (!manager.transaction_session.contains(sessionId)) {
        manager.transaction_session.add(sessionId);
        manager.s_lock_list.put(sessionId, new ArrayList<>());
        manager.x_lock_list.put(sessionId, new ArrayList<>());
      } else {
        return new QueryResult("Already in a transaction.", true);
      }
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
    return new QueryResult("Begin transaction.", true);
  }

  public QueryResult visitCommit_stmt() {
    try {
      if (manager.transaction_session.contains(sessionId)) {
        // 删除事务
        manager.transaction_session.remove(sessionId);
        // 删除事务相关的锁
        Database db = manager.getCurrentDatabase();
        ArrayList<String> write_table = manager.x_lock_list.get(sessionId);
        for (String tbname : write_table) {
          db.get(tbname).free_lock(sessionId, 2);
        }
        write_table.clear();
        manager.x_lock_list.put(sessionId, write_table);
        // 存log
        String path = DATA_DIRECTORY + db.get_name() + ".log";
        File file = new File(path);
        if (file.exists() && file.isFile() && file.length() > 5000) {
          db.persist();
          try {
            FileWriter writer = new FileWriter(path);
            writer.write("");
            writer.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } else {
        return new QueryResult("Not in transaction.", true);
      }
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
    return new QueryResult("Commit transaction.", true);
  }

  public QueryResult visitCreate_db_stmt(SQLParser.Create_db_stmtContext ctx) {
    String databaseName = ctx.database_name().getText().toLowerCase();
    try {
      manager.createDatabaseIfNotExists(databaseName);
      return new QueryResult("Create database OK.", true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitDrop_db_stmt(SQLParser.Drop_db_stmtContext ctx) {
    String databaseName = ctx.database_name().getText().toLowerCase();
    try {
      manager.deleteDatabase(databaseName);
      return new QueryResult("Drop database OK.", true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitCreate_table_stmt(SQLParser.Create_table_stmtContext ctx) {
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      return new QueryResult("No database selected.", false);
    }

    String tableName = ctx.table_name().getText().toLowerCase();
    List<SQLParser.Column_defContext> columnDefList = ctx.column_def();
    int number = columnDefList.size();
    Column[] columns = new Column[number];
    int i = 0;
    try {
      for (SQLParser.Column_defContext columnDefContext : columnDefList) {
        columns[i] = visitColumn_def(columnDefContext);
        i++;
      }

      if (ctx.table_constraint() != null) {
        SQLParser.Table_constraintContext constraintContext = ctx.table_constraint();
        List<String> primaryKeys = visitTable_constraint(constraintContext);
        for (String primaryKey : primaryKeys) {
          boolean wrongPrimaryKey = true;
          for (Column column : columns) {
            if (primaryKey.equalsIgnoreCase(column.getName())) {
              wrongPrimaryKey = false;
              column.setPrimary(1);
              break;
            }
          }
          if (wrongPrimaryKey) {
            return new QueryResult("Wrong primary key " + primaryKey + '.', false);
          }
        }
      }
      database.create(tableName, columns);
      return new QueryResult("Create OK.", true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitShow_meta_stmt(SQLParser.Show_meta_stmtContext ctx) {
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      return new QueryResult("No database selected.", false);
    }
    String tableName = ctx.table_name().getText().toLowerCase();
    try {
      Table table = database.get(tableName);
      if (table == null) {
        return new QueryResult("No such table in current database.", false);
      }
      Column[] metaData = table.getMetaData();
      StringBuilder result = new StringBuilder();
      for (Column column : metaData) {
        result.append(column.toString());
        result.append('\n');
      }
      return new QueryResult(result.toString(), true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitUse_db_stmt(SQLParser.Use_db_stmtContext ctx) {
    String databaseName = ctx.database_name().getText().toLowerCase();
    try {
      manager.switchDatabase(databaseName);
      return new QueryResult("Switch to database " + databaseName + ".", true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitDelete_stmt(SQLParser.Delete_stmtContext ctx) {
    String tableName = ctx.table_name().getText().toLowerCase();
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      return new QueryResult("No database selected.", false);
    }
    Table table = database.get(tableName);
    if (table == null) {
      return new QueryResult("No such table in current database.", false);
    }
    manager.put_x_lock(table, sessionId);
    if (ctx.multiple_condition() == null) { // 删除所有数据，保留表
      try {
        table.deleteAll();
        return new QueryResult("Delete OK.", true);
      } catch (Exception e) {
        return new QueryResult(e.getMessage(), false);
      }
    } else {
      Logic logic = visitMultiple_condition(ctx.multiple_condition()); // 获得逻辑表达式
      try {
        int number = table.delete(logic);
        return new QueryResult("Delete " + number + " rows OK.", true);
      } catch (Exception e) {
        return new QueryResult(e.getMessage(), false);
      }
    }
  }

  public QueryResult visitDrop_table_stmt(SQLParser.Drop_table_stmtContext ctx) {
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      return new QueryResult("No database selected.", false);
    }
    String tableName = ctx.table_name().getText().toLowerCase();
    try {
      database.drop(tableName);
      return new QueryResult("Drop table OK.", true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitQuit_stmt(SQLParser.Quit_stmtContext ctx) {
    try {
      manager.persist();
      return new QueryResult("Quit.", true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitShow_table_stmt(SQLParser.Show_table_stmtContext ctx) {
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      return new QueryResult("No database selected.", false);
    }
    try {
      String[] tableNames = database.getAllTableNames();
      StringBuilder result = new StringBuilder();
      for (String tableName : tableNames) {
        result.append(tableName);
        result.append('\n');
      }
      return new QueryResult(result.toString(), true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitInsert_stmt(SQLParser.Insert_stmtContext ctx) {
    System.out.println("insert");
    String tableName = ctx.table_name().getText().toLowerCase();
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      System.out.println("No database selected.");
      return new QueryResult("No database selected.", false);
    }

    Table table = database.get(tableName);
    if (table == null) {
      System.out.println("No such table in current database.");
      return new QueryResult("No such table in current database.", false);
    }
    // 获取锁
    manager.put_x_lock(table, sessionId);
    ArrayList<Column> selectedColumns;
    Column[] columns = table.getMetaData();
    List<SQLParser.Column_nameContext> columnNameContexts = ctx.column_name();
    if (columnNameContexts != null && columnNameContexts.size() > 0) {
      int len = columns.length;
      selectedColumns = new ArrayList<>();
      ArrayList<Integer> selectedIndex = new ArrayList<>();
      int i;
      for (SQLParser.Column_nameContext columnNameContext : columnNameContexts) {
        boolean has = false;
        boolean duplicate = false;
        String columnName = columnNameContext.getText().toLowerCase();
        for (i = 0; i < len; i++) {
          if (columns[i].getName().equals(columnName)) {
            has = true;
            if (selectedIndex.contains(i)) {
              duplicate = true;
            } else {
              selectedIndex.add(i);
            }
            break;
          }
        }
        if (duplicate) { // 重复
          System.out.println("Exception: insert key " + columnName + " caused duplicated keys!");
          return new QueryResult(
              "Exception: insert key " + columnName + " caused duplicated keys!", false);
        }
        if (!has) {
          System.out.println("Exception: unknown key " + columnName + " in this table!");
          return new QueryResult("Exception: unknown key " + columnName + " in this table!", false);
        }
        selectedColumns.add(columns[i]);
      }

    } else {
      selectedColumns = new ArrayList<>();
      int n = columns.length;
      for (int i = 0; i < n; i++) {
        selectedColumns.add(columns[i]);
      }
    }
    try {
      //            System.out.println("selectedColumns: " + selectedColumns.toString());
      List<SQLParser.Value_entryContext> valueEntryContexts = ctx.value_entry();
      for (SQLParser.Value_entryContext valueEntryContext : valueEntryContexts) {
        ArrayList<Entry> valueEntries = visitValue_entry(valueEntryContext);

        table.insert(selectedColumns, valueEntries);
      }
      //            System.out.println("Insert OK.");
      return new QueryResult("Insert OK.", true);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new QueryResult(e.getMessage(), false);
    }
  }

  public ArrayList<Entry> visitValue_entry(SQLParser.Value_entryContext ctx) {
    List<SQLParser.Literal_valueContext> literalValueContexts = ctx.literal_value();
    ArrayList<Entry> valueEntries = new ArrayList<Entry>();
    try {
      int i = 0;
      for (SQLParser.Literal_valueContext literalValueContext : literalValueContexts) {
        ExpressionType type = visitLiteral_value(literalValueContext);
        if (type == ExpressionType.STRING) {
          int len = literalValueContext.getText().length();
          valueEntries.add(new Entry(literalValueContext.getText().substring(1, len - 1)));
        } else if (type == ExpressionType.NUMBER) {
          valueEntries.add(new Entry(literalValueContext.getText()));
        } else {
          valueEntries.add(new Entry(null));
        }
        i++;
      }
      return valueEntries;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("visitValue_entry");
      return null;
    }
  }

  public QueryResult visitSelect_stmt(SQLParser.Select_stmtContext ctx) {
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      return new QueryResult("No database selected.", false);
    }
    boolean distinct = false;
    if (ctx.K_DISTINCT() != null) {
      distinct = true;
    }
    // all 和默认情况一致，不用处理

    ArrayList<String> selectedColumnList = new ArrayList<>();
    String[] selectedColumns;
    List<SQLParser.Result_columnContext> resultColumnContexts = ctx.result_column();
    if (resultColumnContexts.get(0).getText().equals("*")) {
      selectedColumns = null;
    } else {
      for (SQLParser.Result_columnContext resultColumnContext : resultColumnContexts) {
        selectedColumnList.add(resultColumnContext.getText().toLowerCase());
      }
      selectedColumns = selectedColumnList.toArray(new String[0]);
    }
    // TODO 多个Table_query的情况
    ExtendedQueryTable queryTable = visitTable_query(ctx.table_query(0));
    ArrayList<Table> tables = queryTable.getTables();
    if (tables != null) {
      manager.put_s_lock(tables, sessionId);
    }

    Logic logic = null;
    if (ctx.multiple_condition() != null) {
      logic = visitMultiple_condition(ctx.multiple_condition());
    }
    queryTable.setLogicSelect(logic);
    try {
      QueryResult queryResult = new QueryResult(queryTable, selectedColumns, distinct);
      queryResult.generateQueryRecord();
      ArrayList<String> read_table = manager.s_lock_list.get(sessionId);
      for (String tbname : read_table) {
        database.get(tbname).free_lock(sessionId, 1);
      }
      read_table.clear();
      manager.s_lock_list.put(sessionId, read_table);
      return queryResult;
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public QueryResult visitUpdate_stmt(SQLParser.Update_stmtContext ctx) {
    String tableName = ctx.table_name().getText().toLowerCase();
    Database database = manager.getCurrentDatabase();
    if (database == null) {
      return new QueryResult("No database selected.", false);
    }

    Table table = database.get(tableName);
    if (table == null) {
      return new QueryResult("No such table in current database.", false);
    }
    manager.put_x_lock(table, sessionId);
    try {
      String columnName = ctx.column_name().getText().toLowerCase();
      Expression expression = visitExpression(ctx.expression());
      Entry value;
      if (expression.getType() == ExpressionType.NULL) {
        value = new Entry(null);
      } else if (expression.getType() == ExpressionType.STRING) {
        value = new Entry(expression.getValue());
      } else {
        value = new Entry(ctx.expression().getText());
      }
      Logic logic;
      if (ctx.K_WHERE() != null) {
        logic = visitMultiple_condition(ctx.multiple_condition());
      } else {
        logic = new Logic(null);
      }
      //            table.update(columnName, expression, logic);
      table.update(columnName, value, logic);
      return new QueryResult("Update OK.", true);
    } catch (Exception e) {
      return new QueryResult(e.getMessage(), false);
    }
  }

  public Column visitColumn_def(SQLParser.Column_defContext ctx) {
    String columnName = ctx.column_name().getText().toLowerCase();
    Pair<ColumnType, Integer> columnTypePair = visitType_name(ctx.type_name());
    if (columnTypePair == null) {
      throw new EmptyColumnTypeException(columnName);
    }
    Column column;
    if (columnTypePair.left == ColumnType.STRING) {
      column = new Column(columnName, columnTypePair.left, 0, false, columnTypePair.right);
    } else {
      column = new Column(columnName, columnTypePair.left, 0, false);
    }

    List<SQLParser.Column_constraintContext> columnConstraintContexts = ctx.column_constraint();
    if (columnConstraintContexts != null && columnConstraintContexts.size() != 0) { // 有限制
      for (SQLParser.Column_constraintContext columnConstraintContext : columnConstraintContexts) {
        ColumnConstraintType columnConstraintType = visitColumn_constraint(columnConstraintContext);
        if (columnConstraintType == ColumnConstraintType.NOTNULL) {
          column.setNotNull(true);
        } else if (columnConstraintType == ColumnConstraintType.PRIMARYKEY) {
          column.setPrimary(1);
        } else {
          throw new WrongColumnConstraintException(columnConstraintContext.getText());
        }
      }
    }

    return column;
  }

  public Pair<ColumnType, Integer> visitType_name(SQLParser.Type_nameContext ctx) {
    if (ctx.T_INT() != null) {
      return new Pair<ColumnType, Integer>(ColumnType.INT, 0);
    }
    if (ctx.T_LONG() != null) {
      return new Pair<ColumnType, Integer>(ColumnType.LONG, 0);
    }
    if (ctx.T_FLOAT() != null) {
      return new Pair<ColumnType, Integer>(ColumnType.FLOAT, 0);
    }
    if (ctx.T_DOUBLE() != null) {
      return new Pair<ColumnType, Integer>(ColumnType.DOUBLE, 0);
    }
    if (ctx.T_STRING() != null) {
      try {
        int number = Integer.parseInt(ctx.NUMERIC_LITERAL().getText());
        return new Pair<ColumnType, Integer>(ColumnType.STRING, number);
      } catch (Exception e) {
        throw new NotIntException(ctx.NUMERIC_LITERAL().getText());
      }
    }
    return null;
  }

  public ColumnConstraintType visitColumn_constraint(SQLParser.Column_constraintContext ctx) {
    if (ctx.K_PRIMARY() != null) {
      return ColumnConstraintType.PRIMARYKEY;
    }
    if (ctx.K_NOT() != null && ctx.K_NULL() != null) {
      return ColumnConstraintType.NOTNULL;
    }
    return null;
  }

  public Logic visitMultiple_condition(SQLParser.Multiple_conditionContext ctx) {
    if (ctx.condition() != null) {
      return new Logic(visitCondition(ctx.condition()));
    }
    if (ctx.AND() != null) {
      return new Logic(
          visitMultiple_condition(ctx.multiple_condition(0)),
          visitMultiple_condition(ctx.multiple_condition(1)),
          LogicType.AND);
    }
    if (ctx.OR() != null) {
      return new Logic(
          visitMultiple_condition(ctx.multiple_condition(0)),
          visitMultiple_condition(ctx.multiple_condition(1)),
          LogicType.OR);
    }
    return null;
  }

  public Condition visitCondition(SQLParser.ConditionContext ctx) {
    Expression expressionLeft = visitExpression(ctx.expression(0));
    Expression expressionRight = visitExpression(ctx.expression(1));
    ConditionType conditionType = visitComparator(ctx.comparator());
    return new Condition(expressionLeft, expressionRight, conditionType);
  }

  public Expression visitComparer(SQLParser.ComparerContext ctx) {
    if (ctx.column_full_name() != null) {
      return new Expression(ExpressionType.COLUMN, ctx.column_full_name().getText().toLowerCase());
    }
    if (ctx.literal_value() != null) {
      ExpressionType type = visitLiteral_value(ctx.literal_value());
      if (type == ExpressionType.STRING) {
        int len = ctx.literal_value().getText().length();
        return new Expression(type, ctx.literal_value().getText().substring(1, len - 1));
      } else if (type == ExpressionType.NUMBER) {
        return new Expression(type, ctx.literal_value().getText());
      } else return new Expression(type, null);
    }
    return null;
  }

  public ConditionType visitComparator(SQLParser.ComparatorContext ctx) {
    if (ctx.EQ() != null) {
      return ConditionType.EQ;
    }
    if (ctx.NE() != null) {
      return ConditionType.NE;
    }
    if (ctx.LE() != null) {
      return ConditionType.LE;
    }
    if (ctx.GE() != null) {
      return ConditionType.GE;
    }
    if (ctx.LT() != null) {
      return ConditionType.LT;
    }
    if (ctx.GT() != null) {
      return ConditionType.GT;
    }
    return null;
  }

  public Expression visitExpression(SQLParser.ExpressionContext ctx) {
    if (ctx.comparer() != null) {
      return visitComparer(ctx.comparer());
    }
    // TODO 缺乏对表达式计算的处理
    return null;
  }

  public ArrayList<String> visitTable_constraint(SQLParser.Table_constraintContext ctx) {
    List<SQLParser.Column_nameContext> columnNameContexts = ctx.column_name();
    ArrayList<String> columns = new ArrayList<>();
    for (SQLParser.Column_nameContext columnNameContext : columnNameContexts) {
      columns.add(columnNameContext.getText().toLowerCase());
    }
    return columns;
  }

  /*    public QueryResult visitResult_column(SQLParser.Result_columnContext ctx) {
          if(ctx.table_name() != null){           // table_name '.' '*'

          }
      }
  */
  public ExtendedQueryTable visitTable_query(SQLParser.Table_queryContext ctx) {
    Database database = manager.getCurrentDatabase();
    if (ctx.K_JOIN().size() > 0) { // 多表
      Logic logic = visitMultiple_condition(ctx.multiple_condition());
      ArrayList<Table> tables = new ArrayList<>();
      List<SQLParser.Table_nameContext> tableNameContexts = ctx.table_name();
      try {
        String tableName;
        for (SQLParser.Table_nameContext tableNameContext : tableNameContexts) {
          tableName = tableNameContext.getText().toLowerCase();
          tables.add(database.get(tableName));
        }
        return new ExtendedQueryTable(tables, logic);
      } catch (Exception e) {
        // TODO 抛出异常
      }
    } else { // 单表
      String tableName = ctx.table_name(0).getText().toLowerCase();

      ArrayList<Table> tables = new ArrayList<>();
      Table table = database.get(tableName);
      if (table == null) {
        throw new TableNotExistException(tableName);
      }
      tables.add(table);
      try {
        return new ExtendedQueryTable(tables, null);
      } catch (Exception e) {
        // TODO 抛出异常
      }
    }
    return null;
  }

  public ExpressionType visitLiteral_value(SQLParser.Literal_valueContext ctx) {
    if (ctx.STRING_LITERAL() != null) {
      return ExpressionType.STRING;
    }
    if (ctx.NUMERIC_LITERAL() != null) {
      return ExpressionType.NUMBER;
    }
    if (ctx.K_NULL() != null) {
      return ExpressionType.NULL;
    }
    return null;
  }
}
