package cn.edu.thssdb.query;

import cn.edu.thssdb.type.LogicResultType;
import cn.edu.thssdb.type.LogicType;

public class Logic {
  public Logic lLogic, rLogic;
  public LogicType type;

  public Condition condition;
  public boolean isTerminal;

  public Logic(Logic lLogic, Logic rLogic, LogicType type) {
    this.lLogic = lLogic;
    this.rLogic = rLogic;
    this.type = type;
    this.isTerminal = false;
  }

  public Logic(Condition condition) {
    this.condition = condition;
    this.isTerminal = true;
  }

  public LogicResultType getResult(QueryRow curRow) throws Exception {
    if (isTerminal) {
      return (condition == null) ? LogicResultType.TRUE : condition.getResult(curRow);
    }
    LogicResultType lResult = ((lLogic == null) ? LogicResultType.TRUE : lLogic.getResult(curRow)),
        rResult = ((rLogic == null) ? LogicResultType.TRUE : rLogic.getResult(curRow));
    if (lResult == LogicResultType.UNKNOWN || rResult == LogicResultType.UNKNOWN)
      return LogicResultType.UNKNOWN;
    if (type == LogicType.AND) {
      if (lResult == LogicResultType.FALSE || rResult == LogicResultType.FALSE)
        return LogicResultType.FALSE;
      if (lResult == LogicResultType.TRUE && rResult == LogicResultType.TRUE)
        return LogicResultType.TRUE;
    } else if (type == LogicType.OR) {
      if (lResult == LogicResultType.TRUE || rResult == LogicResultType.TRUE)
        return LogicResultType.TRUE;
      if (lResult == LogicResultType.FALSE && rResult == LogicResultType.FALSE)
        return LogicResultType.FALSE;
    }
    return LogicResultType.UNKNOWN;
  }
}
