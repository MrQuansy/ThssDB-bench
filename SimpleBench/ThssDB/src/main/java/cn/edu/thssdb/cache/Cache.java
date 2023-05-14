package cn.edu.thssdb.cache;

import cn.edu.thssdb.exception.DuplicateKeyException;
import cn.edu.thssdb.exception.KeyNotExistException;
import cn.edu.thssdb.index.BPlusTree;
import cn.edu.thssdb.index.BPlusTreeIterator;
import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;

import java.io.*;
import java.util.*;

import static cn.edu.thssdb.utils.Global.DATA_DIRECTORY;

/**
 * 页式缓存方案： 将所有row存储在Page中，BPlusTree保存primaryEntry到pageId的索引
 * entryIndex保存当前在缓存中的所有primaryEntry到pageId的索引 所有已有Page，不论是否在缓存中，都在BPlusTree中有记录
 */
public class Cache {
  private String cacheName;

  private BPlusTree<Entry, Integer> index;

  HashMap<Entry, Integer> entryIndex;
  private static final int maxPageNum = 1024;
  //    HashMap<Integer,Page> pages;
  LinkedHashMap<Integer, Page> pages;
  private int pageNum;

  public Cache(String dbName, String tbName) {
    this.cacheName = dbName + "_" + tbName;
    this.index = new BPlusTree<>();
    this.entryIndex = new HashMap<Entry, Integer>();

    /**
     * 实现LRU算法 策略1：HashMap+timeStamp，遍历查找timestamp最早的替换 优点：粒度小，自定义空间大 缺点：操作量大，效率低；线程不安全
     * 策略2：HashMap+LinkedList，找到LinkedList最末尾的元素替换，手动维护LinkedList顺序 优点：省去timestamp和遍历
     * 缺点：操作页面时手动更新LinkedList顺序；线程不安全 策略3：LinkedHashMap，自动维护顺序，自动删除页面
     * 优点：策略2的javaSE实现，操作少，维护、删除由数据结构自动执行 缺点：可定义空间小，进行get操作时默认将访问页面移到尾部，超出数目自动删除，难以获取相关信息，
     * 因此在进行addPage等之前要首先对将移除的界面进行写回等操作；线程不安全
     */
    // 这里先采用策略3，后续有需要再更改策略
    this.pages =
        new LinkedHashMap<Integer, Page>(maxPageNum + 1, 1.0F, true) {
          @Override
          protected boolean removeEldestEntry(Map.Entry<Integer, Page> var) {
            return this.size() > maxPageNum;
          }
        };
    this.pageNum = 0;
  }

  /** --- GET methods --- */
  public int getPageNum() {
    return pageNum;
  }

  private int searchPage(Entry entry) {
    if (!entryIndex.containsKey(entry)) {
      return -1;
    }
    return entryIndex.get(entry);
  }

  public BPlusTreeIterator<Entry, Integer> getIndexIterator() {
    return index.iterator();
  }

  /** --- ROW methods --- */
  public Row getRow(Entry entry, int primaryKey) {
    int pid = searchPage(entry);
    if (pid == -1) {
      try {
        pid = index.get(entry);
      } catch (KeyNotExistException e) {
        throw new KeyNotExistException(entry.toString());
      }
      exchangePage(pid, primaryKey);
    }
    return pages.get(pid).getRow(entry);
  }

  public void insertRow(ArrayList<Entry> entries, int primaryKey) {
    Row row = new Row(entries);
    int len = row.toString().length();
    Entry primaryEntry = entries.get(primaryKey);
    Page curPage = pages.get(pageNum);
    if (curPage == null || curPage.getSize() + len > Page.maxSize) {
      addPage(primaryKey);
      curPage = pages.get(pageNum);
    }
    entryIndex.put(primaryEntry, pageNum);
    try {
      index.put(primaryEntry, pageNum);
    } catch (DuplicateKeyException e) {
      throw new DuplicateKeyException(primaryEntry.toString());
    }
    curPage.insertRow(primaryEntry, row);
    curPage.setEdited(true);
  }

  public void deleteRow(Entry entry, int primaryKey) {
    int pid = searchPage(entry);
    if (pid == -1) {
      try {
        pid = index.get(entry);
      } catch (KeyNotExistException e) {
        throw new KeyNotExistException(entry.toString());
      }
      exchangePage(pid, primaryKey);
    }
    Page curPage = pages.get(pid);

    entryIndex.remove(entry);
    index.remove(entry);
    curPage.removeRow(entry);
    curPage.setEdited(true);
  }

  public void updateRow(
      Entry primaryEntry, int primaryKey, int[] targetKeys, ArrayList<Entry> targetEntries) {
    Row row;
    int pid = searchPage(primaryEntry);
    if (pid == -1) {
      try {
        pid = index.get(primaryEntry);
      } catch (KeyNotExistException e) {
        throw new KeyNotExistException(primaryEntry.toString());
      }
      exchangePage(pid, primaryKey);
    }
    row = pages.get(pid).getRow(primaryEntry);

    // 检查是否更新了主键，如果是检查冲突
    boolean changePrimaryEntry = false;
    Entry targetPrimaryEntry = null;
    for (int i = 0; i < targetKeys.length; i++) {
      if (targetKeys[i] == primaryKey) {
        changePrimaryEntry = true;
        targetPrimaryEntry = targetEntries.get(i);
        // 检查冲突
        if (index.contains(targetPrimaryEntry) && !primaryEntry.equals(targetPrimaryEntry))
          throw new DuplicateKeyException(targetPrimaryEntry.toString());
        break;
      }
    }

    // 更新行内容
    for (int i = 0; i < targetKeys.length; i++) {
      int key = targetKeys[i];
      row.getEntries().set(key, targetEntries.get(i));
    }

    Page curPage = pages.get(pid);
    if (changePrimaryEntry) {
      curPage.removeRow(primaryEntry);
      entryIndex.remove(primaryEntry);
      curPage.insertRow(targetPrimaryEntry, row);
      entryIndex.put(targetPrimaryEntry, pid);
      index.remove(primaryEntry);
      try {
        index.put(targetPrimaryEntry, pid);
      } catch (DuplicateKeyException e) {
        throw new DuplicateKeyException(targetPrimaryEntry.toString());
      }
    }
    curPage.setEdited(true);
  }

  public void deleteAll(int primaryKey) {
    BPlusTreeIterator<Entry, Integer> iterator = getIndexIterator();
    while (iterator.hasNext()) {
      deleteRow(iterator.next().getLeft(), primaryKey);
    }
  }
  /** --- PAGE methods --- */
  private boolean addPage(int primaryKey) {
    boolean noOverflow = true;
    if (pageNum >= maxPageNum) {
      expelPage(primaryKey);
      noOverflow = false;
    }
    ++pageNum;
    Page page = new Page(cacheName, pageNum);
    pages.put(pageNum, page);
    return noOverflow;
  }

  private void expelPage(int primaryKey) {
    Map.Entry<Integer, Page> target = pages.entrySet().iterator().next();
    int targetId = target.getKey();
    Page targetPage = target.getValue();
    if (targetPage == null) {
      return;
    }

    ArrayList<Row> rows = targetPage.getRows();
    ArrayList<Entry> entries = new ArrayList<Entry>();
    Entry tmpEntry;
    for (Row row : rows) {
      tmpEntry = row.getEntry(primaryKey);
      entries.add(row.getEntry(primaryKey));
      index.update(tmpEntry, targetId);
      entryIndex.remove(tmpEntry);
    }
    if (targetPage.isEdited()) {
      try {
        serialize(rows, DATA_DIRECTORY + targetPage.getPageFileName());
      } catch (IOException e) {
        return;
      }
    }
    pages.remove(targetId);
  }

  private void exchangePage(int pid, int primaryKey) {
    if (pageNum >= maxPageNum) expelPage(primaryKey);

    Page curPage = new Page(cacheName, pid);
    ArrayList<Row> rows = deserialize(new File(DATA_DIRECTORY + curPage.getPageFileName()));
    for (Row row : rows) {
      Entry primaryEntry = row.getEntry(primaryKey);
      entryIndex.put(primaryEntry, pid);
      index.update(primaryEntry, pid);
      curPage.insertRow(primaryEntry, row);
    }
    pages.put(pid, curPage);
  }

  public boolean insertPage(ArrayList<Row> rows, int primaryKey) {
    boolean noOverflow = addPage(primaryKey);
    Page curPage = pages.get(pageNum);
    for (Row row : rows) {
      ArrayList<Entry> entries = row.getEntries();
      Entry primaryEntry = entries.get(primaryKey);
      curPage.insertRow(primaryEntry, row);
      entryIndex.put(primaryEntry, pageNum);
      index.put(primaryEntry, pageNum);
    }
    return noOverflow;
  }

  /** --- SERIALIZE methods --- */
  private void serialize(ArrayList<Row> rows, String filename) throws IOException {
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
    oos.writeObject(rows);
    oos.close();
  }

  private ArrayList<Row> deserialize(File file) {
    ArrayList<Row> rows;
    ObjectInputStream ois = null;
    try {
      ois = new ObjectInputStream(new FileInputStream(file));
      rows = (ArrayList<Row>) ois.readObject();
    } catch (Exception e) {
      rows = null;
    } finally {
      try {
        ois.close();
      } catch (Exception e) {
        return null;
      }
    }
    return rows;
  }

  public void persist() {
    for (Page page : pages.values()) {
      ArrayList<Row> rows = page.getRows();

      try {
        serialize(rows, DATA_DIRECTORY + page.getPageFileName());
      } catch (IOException e) {
        return;
      }
    }
  }

  public void recover(File file, int primaryKey) {
    ArrayList<Row> rows = deserialize(file);
    if (rows == null) {
      return;
    }
    for (Row r : rows) {
      insertRow(r.getEntries(), primaryKey);
    }
  }
}
