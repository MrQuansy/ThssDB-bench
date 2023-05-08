package cn.edu.thssdb.query;

import cn.edu.thssdb.exception.NotNumberException;
import cn.edu.thssdb.type.ExpressionType;

public class Expression {
    private ExpressionType type;
    private Comparable value;

    public Expression(ExpressionType type, String value) {
        this.type = type;
        try {
            switch (type) {
                case NUMBER:
                    this.value = Double.parseDouble(value);
                    break;
                case STRING:
                case COLUMN:
                    this.value = value;
                    break;
                default:
                    this.value = null;
            }
        }catch (Exception e){
            throw new NotNumberException(value);
        }

    }

    public ExpressionType getType() {
        return type;
    }

    public Comparable getValue() {
        return value;
    }

    public static Expression fromExpression() {
        return new Expression(ExpressionType.NUMBER, "0");
    }
}
