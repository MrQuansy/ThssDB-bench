package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Column;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.Table;
import cn.edu.thssdb.type.LogicResultType;
//import sun.tools.jconsole.Tab;

import java.util.ArrayList;
import java.util.Iterator;

public class ExtendedQueryTable extends QueryTable {
    private ArrayList<Iterator<Row>> iters;
    private ArrayList<Table> tables;
    private ArrayList<Column> columns;

    private Logic logicJoin;
    private ArrayList<Row> rowsToJoin;

    /**
     * 构造函数，用于一个以上表连接时
     *
     * @param tables
     * @param logicJoin
     */
    public ExtendedQueryTable(ArrayList tables, Logic logicJoin) throws Exception {
        super();
        this.iters = new ArrayList<>();
        this.tables = tables;
        this.logicJoin = logicJoin;
        this.rowsToJoin = new ArrayList<>();
        this.columns = new ArrayList<>();
        for (Table table : this.tables) {
            this.columns.addAll(table.columns);
            this.iters.add(table.iterator());
        }
    }

    /**
     * 获取所有表的MetaInfo
     *
     * @return
     */
    @Override
    public ArrayList<MetaInfo> getMetaInfo() {
        ArrayList<MetaInfo> metaInfos = new ArrayList<>();
        for (Table table : tables) {
            metaInfos.add(new MetaInfo(table.tableName, table.columns));
        }
        return metaInfos;
    }

    /**
     * 向QueryTable中添加新的QueryRow
     *
     * @throws Exception
     */
    @Override
    protected void nextQueryRow() throws Exception {
        while (true) {
            QueryRow row = buildQueryRow();
            if (row == null) return;
            if ((logicJoin == null || logicJoin.getResult(row) == LogicResultType.TRUE)
                    && (logicSelect == null || logicSelect.getResult(row) == LogicResultType.TRUE)) {
                queue.add(row);
                return;
            }
        }
    }

    /**
     * 找到下一组需要Join的Row，保存为QueryRow
     *
     * @return
     */
    private QueryRow buildQueryRow() {
        if (rowsToJoin.isEmpty()) {
            for (Iterator<Row> iter : iters) {
                if (!iter.hasNext()) return null;
                rowsToJoin.add(iter.next());
            }
        } else {
            int i;
            for (i = iters.size() - 1; i >= 0; --i) {
                rowsToJoin.remove(rowsToJoin.size() - 1);
                if (iters.get(i).hasNext()) break;
                iters.set(i, tables.get(i).iterator());
            }
            if (i < 0) return null;
            for (; i < iters.size(); ++i) {
                if (!iters.get(i).hasNext()) return null;
                rowsToJoin.add(iters.get(i).next());
            }
        }
        return new QueryRow(rowsToJoin, tables);
    }

    public ArrayList<Table>  getTables(){
        return tables;
    }
}
