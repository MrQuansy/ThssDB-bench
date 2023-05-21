package cn.edu.thssdb.query;

import cn.edu.thssdb.common.Pair;
import cn.edu.thssdb.parser.SQLParser;
import cn.edu.thssdb.schema.Cell;
import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.type.ColumnType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Designed for the select query with join/filtering... hasNext() looks up whether the select result
 * contains a next row next() returns a row, plz keep an iterator.
 */
public class QueryTable implements Iterator<Row> {

  public ArrayList<Row> Rows = new ArrayList<Row>();
  public ArrayList<Pair<String, ColumnType>> columns = new ArrayList<Pair<String, ColumnType>>();

  public int pos = 0;
  public int size = 0;

  public QueryTable() {
    return;
  }

  public QueryTable(Table table_1, Table table_2, SQLParser.Multiple_conditionContext condition) {
    // TODO
    String table_1_name = table_1.tableName;
    String table_2_name = table_2.tableName;
    for (Column column : table_1.columns) {
      String column_name = table_1_name + '.' + column.getColumnName();
      ColumnType columnType = column.getColumnType();
      Pair<String, ColumnType> pair = new Pair<String, ColumnType>(column_name, columnType);
      this.columns.add(pair);
    }
    for (Column column : table_2.columns) {
      String column_name = table_2_name + '.' + column.getColumnName();
      ColumnType columnType = column.getColumnType();
      Pair<String, ColumnType> pair = new Pair<String, ColumnType>(column_name, columnType);
      this.columns.add(pair);
    }
    Iterator<Row> t_iter_1 = table_1.iterator();
    while (t_iter_1.hasNext()) {
      Row row_1 = t_iter_1.next();
      Iterator<Row> t_iter_2 = table_2.iterator();
      while (t_iter_2.hasNext()) {
        Row row_2 = t_iter_2.next();
        Row tmp = new Row(row_1);
        tmp.appendEntries(row_2.getEntries());
        if (this.verify(tmp, condition)) {
          this.Rows.add(tmp);
          this.size++;
        }
      }
    }
  }

  public QueryTable(Table table) {
    String table_name = table.tableName;
    for (Column column : table.columns) {
      String column_name = table_name + '.' + column.getColumnName();
      ColumnType columnType = column.getColumnType();
      Pair<String, ColumnType> pair = new Pair<String, ColumnType>(column_name, columnType);
      this.columns.add(pair);
    }
    Iterator<Row> t_iter = table.iterator();
    while (t_iter.hasNext()) {
      Rows.add(t_iter.next());
      size++;
    }
  }

  public QueryTable distinct() {
    QueryTable qtable = new QueryTable();
    for (Pair<String, ColumnType> col : this.columns) {
      qtable.columns.add(col);
    }
    for (Row row : this.Rows) {
      boolean is_unique = true;
      for (Row q_row : qtable.Rows) {
        if (row.toString().equals(q_row.toString())) {
          is_unique = false;
          break;
        }
      }
      if (!is_unique) {
        continue;
      } else {
        qtable.Rows.add(row);
        qtable.size++;
      }
    }
    return qtable;
  }

  public boolean verify(Row row, SQLParser.Multiple_conditionContext condition) {
    if (condition.condition() == null) {
      if (condition.AND() != null) {
        return verify(row, condition.multiple_condition(0))
            && verify(row, condition.multiple_condition(1));
      } else if (condition.OR() != null) {
        return verify(row, condition.multiple_condition(0))
            || verify(row, condition.multiple_condition(1));
      }
    } else {
      SQLParser.ExpressionContext expression_0 = condition.condition().expression(0);
      SQLParser.ExpressionContext expression_1 = condition.condition().expression(1);
      String compare_0_str = "";
      String compare_1_str = "";
      double compare_0_d = 0;
      double compare_1_d = 0;
      Boolean isString = false;
      if (expression_0.comparer() != null && expression_1.comparer() != null) {
        String column_name_0 = "";
        String column_name_1 = "";
        if (expression_0.comparer().column_full_name() != null) {
          column_name_0 = expression_0.comparer().column_full_name().getText();
          int col_index_0 = this.find_column(column_name_0);
          if (col_index_0 >= 0) {
            if (row.getEntries().get(col_index_0).value == null) {
              return false;
            }
            if (columns.get(col_index_0).right == ColumnType.STRING) {
              compare_0_str = row.getEntries().get(col_index_0).toString();
              isString = true;
            }
          }
        } else {
          column_name_0 = expression_0.comparer().literal_value().getText();
        }
        if (expression_1.comparer().column_full_name() != null) {
          column_name_1 = expression_1.comparer().column_full_name().getText();
          int col_index_1 = this.find_column(column_name_1);
          if (col_index_1 >= 0) {
            if (row.getEntries().get(col_index_1).value == null) {
              return false;
            }
            if (columns.get(col_index_1).right == ColumnType.STRING) {
              compare_1_str = row.getEntries().get(col_index_1).toString();
              if (!isString) {
                compare_0_str = column_name_0;
              }
              isString = true;
            }
          }
        } else {
          column_name_1 = expression_1.comparer().literal_value().getText();
          column_name_1 = column_name_1.replace("'", "");
          if (isString) {
            compare_1_str = column_name_1;
          }
        }

        int col_index_0 = this.find_column(column_name_0);
        int col_index_1 = this.find_column(column_name_1);
        if (!isString) {
          compare_0_d = this.compute(row, condition.condition().expression(0));
          compare_1_d = this.compute(row, condition.condition().expression(1));
        }
      } else {
        compare_0_d = this.compute(row, condition.condition().expression(0));
        compare_1_d = this.compute(row, condition.condition().expression(1));
        isString = false;
      }
      if (condition.condition().comparator().EQ() != null) {
        if (isString) {
          return compare_0_str.compareTo(compare_1_str) == 0;
        } else {
          return compare_0_d == compare_1_d;
        }
      } else if (condition.condition().comparator().GE() != null) {
        if (isString) {
          return compare_0_str.compareTo(compare_1_str) >= 0;
        } else {
          return compare_0_d >= compare_1_d;
        }
      } else if (condition.condition().comparator().GT() != null) {
        if (isString) {
          return compare_0_str.compareTo(compare_1_str) > 0;
        } else {
          return compare_0_d > compare_1_d;
        }
      } else if (condition.condition().comparator().LE() != null) {
        if (isString) {
          return compare_0_str.compareTo(compare_1_str) <= 0;
        } else {
          return compare_0_d <= compare_1_d;
        }
      } else if (condition.condition().comparator().LT() != null) {
        if (isString) {
          return compare_0_str.compareTo(compare_1_str) < 0;
        } else {
          return compare_0_d < compare_1_d;
        }
      } else if (condition.condition().comparator().NE() != null) {
        if (isString) {
          return compare_0_str.compareTo(compare_1_str) != 0;
        } else {
          return compare_0_d != compare_1_d;
        }
      }
    }
    return true;
  }

  public int find_column(String column) {
    int index = 0;
    if (!column.contains(".")) {
      column = "." + column;
    }
    for (Pair<String, ColumnType> col : this.columns) {
      if (col.left.contains(column)) {
        return index;
      }
      index++;
    }
    return -1;
  }

  public Double compute(Row row, SQLParser.ExpressionContext expression) {
    if (expression.comparer() == null) {
      if (expression.ADD() != null) {
        Double ex_0 = compute(row, expression.expression(0));
        Double ex_1 = compute(row, expression.expression(1));
        if (ex_0 == null || ex_1 == null) {
          return null;
        }
        return ex_0 + ex_1;
      } else if (expression.SUB() != null) {
        Double ex_0 = compute(row, expression.expression(0));
        Double ex_1 = compute(row, expression.expression(1));
        if (ex_0 == null || ex_1 == null) {
          return null;
        }
        return ex_0 - ex_1;
      } else if (expression.MUL() != null) {
        Double ex_0 = compute(row, expression.expression(0));
        Double ex_1 = compute(row, expression.expression(1));
        if (ex_0 == null || ex_1 == null) {
          return null;
        }
        return ex_0 * ex_1;
      } else if (expression.DIV() != null) {
        Double ex_0 = compute(row, expression.expression(0));
        Double ex_1 = compute(row, expression.expression(1));
        if (ex_0 == null || ex_1 == null) {
          return null;
        }
        return ex_0 / ex_1;
      }
    } else {
      String column_name = "";
      if (expression.comparer().column_full_name() != null) {
        column_name = expression.comparer().column_full_name().getText();
      } else {
        column_name = expression.comparer().literal_value().getText();
      }
      int col_index = this.find_column(column_name);
      if (col_index >= 0) {
        if (row.getEntries().get(col_index).value == null) {
          return null;
        }
        return Double.valueOf(row.getEntries().get(col_index).value.toString());
      } else {
        return Double.valueOf(column_name);
      }
    }
    return null;
  }

  public QueryTable filter(SQLParser.Multiple_conditionContext condition) {
    QueryTable qtable = new QueryTable();
    for (Pair<String, ColumnType> col : this.columns) {
      qtable.columns.add(col);
    }
    for (Row row : this.Rows) {
      if (this.verify(row, condition)) {
        qtable.Rows.add(row);
        qtable.size++;
      }
    }
    return qtable;
  }

  public QueryTable select(List<SQLParser.Result_columnContext> result_column) {
    QueryTable qtable = new QueryTable();
    ArrayList<Integer> col_index = new ArrayList<Integer>();
    for (SQLParser.Result_columnContext col : result_column) {
      if (col.table_name() != null) {
        String t_name = col.table_name().getText() + ".";
        int index = 0;
        Boolean col_find = false;
        for (Pair<String, ColumnType> t_col : this.columns) {
          if (t_col.left.contains(t_name)) {
            col_find = true;
            if (!col_index.contains(index)) {
              col_index.add(index);
            }
          }
          index++;
        }
        if (!col_find) {
          return null;
        }
      } else if (col.MUL() != null) {
        for (Pair<String, ColumnType> res_col : this.columns) {
          qtable.columns.add(res_col);
        }
        this.pos = 0;
        while (this.hasNext()) {
          qtable.Rows.add(this.next());
          qtable.size++;
        }
        return qtable;
      } else {
        String col_name = col.column_full_name().getText();
        if (col_name.equalsIgnoreCase("distinct")) {
          continue;
        }
        if (col_name.indexOf(".") == -1) {
          col_name = "." + col_name;
        }
        int index = 0;
        boolean col_find = false;
        for (Pair<String, ColumnType> t_col : this.columns) {
          if (t_col.left.contains(col_name)) {
            col_find = true;
            if (!col_index.contains(index)) {
              col_index.add(index);
            }
            break;
          }
          index++;
        }
        if (!col_find) {
          return null;
        }
      }
    }
    for (Integer col_id : col_index) {
      qtable.columns.add(this.columns.get(col_id));
    }
    this.pos = 0;
    while (this.hasNext()) {
      Row row = this.next();

      ArrayList<Cell> tmp_clist = new ArrayList<Cell>();
      for (Integer col_id : col_index) {
        tmp_clist.add(row.getEntries().get(col_id));
      }
      Row tmp_row = new Row(tmp_clist);
      qtable.Rows.add(tmp_row);
      qtable.size++;
    }
    return qtable;
  }

  public QueryTable qappend(QueryTable qtable) {
    QueryTable new_qtable = new QueryTable();
    for (Pair<String, ColumnType> col : this.columns) {
      new_qtable.columns.add(col);
    }
    for (Pair<String, ColumnType> col : qtable.columns) {
      new_qtable.columns.add(col);
    }
    this.pos = 0;
    qtable.pos = 0;
    while (this.hasNext()) {
      Row row_1 = this.next();
      qtable.pos = 0;
      while (qtable.hasNext()) {
        Row row_2 = qtable.next();
        Row tmp = new Row(row_1);
        tmp.appendEntries(row_2.getEntries());
        new_qtable.Rows.add(tmp);
        new_qtable.size++;
      }
    }
    return new_qtable;
    //    return null;
  }

  public void qjoin(QueryTable qtable) {}

  @Override
  public boolean hasNext() {
    // TODO
    if (this.pos < this.size) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Row next() {
    // TODO
    pos++;
    return this.Rows.get(pos - 1);
  }
}
