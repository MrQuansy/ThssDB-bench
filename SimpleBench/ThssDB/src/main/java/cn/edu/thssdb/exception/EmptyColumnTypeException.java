package cn.edu.thssdb.exception;

public class EmptyColumnTypeException extends RuntimeException{
    private String columnName;

    public EmptyColumnTypeException(String columnName)
    {
        super();
        this.columnName = columnName;
    }

    @Override
    public String getMessage() {
        if (columnName == null)
            return "Exception: columnType doesn't exist!";
        else
            return "Exception: columnType of value \"" + this.columnName + "\" doesn't exist!";
    }
}
