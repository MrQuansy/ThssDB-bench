package cn.edu.thssdb.cache;

import cn.edu.thssdb.schema.Entry;
import cn.edu.thssdb.schema.Row;

import java.util.ArrayList;
import java.util.HashMap;

public class Page {
    public static final int maxSize = 2048;
    private String pageFileName;    //filename on disk
    private int pid;                //page unique id in cache
    private int size;               //size of entries
    private boolean edited;         //edited since inserted into cache

    private HashMap<Entry, Row> rows;


    public Page(String pageFileName, int pid)
    {
        this.pageFileName=pageFileName;
        this.pid=pid;
        size = 0;
        edited = false;
        rows = new HashMap<Entry, Row>();
    }

    /**--- GET methods ---*/
    public String getPageFileName() { return pageFileName; }
    public int getId() { return pid; }
    public int getSize() { return size; }
    public boolean isEdited() { return edited; }
    public ArrayList<Entry> getEntries() { return new ArrayList<Entry>(rows.keySet());}
    public ArrayList<Row> getRows() { return new ArrayList<Row>(rows.values()); }
    public Row getRow(Entry entry) { return rows.get(entry); }

    /**--- SET methods ---*/
    public void setEdited(boolean new_edited) { this.edited=new_edited; }

    /**--- ENTRY methods ---*/
    /*将每条entry都存入cache*/
    public void insertRow(Entry entry, Row row)
    {
        this.size += row.toString().length();
        this.rows.put(entry, row);
    }

    public void removeRow(Entry entry)
    {
        Row row = this.rows.get(entry);
        this.size -= row.toString().length();
        this.rows.remove(entry);
    }
}
