package cn.edu.thssdb.query;

import cn.edu.thssdb.type.ConditionType;
import cn.edu.thssdb.type.ExpressionType;
import cn.edu.thssdb.type.LogicResultType;

public class Condition {
  Expression left, right;
  ConditionType type;

  public Condition(Expression left, Expression right, ConditionType type) {
    this.left = left;
    this.right = right;
    this.type = type;
  }

  public LogicResultType getResult(QueryRow row) throws Exception {
    ExpressionType lType = left.getType();
    Comparable lValue = left.getValue();
    ExpressionType rType = right.getType();
    Comparable rValue = right.getValue();
    ExpressionType llType = lType, rrType = rType;

    if (lType == ExpressionType.COLUMN) {
      Expression tLeft = row.getExpressionFromColumn((String) lValue);
      lType = tLeft.getType();
      lValue = tLeft.getValue();
    }
    if (rType == ExpressionType.COLUMN) {
      Expression tRight = row.getExpressionFromColumn((String) rValue);
      rType = tRight.getType();
      rValue = tRight.getValue();
    }
    if (lType == ExpressionType.NULL && rType == ExpressionType.NULL && type == ConditionType.EQ) {
      return LogicResultType.TRUE;
    }

    if (lType == ExpressionType.NULL
        || rType == ExpressionType.NULL
        || left == null
        || right == null
        || lValue == null
        || rValue == null) {
      return LogicResultType.UNKNOWN;
    }
    if (llType == ExpressionType.COLUMN && rrType != ExpressionType.COLUMN && lType != rType) {
      try {
        if (lType == ExpressionType.NUMBER) {
          double newValue = Double.parseDouble((String) rValue);
          rType = ExpressionType.NUMBER;
          rValue = newValue;
        } else if (lType == ExpressionType.STRING) {
          String newValue = String.valueOf(rValue);
          rType = ExpressionType.STRING;
          rValue = newValue;
        }
      } catch (NumberFormatException e) {
        throw new Exception("查找转换右类型失败 " + rType.name());
      }
    } else if (llType != ExpressionType.COLUMN
        && rrType == ExpressionType.COLUMN
        && lType != rType) {
      try {
        if (rType == ExpressionType.NUMBER) {
          double newValue = Double.parseDouble((String) lValue);
          lType = ExpressionType.NUMBER;
          lValue = newValue;
        } else if (rType == ExpressionType.STRING) {
          String newValue = String.valueOf(lValue);
          lType = ExpressionType.STRING;
          lValue = newValue;
        }
      } catch (NumberFormatException e) {
        throw new Exception("查找转换左类型失败 " + lType.name());
      }
    }
    if (lType != rType) {
      // TODO: 抛出异常
      throw new Exception("Wrong Compare Type: " + lType.name() + ": " + rType.name());
    }
    int compareResult = (lValue.compareTo(rValue));
    boolean result = false;
    switch (type) {
      case EQ:
        result = (compareResult == 0);
        break;
      case NE:
        result = (compareResult != 0);
        break;
      case GT:
        result = (compareResult > 0);
        break;
      case LT:
        result = (compareResult < 0);
        break;
      case GE:
        result = (compareResult >= 0);
        break;
      case LE:
        result = (compareResult <= 0);
        break;
    }
    if (result) {
      return LogicResultType.TRUE;
    } else {
      return LogicResultType.FALSE;
    }
  }
}
