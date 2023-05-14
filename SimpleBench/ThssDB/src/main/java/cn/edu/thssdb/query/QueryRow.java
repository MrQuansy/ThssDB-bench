package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.type.ColumnType;
import cn.edu.thssdb.type.ExpressionType;

import java.util.ArrayList;

public class QueryRow extends Row {
  private ArrayList<Table> tables;

  public QueryRow(ArrayList<Row> rows, ArrayList<Table> tables) {
    this.entries = new ArrayList<>();
    for (Row row : rows) {
      this.entries.addAll(row.getEntries());
    }
    this.tables = new ArrayList<>();
    this.tables.addAll(tables);
  }

  /**
   * 将ColumnType转换成CompareType
   *
   * @param type
   * @return
   */
  public ExpressionType getCompareType(ColumnType type) {
    switch (type) {
      case INT:
      case LONG:
      case FLOAT:
      case DOUBLE:
        return ExpressionType.NUMBER;
      case STRING:
        return ExpressionType.STRING;
    }
    return ExpressionType.NULL;
  }

  /**
   * 根据列名获取Expression
   *
   * @param full_name
   * @return
   */
  public Expression getExpressionFromColumn(String full_name) {
    int target_index = 0;
    ColumnType target_type = ColumnType.INT;
    int equalNum = 0;
    int total = 0;
    // 纯ColumnName
    if (!full_name.contains(".")) {
      for (Table table : tables) {
        for (int i = 0; i < table.columns.size(); ++i) {
          Column column = table.columns.get(i);
          if (full_name.equals(column.getName())) {
            ++equalNum;
            target_index = total + i;
            target_type = column.getType();
          }
        }
        total += table.columns.size();
      }
    }
    // TableName.ColumnName
    else {
      String[] splited = MetaInfo.splitFullName(full_name);
      String tableName = splited[0], columnName = splited[1];
      for (Table table : tables) {
        if (tableName.equals(table.tableName)) {
          for (int i = 0; i < table.columns.size(); ++i) {
            if (columnName.equals(table.columns.get(i).getName())) {
              ++equalNum;
              target_index = total + i;
              target_type = table.columns.get(i).getType();
            }
          }
          break;
        }
        total += table.columns.size();
      }
    }
    if (equalNum > 1) {
      // TODO: 抛出异常
    }
    if (equalNum < 1) {
      // TODO: 抛出异常
    }
    ExpressionType expressionType = getCompareType(target_type);
    Comparable expressionValue = this.entries.get(target_index).value;

    if (expressionValue == null) {
      return new Expression(ExpressionType.NULL, null);
    }
    Expression expression = new Expression(expressionType, String.valueOf(expressionValue));
    return expression;
  }
}
