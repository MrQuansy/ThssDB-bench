package cn.edu.thssdb.parser;

import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.schema.Manager;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLHandler {
  Manager manager;
  private final String[] wal = {"insert", "delete", "update", "begin", "commit"};

  public SQLHandler(Manager manager) {
    this.manager = manager;
  }

  public ArrayList<QueryResult> handle(String s, long sessionId) {
    ArrayList<QueryResult> results = new ArrayList<>();
    ErrorListener errorListener = new ErrorListener();
    try {
      // 词法分析
      SQLLexer lexer = new SQLLexer(CharStreams.fromString(s));
      lexer.removeErrorListeners();
      lexer.addErrorListener(errorListener); // 添加错误提示
      CommonTokenStream tokens = new CommonTokenStream(lexer); // 转成token流
      // 语法分析
      SQLParser parser = new SQLParser(tokens);
      parser.removeErrorListeners();
      parser.addErrorListener(errorListener);
      // 语义分析
      SimpleSQLVisitor simpleSQLVisitor = new SimpleSQLVisitor(manager, sessionId);
      ArrayList<QueryResult> queryResults = simpleSQLVisitor.visitParse(parser.parse());
      if (queryResults.size() == 1) {
        queryResults.get(0).setSql(s);
      }
      String cmd = s.split("\\s+")[0];
      if (Arrays.asList(wal).contains(cmd.toLowerCase()) && !queryResults.get(0).isWrong()) {
        manager.write_log(s);
      }
      results.addAll(queryResults);
      System.out.println("out");
      return results;
    } catch (Exception e) {
      System.out.println("parser error");
      System.out.println(e.getMessage());
      QueryResult queryResult = new QueryResult(e.getMessage(), false);
      results.add(queryResult);
      return results;
    }
  }
}
