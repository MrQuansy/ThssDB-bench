package cn.edu.thssdb.schema;

import cn.edu.thssdb.cache.Cache;
import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.index.BPlusTree;
import cn.edu.thssdb.query.Expression;
import cn.edu.thssdb.query.Logic;
import cn.edu.thssdb.query.QueryRow;
import cn.edu.thssdb.type.ColumnType;
import cn.edu.thssdb.type.ExpressionType;
import cn.edu.thssdb.type.LogicResultType;
import cn.edu.thssdb.utils.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static cn.edu.thssdb.utils.Global.DATA_DIRECTORY;

enum LockType {EMPTY, READLOCK, WRITELOCK};

public class Table implements Iterable<Row> {
    ReentrantReadWriteLock lock;
    private String databaseName;
    public String tableName;
    public ArrayList<Column> columns;
    private Cache cache;
    //public BPlusTree<Entry, Row> index;
    private int primaryIndex;
    private Long x_lock_session;
    private ArrayList<Long> s_lock_sessions;
    LockType current_lock_type;

    public Table(String databaseName, String tableName, Column[] columns) {
        // 初始化
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.columns = new ArrayList<>(Arrays.asList(columns));
        this.primaryIndex = -1;
        // 找到主键
        int schema_len = this.columns.size();
        for (int i = 0; i < schema_len; ++i) {
            if (columns[i].isPrimary() == 1) {
                this.primaryIndex = i;
                break;
            }
        }
        if (primaryIndex < 0) {
            throw new KeyNotExistException();
        }
        this.cache = new Cache(databaseName, tableName);
        this.lock = new ReentrantReadWriteLock();
        x_lock_session = null;
        s_lock_sessions = new ArrayList<Long>();
        current_lock_type = LockType.EMPTY;
        recover();
    }

    public Column[] getMetaData() {
        return columns.toArray(new Column[0]);
    }

    /**
     * @description: 从内存中恢复数据
     */
    private void recover() {
        try {
            File dir = new File(DATA_DIRECTORY);
            File[] all_files = dir.listFiles();
            if (all_files == null) return;
            for (File f : all_files) {
                if (f.isFile()) {
                    try {
                        // 表格的元数据信息
                        String[] filename = f.getName().split("_");
                        if (filename.length == 2 &&
                                filename[0].equals(this.databaseName) && filename[1].equals(this.tableName)) {
                            cache.recover(f, primaryIndex);
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            throw new FileException();
        }
    }


    /**
     * 事务相关
     */
    // 返回-1加锁失败，1表示加锁成功，0表示已经有锁
    public int put_x_lock(Long sessionId) {
        if (current_lock_type.equals(LockType.EMPTY)) {
            x_lock_session = sessionId;
            current_lock_type = LockType.WRITELOCK;
            return 1;
        } else if (x_lock_session != null && x_lock_session.equals(sessionId)) {
            return 0;
        }
        return -1;
    }

    public int put_s_lock(Long sessionId) {
        if ((current_lock_type.equals(LockType.READLOCK) && s_lock_sessions.contains(sessionId)) ||
                (x_lock_session != null && x_lock_session.equals(sessionId))) {
            return 0;
        } else if (current_lock_type.equals(LockType.EMPTY) || current_lock_type.equals(LockType.READLOCK)) {
            s_lock_sessions.add(sessionId);
            current_lock_type = LockType.READLOCK;
            return 1;
        }
        return -1;
    }

    //type 1 表示释放读锁，type 2表示释放写锁
    public void free_lock(Long sessionId, int type) {
        if (type == 1 && s_lock_sessions.contains(sessionId)) {
            s_lock_sessions.remove(sessionId);
            if (s_lock_sessions.size() == 0) {
                current_lock_type = LockType.EMPTY;
            }
        } else if (type == 2 && x_lock_session != null && x_lock_session.equals(sessionId)) {
            x_lock_session = null;
            current_lock_type = LockType.EMPTY;
        }
        System.out.println(sessionId);
    }

    /**
     * @description: 将columns和 entries对应起来
     * @param: columns, entries
     * @return: 返回从属性名到具体值的映射
     */
    public HashMap<Column, Entry> match_column_entry(ArrayList<Column> columns, ArrayList<Entry> entries) {
        if (columns == null || entries == null) {
            return null;
        }
        HashMap<Column, Entry> res = new HashMap<>();
        int col_len = columns.size();
        if (col_len == entries.size()) {
            for (int i = 0; i < col_len; ++i) {
                Column column = columns.get(i);
                Entry entry = entries.get(i);
                try {
                    if (entry.value == null) {
                        if (column.isNotNull()) {
                            throw new AttributeNullException(column.getName());
                        }
                    } else if (column.getType() == ColumnType.INT) {
                        int newValue = Integer.parseInt((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.LONG) {
                        long newValue = Long.parseLong((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.FLOAT) {
                        float newValue = Float.parseFloat((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.DOUBLE) {
                        double newValue = Double.parseDouble((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.STRING) {
                        String newValue = (String) entry.value;
                        entry.value = newValue;
                    } else {
                        throw new Exception("插入类型转换错误");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new WrongCoumnInsertException(entry.value, column.getType());
                }
                res.put(column, entry);
            }
        } else {
            throw new ColumnsNotMatchException();
        }
        return res;
    }

    /**
     * @description: 插入一行数据
     * @param: columns, entries，插入数据的列名和数据
     * @return:
     */
    public void insert(ArrayList<Column> columns, ArrayList<Entry> entries) {
        // 匹配
        HashMap<Column, Entry> matched = match_column_entry(columns, entries);
        // 将数据与schema对应起来
        ArrayList<Entry> ordered_entries = new ArrayList<>();
        for (Column col : this.columns) {
            if (matched.get(col) != null) {
                ordered_entries.add(matched.get(col));
            } else {
                if (col.isPrimary() == 1) {
                    throw new PrimaryKeyNullException(col.getName());
                }
                if (col.isNotNull()) {
                    throw new AttributeNullException(col.getName());
                }
                ordered_entries.add(null);
            }
        }
        try {
            lock.writeLock().lock();
            cache.insertRow(ordered_entries, primaryIndex);
            //index.put(ordered_entries.get(this.primaryIndex), new Row(ordered_entries.toArray(new Entry[this.columns.size()])));
        } catch (Exception e) {
            throw e;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * @description: 删除一行数据
     * @param: primaryEntry, 删除行的主键
     * @return:
     */
    public void delete(Entry primaryEntry) {
        if (primaryEntry == null)
            throw new KeyNotExistException();
        try {
            lock.writeLock().lock();
            cache.deleteRow(primaryEntry, primaryIndex);
            //index.remove(primaryEntry);
        } catch (Exception e) {
            throw e;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * @description: 删除一行数据 加入了parser
     * @param: primaryEntry, 删除行的主键
     * @return:
     */
    public int delete(Logic logic) throws Exception {
        int num = 0;
        for (Row row : this) {
            QueryRow query_row = new QueryRow(new ArrayList<Row>(Arrays.asList(row)), new ArrayList<Table>(Arrays.asList(this)));
            if (logic == null || logic.getResult(query_row) == LogicResultType.TRUE) {
                delete(row.getEntries().get(primaryIndex));
                ++num;
            }
        }
        return num;
    }

    /**
     * @description: 删除所有数据，保留元数据
     * @param: NULL
     * @return: NULL
     */
    public void deleteAll() {
        try {
            lock.writeLock().lock();
            cache.deleteAll(primaryIndex);
        } catch (Exception e) {
            throw e;
        } finally {
            lock.writeLock().unlock();
        }

    }

    /**
     * @description: 更新一行数据
     * @param: primaryEntry, columns, entries
     * @return:
     */
    public void update(Entry primaryEntry, ArrayList<Column> columns, ArrayList<Entry> entries) {
        if (primaryEntry == null) {
            throw new KeyNotExistException();
        }
        int column_len = columns.size();
        int table_column_len = this.columns.size();
        int target_keys[] = new int[column_len];
        boolean matched;
        for (int i = 0; i < columns.size(); ++i) {
            matched = false;
            for (int j = 0; j < table_column_len; ++j) {
                if (columns.get(i).equals(this.columns.get(j))) {
                    target_keys[i] = j;
                    matched = true;
                    break;
                }
            }
            if (!matched)
                throw new KeyNotExistException(columns.get(i).toString());
        }
        try {
            lock.writeLock().lock();
            cache.updateRow(primaryEntry, primaryIndex, target_keys, entries);
        } catch (KeyNotExistException | DuplicateKeyException e) {
            throw e;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * @description: 更新一行数据 parser
     * @param: columnName, expression, logic
     * @return:
     */
    public int update(String columnName, Expression expression, Logic logic) throws Exception {
        int num = 0;
        for (Row row : this) {
            QueryRow query_row = new QueryRow(new ArrayList<Row>(Arrays.asList(row)), new ArrayList<Table>(Arrays.asList(this)));
            if (logic == null || logic.getResult(query_row) == LogicResultType.TRUE) {
                // 查找对应的列
                Column column = null;
                boolean flag = false;
                for (Column col : this.columns) {
                    if (col.getName().equals(columnName)) {
                        column = col;
                        flag = true;
                        break;
                    }
                }
                if (flag == false || column == null) {
                    throw new AttributeNotExistException(columnName);
                }
                Comparable exp_value = checkExpression(expression, column);
                if (checkValid(column, exp_value)) {
                    update(row.getEntries().get(primaryIndex), new ArrayList<Column>(Arrays.asList(column)),
                            new ArrayList<Entry>(Arrays.asList(new Entry(exp_value))));
                    ++num;
                }
            }
        }
        return num;
    }

    public Row query(Entry primaryEnter) {
        if (primaryEnter == null) {
            return null;
        }
        Row find;
        try {
            lock.readLock().lock();
            find = cache.getRow(primaryEnter, primaryIndex);
            //find = index.get(primaryEnter);
        } catch (Exception e) {
            throw e;
        } finally {
            lock.readLock().unlock();
        }
        return find;
    }

    /**
     * @description:
     * @param:
     * @return:
     */
    public void persist() {
        try {
            lock.readLock().lock();
            cache.persist();
        } finally {
            lock.readLock().unlock();
        }
    }

    private ArrayList<Row> deserialize() {
        return null;
    }

    private boolean checkValid(Column col, Comparable exp_value) {
        if (exp_value == null && col.isNotNull()) {
            throw new NullException(col.getName());
        }
        if (col.getType() == ColumnType.STRING && exp_value != null) {
            int max_len = col.getMaxLength();
            if (max_len >= 0 && (exp_value.toString().length() > max_len))
                throw new WrongColumnConstraintException(col.getName() + "'s length has exceeded its maximum");
        }
        return true;
    }

    private Comparable checkExpression(Expression exp, Column col) {
        if (exp == null || exp.getValue() == null || exp.getType() == ExpressionType.NULL) {
            if (col.isNotNull())
                throw new NullException(col.getName());
            return null;
        }

        if (exp.getType() == ExpressionType.COLUMN) {
            throw new WrongColumnTypeException("COLUMN");
        }
        ColumnType col_type = col.getType();
        ExpressionType exp_type = exp.getType();
        String exp_str = exp.getValue().toString();
        if (exp_type == ExpressionType.STRING) {
            if (col_type == ColumnType.STRING)
                return exp_str;
            throw new WrongColumnTypeException("String");
        } else {
            if (col_type == ColumnType.INT) {
                return Integer.parseInt(exp_str);
            } else if (col_type == ColumnType.DOUBLE) {
                return Double.parseDouble(exp_str);
            } else if (col_type == ColumnType.FLOAT) {
                return Float.parseFloat(exp_str);
            } else {
                throw new WrongColumnTypeException("NUMBER");
            }
        }
    }

    public int update(String columnName, Entry entry, Logic logic) throws Exception {
        int num = 0;
        for (Row row : this) {
            QueryRow query_row = new QueryRow(new ArrayList<Row>(Arrays.asList(row)), new ArrayList<Table>(Arrays.asList(this)));
            if (logic == null || logic.getResult(query_row) == LogicResultType.TRUE) {
                // 查找对应的列
                Column column = null;
                boolean flag = false;
                for (Column col : this.columns) {
                    if (col.getName().equals(columnName)) {
                        column = col;
                        flag = true;
                        break;
                    }
                }
                if (flag == false || column == null) {
                    throw new AttributeNotExistException(columnName);
                }
                try {
                    if (entry.value == null) {
                        if (column.isNotNull()) {
                            throw new AttributeNullException(column.getName());
                        }
                    } else if (column.getType() == ColumnType.INT) {
                        int newValue = Integer.parseInt((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.LONG) {
                        long newValue = Long.parseLong((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.FLOAT) {
                        float newValue = Float.parseFloat((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.DOUBLE) {
                        double newValue = Double.parseDouble((String) entry.value);
                        entry.value = newValue;
                    } else if (column.getType() == ColumnType.STRING) {
                        String newValue = (String) entry.value;
                        entry.value = newValue;
                    } else {
                        throw new Exception("插入类型转换错误");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new WrongCoumnInsertException(entry.value, column.getType());
                }
                if (checkValid(column, entry.value)) {
                    update(row.getEntries().get(primaryIndex), new ArrayList<Column>(Arrays.asList(column)),
                            new ArrayList<Entry>(Arrays.asList(entry)));
                    ++num;
                }
            }
        }
        return num;
    }

    /**
     * --- iterator ---
     */
    private class TableIterator implements Iterator<Row> {
        private Iterator<Pair<Entry, Integer>> iterator;
        private LinkedList<Entry> entries;
        private Cache cache;

        TableIterator(Table table) {
            cache = table.cache;
            iterator = table.cache.getIndexIterator();
            entries = new LinkedList<>();
            while (iterator.hasNext()) {
                entries.add(iterator.next().getLeft());
            }
            iterator = null;
        }

        @Override
        public boolean hasNext() {
            return !entries.isEmpty();
        }

        @Override
        public Row next() {
            Entry entry = entries.getFirst();
            Row row = cache.getRow(entry, primaryIndex);
            entries.removeFirst();
            return row;
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return new TableIterator(this);
    }

}
