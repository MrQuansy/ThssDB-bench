package cn.edu.thssdb.exception;

public class ParseErrorException extends RuntimeException{
    private String errorString;

    public ParseErrorException(String errorString)
    {
        super();
        this.errorString = errorString;
    }

    @Override
    public String getMessage() {
        if (errorString == null)
            return "Exception: Wrong SQL!";
        else
            return "Exception: "+ errorString;
    }
}
