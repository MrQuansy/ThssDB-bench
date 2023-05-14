package cn.edu.thssdb.parser;

import cn.edu.thssdb.exception.ParseErrorException;
import org.antlr.v4.runtime.*;

import java.util.Collections;
import java.util.List;

// http://www.voidcn.com/article/p-yjkstlnk-bod.html
public class ErrorListener extends BaseErrorListener {
  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e) {
    List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
    Collections.reverse(stack);
    System.err.println("rule stack: " + stack);
    System.err.println(
        "line " + line + ":" + charPositionInLine + " at " + offendingSymbol + ": " + msg);
    throw new ParseErrorException("line " + line + ":" + charPositionInLine + ": " + msg);
  }
}
