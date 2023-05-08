package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Column;

import java.util.ArrayList;
import java.util.List;

class MetaInfo {

    private String tableName;
    private List<Column> columns;

    MetaInfo(String tableName, ArrayList<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    /**
     * 找到对应列的下标
     *
     * @param name
     * @return
     */
    int columnFind(String name) {
        for (int i = 0; i < columns.size(); ++i) {
            if (columns.get(i).getName().equals(name)) return i;
        }
        return -1;
    }

    public String getFullColumnName(int index) {
        if (index < 0 || index >= columns.size()) return null;
        return tableName + "." + columns.get(index).getName();
    }

    public String getTableName() {
        return tableName;
    }

    public int getColumnSize() {
        return columns.size();
    }

    /**
     * 将TableName.ColumnName转换为[TableName, ColumnName]
     *
     * @param full_name
     * @return
     */
    public static String[] splitFullName(String full_name) {
        String[] splited = full_name.split("\\.");
        if (splited.length != 2) {
            // TODO: 抛出异常
        }
        return splited;
    }
}