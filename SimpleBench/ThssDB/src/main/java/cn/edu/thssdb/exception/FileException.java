package cn.edu.thssdb.exception;

public class FileException extends RuntimeException{
    private String key;

    public FileException()
    {
        super();
        this.key = null;
    }

    public FileException(String key)
    {
        super();
        this.key = key;
    }

    @Override
    public String getMessage() {
        if (key == null)
            return "Exception: file exception!";
        else
            return "Exception: file \"" + this.key + "\" exception!";
    }
}

