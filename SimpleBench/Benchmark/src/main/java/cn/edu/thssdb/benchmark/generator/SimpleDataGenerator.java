package cn.edu.thssdb.benchmark.generator;

import cn.edu.thssdb.benchmark.common.Constants;
import cn.edu.thssdb.benchmark.common.TableSchema;

import java.util.ArrayList;
import java.util.List;

public class SimpleDataGenerator extends BaseDataGenerator {

  private String stringFormat = "%0" + Constants.stringLength + "d";

  @Override
  protected void initTableSchema() {
    for (int tableId = 0; tableId < Constants.tableCount; tableId++) {
      String tableName = "test_table" + tableId;
      List<String> columns = new ArrayList<>();
      List<Integer> types = new ArrayList<>();
      for (int columnId = 0; columnId < Constants.columnCount; columnId++) {
        columns.add("column" + columnId);
        types.add(columnId % Constants.columnTypes.length);
      }
      schemaMap.put(tableName, new TableSchema(tableName, columns, types, tableId));
    }
  }

  @Override
  public Object generateValue(String tableName, int rowId, int columnId) {
    switch (schemaMap.get(tableName).types.get(columnId)) {
      case 0:
        return rowId;
      case 1:
        return (long) rowId;
      case 2:
        return (double) rowId;
      case 3:
        return (float) rowId;
      case 4:
        return String.format(stringFormat, rowId);
    }
    return null;
  }
}
