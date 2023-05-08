package cn.edu.thssdb.exception;

public class PrimaryKeyNullException extends RuntimeException {
    private String mColumnName;

    public PrimaryKeyNullException(String column_name)
    {
        super();
        this.mColumnName = column_name;
    }

    @Override
    public String getMessage() {
        return "Exception: Primary key " + mColumnName + " should not be null!";
    }
}