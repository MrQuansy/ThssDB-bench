package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Row;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class QueryTable implements Iterator<Row> {
  public ArrayList<QueryRow> queue;
  protected Logic logicSelect;
  private boolean isFirst;

  QueryTable() throws Exception {
    queue = new ArrayList<>();
    isFirst = true;
  }

  @Override
  public boolean hasNext() {
    return isFirst || !queue.isEmpty();
  }

  @Override
  public QueryRow next() {
    if (isFirst) {
      isFirst = false;
      try {
        nextQueryRow();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (queue.isEmpty()) return null;
    QueryRow next = queue.get(queue.size() - 1);
    queue.remove(queue.size() - 1);
    if (queue.isEmpty()) {
      try {
        nextQueryRow();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return next;
  }

  /**
   * 设置选择逻辑
   *
   * @param logicSelect
   */
  public void setLogicSelect(Logic logicSelect) {
    this.logicSelect = logicSelect;
  }

  protected abstract void nextQueryRow() throws Exception;

  public abstract ArrayList<MetaInfo> getMetaInfo();
}
