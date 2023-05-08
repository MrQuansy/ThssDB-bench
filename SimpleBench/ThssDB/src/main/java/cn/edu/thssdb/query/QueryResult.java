package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.utils.Cell;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class QueryResult {

    private QueryTable queryTable;
    private ArrayList<MetaInfo> metaInfoInfos;
    private ArrayList<Integer> columnIndexes;
    public ArrayList<String> columnNames;
    private boolean isDistinct;
    private HashSet<String> stringResults;
    public ArrayList<Row> results;

    private List<Cell> attrs;
    private boolean wrong = false; //是否是错误的结果
    private String sql;
    private String message;


    public QueryResult(String s, boolean success){
        this.wrong = ! success;
        this.message = s;
    }

    public QueryResult(QueryTable[] queryTables) {
        // TODO
        this.columnIndexes = new ArrayList<>();
        this.stringResults = new HashSet<>();
        this.attrs = new ArrayList<>();
        this.results = new ArrayList<>();
    }
    public void setWrong(boolean mWrong){
        wrong = mWrong;
    }

    public void setSql(String s){
        sql = s;
    }

    public boolean isWrong(){
        return wrong;
    }

    public String getMessage(){
        return message;
    }

    public String getSql(){
        return sql;
    }

    /**
     * 构造函数，接收查询表、选择列、是否distinct
     * @param queryTable
     * @param selectedColumns
     * @param isDistinct
     */
    public QueryResult(QueryTable queryTable, String[] selectedColumns, boolean isDistinct) {
        this.queryTable = queryTable;
        this.metaInfoInfos = new ArrayList<>();
        this.metaInfoInfos.addAll(queryTable.getMetaInfo());

        this.columnIndexes = new ArrayList<>();
        this.columnNames = new ArrayList<>();
        this.stringResults = new HashSet<>();
        this.results = new ArrayList<>();
        // 选中某些列
        if (selectedColumns != null && selectedColumns.length > 0) {
            for (String selectedColumn : selectedColumns) {
                this.columnIndexes.add(getIndexFromColumn(selectedColumn));
                this.columnNames.add(selectedColumn);
            }
        }
        // 没有选中列
        else {
            int total=0;
            for(MetaInfo metaInfo:metaInfoInfos){
                for(int i=0;i<metaInfo.getColumnSize();++i){
                    this.columnIndexes.add(total+i);
                    this.columnNames.add(metaInfo.getFullColumnName(i));
                }
                total+=metaInfo.getColumnSize();
            }
        }
        this.isDistinct=isDistinct;
    }

    public static Row combineRow(LinkedList<Row> rows) {
        ArrayList<Entry> new_row = new ArrayList<>();
        for (Row row : rows) {
            new_row.addAll(row.getEntries());
        }
        return new Row(new_row);
    }

    public void generateQueryRecord() {
        while(queryTable.hasNext()){
            QueryRow row=queryTable.next();
            if(row==null)break;
            int selectedNum=columnIndexes.size();
            Entry[] entries=new Entry[selectedNum];
            ArrayList<Entry> allEntries=row.getEntries();
            for(int i=0;i<selectedNum;++i){
                entries[i]=allEntries.get(columnIndexes.get(i));
            }
            Row result=new Row(entries);
            String stringResult=result.toString();
            if(!isDistinct||!stringResults.contains(stringResult)){
                if(isDistinct) {
                    stringResults.add(stringResult);
                }
                results.add(result);
            }
        }
    }

    /**
     * 根据列名找到下标
     *
     * @param full_name
     * @return
     */
    private int getIndexFromColumn(String full_name) {
        int target_index = 0;
        int equalNum = 0;
        int total = 0;
        // 纯ColumnName
        if (!full_name.contains(".")) {
            for (MetaInfo metaInfo : metaInfoInfos) {
                int columnIndex = metaInfo.columnFind(full_name);
                if (columnIndex >= 0) {
                    target_index = total + columnIndex;
                    ++equalNum;
                }
                total += metaInfo.getColumnSize();
            }
        } else {
            String[] splited = MetaInfo.splitFullName(full_name);
            String tableName = splited[0],
                    columnName = splited[1];
            for (MetaInfo metaInfo : metaInfoInfos) {
                if (!metaInfo.getTableName().equals(tableName)) {
                    total += metaInfo.getColumnSize();
                    continue;
                }

                int columnIndex = metaInfo.columnFind(columnName);
                if (columnIndex >= 0) {
                    target_index = total + columnIndex;
                    ++equalNum;
                }
            }
        }

        if (equalNum < 1) {
            // TODO:抛出异常
        }
        if (equalNum > 1) {
            // TODO: 抛出异常
        }
        return target_index;
    }
}
