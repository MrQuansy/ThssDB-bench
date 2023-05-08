package cn.edu.thssdb.exception;

public class ColumnsNotMatchException extends RuntimeException{
    private String key;

    public ColumnsNotMatchException()
    {
        super();
        this.key = null;
    }


    @Override
    public String getMessage() {
        return "Exception: Columns don't match!";
    }
}

