import java.util.List;

public class TableSchema {

    public String tableName;
    public List<String> columns;
    public List<Integer> types;
    public int primaryKeyColumnIndex;

    public TableSchema(String tableName,List<String> columns,List<Integer> types,int primaryKeyColumnIndex){
        this.tableName = tableName;
        this.columns = columns;
        this.types = types;
        this.primaryKeyColumnIndex = primaryKeyColumnIndex;
    }
}
