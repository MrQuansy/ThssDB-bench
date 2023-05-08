package cn.edu.thssdb.exception;

public class NotNumberException extends RuntimeException{
    private String numericLiteral;

    public NotNumberException(String numericLiteral)
    {
        super();
        this.numericLiteral = numericLiteral;
    }

    @Override
    public String getMessage() {
        if (numericLiteral == null)
            return "Exception: value in String doesn't exist!";
        else
            return "Exception: value \"" + this.numericLiteral + "\" in String is not a number!";
    }
}

