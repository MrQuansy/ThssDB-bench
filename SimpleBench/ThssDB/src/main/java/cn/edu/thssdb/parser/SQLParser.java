// Generated from D:/code/dataBase/bigwork/parser/ThssDB/src/main/java/cn/edu/thssdb/parser\SQL.g4
// by ANTLR 4.9.1
package cn.edu.thssdb.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLParser extends Parser {
  static {
    RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int T__0 = 1,
      T__1 = 2,
      T__2 = 3,
      T__3 = 4,
      T__4 = 5,
      EQ = 6,
      NE = 7,
      LT = 8,
      GT = 9,
      LE = 10,
      GE = 11,
      ADD = 12,
      SUB = 13,
      MUL = 14,
      DIV = 15,
      AND = 16,
      OR = 17,
      T_INT = 18,
      T_LONG = 19,
      T_FLOAT = 20,
      T_DOUBLE = 21,
      T_STRING = 22,
      K_ADD = 23,
      K_ALL = 24,
      K_AS = 25,
      K_BY = 26,
      K_BEGIN = 27,
      K_COLUMN = 28,
      K_COMMIT = 29,
      K_CREATE = 30,
      K_DATABASE = 31,
      K_DATABASES = 32,
      K_DELETE = 33,
      K_DISTINCT = 34,
      K_DROP = 35,
      K_EXISTS = 36,
      K_FROM = 37,
      K_GRANT = 38,
      K_IF = 39,
      K_IDENTIFIED = 40,
      K_INSERT = 41,
      K_INTO = 42,
      K_JOIN = 43,
      K_KEY = 44,
      K_NOT = 45,
      K_NULL = 46,
      K_ON = 47,
      K_PRIMARY = 48,
      K_QUIT = 49,
      K_REVOKE = 50,
      K_SELECT = 51,
      K_SET = 52,
      K_SHOW = 53,
      K_TABLE = 54,
      K_TABLES = 55,
      K_TO = 56,
      K_TRANSACTION = 57,
      K_UPDATE = 58,
      K_USE = 59,
      K_USER = 60,
      K_VALUES = 61,
      K_VIEW = 62,
      K_WHERE = 63,
      IDENTIFIER = 64,
      NUMERIC_LITERAL = 65,
      EXPONENT = 66,
      STRING_LITERAL = 67,
      SINGLE_LINE_COMMENT = 68,
      MULTILINE_COMMENT = 69,
      SPACES = 70;
  public static final int RULE_parse = 0,
      RULE_sql_stmt_list = 1,
      RULE_sql_stmt = 2,
      RULE_begin_transaction_stmt = 3,
      RULE_commit_stmt = 4,
      RULE_create_db_stmt = 5,
      RULE_drop_db_stmt = 6,
      RULE_create_user_stmt = 7,
      RULE_drop_user_stmt = 8,
      RULE_create_table_stmt = 9,
      RULE_show_meta_stmt = 10,
      RULE_grant_stmt = 11,
      RULE_revoke_stmt = 12,
      RULE_use_db_stmt = 13,
      RULE_delete_stmt = 14,
      RULE_drop_table_stmt = 15,
      RULE_show_db_stmt = 16,
      RULE_quit_stmt = 17,
      RULE_show_table_stmt = 18,
      RULE_insert_stmt = 19,
      RULE_value_entry = 20,
      RULE_select_stmt = 21,
      RULE_create_view_stmt = 22,
      RULE_drop_view_stmt = 23,
      RULE_update_stmt = 24,
      RULE_column_def = 25,
      RULE_type_name = 26,
      RULE_column_constraint = 27,
      RULE_multiple_condition = 28,
      RULE_condition = 29,
      RULE_comparer = 30,
      RULE_comparator = 31,
      RULE_expression = 32,
      RULE_table_constraint = 33,
      RULE_result_column = 34,
      RULE_table_query = 35,
      RULE_auth_level = 36,
      RULE_literal_value = 37,
      RULE_column_full_name = 38,
      RULE_database_name = 39,
      RULE_table_name = 40,
      RULE_user_name = 41,
      RULE_column_name = 42,
      RULE_view_name = 43,
      RULE_password = 44;

  private static String[] makeRuleNames() {
    return new String[] {
      "parse",
      "sql_stmt_list",
      "sql_stmt",
      "begin_transaction_stmt",
      "commit_stmt",
      "create_db_stmt",
      "drop_db_stmt",
      "create_user_stmt",
      "drop_user_stmt",
      "create_table_stmt",
      "show_meta_stmt",
      "grant_stmt",
      "revoke_stmt",
      "use_db_stmt",
      "delete_stmt",
      "drop_table_stmt",
      "show_db_stmt",
      "quit_stmt",
      "show_table_stmt",
      "insert_stmt",
      "value_entry",
      "select_stmt",
      "create_view_stmt",
      "drop_view_stmt",
      "update_stmt",
      "column_def",
      "type_name",
      "column_constraint",
      "multiple_condition",
      "condition",
      "comparer",
      "comparator",
      "expression",
      "table_constraint",
      "result_column",
      "table_query",
      "auth_level",
      "literal_value",
      "column_full_name",
      "database_name",
      "table_name",
      "user_name",
      "column_name",
      "view_name",
      "password"
    };
  }

  public static final String[] ruleNames = makeRuleNames();

  private static String[] makeLiteralNames() {
    return new String[] {
      null, "';'", "'('", "','", "')'", "'.'", "'='", "'<>'", "'<'", "'>'", "'<='", "'>='", "'+'",
      "'-'", "'*'", "'/'", "'&&'", "'||'"
    };
  }

  private static final String[] _LITERAL_NAMES = makeLiteralNames();

  private static String[] makeSymbolicNames() {
    return new String[] {
      null,
      null,
      null,
      null,
      null,
      null,
      "EQ",
      "NE",
      "LT",
      "GT",
      "LE",
      "GE",
      "ADD",
      "SUB",
      "MUL",
      "DIV",
      "AND",
      "OR",
      "T_INT",
      "T_LONG",
      "T_FLOAT",
      "T_DOUBLE",
      "T_STRING",
      "K_ADD",
      "K_ALL",
      "K_AS",
      "K_BY",
      "K_BEGIN",
      "K_COLUMN",
      "K_COMMIT",
      "K_CREATE",
      "K_DATABASE",
      "K_DATABASES",
      "K_DELETE",
      "K_DISTINCT",
      "K_DROP",
      "K_EXISTS",
      "K_FROM",
      "K_GRANT",
      "K_IF",
      "K_IDENTIFIED",
      "K_INSERT",
      "K_INTO",
      "K_JOIN",
      "K_KEY",
      "K_NOT",
      "K_NULL",
      "K_ON",
      "K_PRIMARY",
      "K_QUIT",
      "K_REVOKE",
      "K_SELECT",
      "K_SET",
      "K_SHOW",
      "K_TABLE",
      "K_TABLES",
      "K_TO",
      "K_TRANSACTION",
      "K_UPDATE",
      "K_USE",
      "K_USER",
      "K_VALUES",
      "K_VIEW",
      "K_WHERE",
      "IDENTIFIER",
      "NUMERIC_LITERAL",
      "EXPONENT",
      "STRING_LITERAL",
      "SINGLE_LINE_COMMENT",
      "MULTILINE_COMMENT",
      "SPACES"
    };
  }

  private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated public static final String[] tokenNames;

  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override
  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  @Override
  public String getGrammarFileName() {
    return "SQL.g4";
  }

  @Override
  public String[] getRuleNames() {
    return ruleNames;
  }

  @Override
  public String getSerializedATN() {
    return _serializedATN;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public SQLParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  public static class ParseContext extends ParserRuleContext {
    public Sql_stmt_listContext sql_stmt_list() {
      return getRuleContext(Sql_stmt_listContext.class, 0);
    }

    public ParseContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_parse;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterParse(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitParse(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitParse(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ParseContext parse() throws RecognitionException {
    ParseContext _localctx = new ParseContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_parse);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(90);
        sql_stmt_list();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Sql_stmt_listContext extends ParserRuleContext {
    public List<Sql_stmtContext> sql_stmt() {
      return getRuleContexts(Sql_stmtContext.class);
    }

    public Sql_stmtContext sql_stmt(int i) {
      return getRuleContext(Sql_stmtContext.class, i);
    }

    public Sql_stmt_listContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_sql_stmt_list;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterSql_stmt_list(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitSql_stmt_list(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitSql_stmt_list(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Sql_stmt_listContext sql_stmt_list() throws RecognitionException {
    Sql_stmt_listContext _localctx = new Sql_stmt_listContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_sql_stmt_list);
    int _la;
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(95);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__0) {
          {
            {
              setState(92);
              match(T__0);
            }
          }
          setState(97);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(98);
        sql_stmt();
        setState(107);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            {
              {
                setState(100);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                  {
                    {
                      setState(99);
                      match(T__0);
                    }
                  }
                  setState(102);
                  _errHandler.sync(this);
                  _la = _input.LA(1);
                } while (_la == T__0);
                setState(104);
                sql_stmt();
              }
            }
          }
          setState(109);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
        }
        setState(113);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__0) {
          {
            {
              setState(110);
              match(T__0);
            }
          }
          setState(115);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Sql_stmtContext extends ParserRuleContext {
    public Begin_transaction_stmtContext begin_transaction_stmt() {
      return getRuleContext(Begin_transaction_stmtContext.class, 0);
    }

    public Commit_stmtContext commit_stmt() {
      return getRuleContext(Commit_stmtContext.class, 0);
    }

    public Create_table_stmtContext create_table_stmt() {
      return getRuleContext(Create_table_stmtContext.class, 0);
    }

    public Create_db_stmtContext create_db_stmt() {
      return getRuleContext(Create_db_stmtContext.class, 0);
    }

    public Create_user_stmtContext create_user_stmt() {
      return getRuleContext(Create_user_stmtContext.class, 0);
    }

    public Drop_db_stmtContext drop_db_stmt() {
      return getRuleContext(Drop_db_stmtContext.class, 0);
    }

    public Drop_user_stmtContext drop_user_stmt() {
      return getRuleContext(Drop_user_stmtContext.class, 0);
    }

    public Delete_stmtContext delete_stmt() {
      return getRuleContext(Delete_stmtContext.class, 0);
    }

    public Drop_table_stmtContext drop_table_stmt() {
      return getRuleContext(Drop_table_stmtContext.class, 0);
    }

    public Insert_stmtContext insert_stmt() {
      return getRuleContext(Insert_stmtContext.class, 0);
    }

    public Select_stmtContext select_stmt() {
      return getRuleContext(Select_stmtContext.class, 0);
    }

    public Create_view_stmtContext create_view_stmt() {
      return getRuleContext(Create_view_stmtContext.class, 0);
    }

    public Drop_view_stmtContext drop_view_stmt() {
      return getRuleContext(Drop_view_stmtContext.class, 0);
    }

    public Grant_stmtContext grant_stmt() {
      return getRuleContext(Grant_stmtContext.class, 0);
    }

    public Revoke_stmtContext revoke_stmt() {
      return getRuleContext(Revoke_stmtContext.class, 0);
    }

    public Use_db_stmtContext use_db_stmt() {
      return getRuleContext(Use_db_stmtContext.class, 0);
    }

    public Show_db_stmtContext show_db_stmt() {
      return getRuleContext(Show_db_stmtContext.class, 0);
    }

    public Show_table_stmtContext show_table_stmt() {
      return getRuleContext(Show_table_stmtContext.class, 0);
    }

    public Show_meta_stmtContext show_meta_stmt() {
      return getRuleContext(Show_meta_stmtContext.class, 0);
    }

    public Quit_stmtContext quit_stmt() {
      return getRuleContext(Quit_stmtContext.class, 0);
    }

    public Update_stmtContext update_stmt() {
      return getRuleContext(Update_stmtContext.class, 0);
    }

    public Sql_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_sql_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterSql_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitSql_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitSql_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Sql_stmtContext sql_stmt() throws RecognitionException {
    Sql_stmtContext _localctx = new Sql_stmtContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_sql_stmt);
    try {
      setState(137);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(116);
            begin_transaction_stmt();
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(117);
            commit_stmt();
          }
          break;
        case 3:
          enterOuterAlt(_localctx, 3);
          {
            setState(118);
            create_table_stmt();
          }
          break;
        case 4:
          enterOuterAlt(_localctx, 4);
          {
            setState(119);
            create_db_stmt();
          }
          break;
        case 5:
          enterOuterAlt(_localctx, 5);
          {
            setState(120);
            create_user_stmt();
          }
          break;
        case 6:
          enterOuterAlt(_localctx, 6);
          {
            setState(121);
            drop_db_stmt();
          }
          break;
        case 7:
          enterOuterAlt(_localctx, 7);
          {
            setState(122);
            drop_user_stmt();
          }
          break;
        case 8:
          enterOuterAlt(_localctx, 8);
          {
            setState(123);
            delete_stmt();
          }
          break;
        case 9:
          enterOuterAlt(_localctx, 9);
          {
            setState(124);
            drop_table_stmt();
          }
          break;
        case 10:
          enterOuterAlt(_localctx, 10);
          {
            setState(125);
            insert_stmt();
          }
          break;
        case 11:
          enterOuterAlt(_localctx, 11);
          {
            setState(126);
            select_stmt();
          }
          break;
        case 12:
          enterOuterAlt(_localctx, 12);
          {
            setState(127);
            create_view_stmt();
          }
          break;
        case 13:
          enterOuterAlt(_localctx, 13);
          {
            setState(128);
            drop_view_stmt();
          }
          break;
        case 14:
          enterOuterAlt(_localctx, 14);
          {
            setState(129);
            grant_stmt();
          }
          break;
        case 15:
          enterOuterAlt(_localctx, 15);
          {
            setState(130);
            revoke_stmt();
          }
          break;
        case 16:
          enterOuterAlt(_localctx, 16);
          {
            setState(131);
            use_db_stmt();
          }
          break;
        case 17:
          enterOuterAlt(_localctx, 17);
          {
            setState(132);
            show_db_stmt();
          }
          break;
        case 18:
          enterOuterAlt(_localctx, 18);
          {
            setState(133);
            show_table_stmt();
          }
          break;
        case 19:
          enterOuterAlt(_localctx, 19);
          {
            setState(134);
            show_meta_stmt();
          }
          break;
        case 20:
          enterOuterAlt(_localctx, 20);
          {
            setState(135);
            quit_stmt();
          }
          break;
        case 21:
          enterOuterAlt(_localctx, 21);
          {
            setState(136);
            update_stmt();
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Begin_transaction_stmtContext extends ParserRuleContext {
    public TerminalNode K_BEGIN() {
      return getToken(SQLParser.K_BEGIN, 0);
    }

    public TerminalNode K_TRANSACTION() {
      return getToken(SQLParser.K_TRANSACTION, 0);
    }

    public Begin_transaction_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_begin_transaction_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener)
        ((SQLListener) listener).enterBegin_transaction_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener)
        ((SQLListener) listener).exitBegin_transaction_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitBegin_transaction_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Begin_transaction_stmtContext begin_transaction_stmt() throws RecognitionException {
    Begin_transaction_stmtContext _localctx = new Begin_transaction_stmtContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_begin_transaction_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(139);
        match(K_BEGIN);
        setState(140);
        match(K_TRANSACTION);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Commit_stmtContext extends ParserRuleContext {
    public TerminalNode K_COMMIT() {
      return getToken(SQLParser.K_COMMIT, 0);
    }

    public Commit_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_commit_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterCommit_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitCommit_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitCommit_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Commit_stmtContext commit_stmt() throws RecognitionException {
    Commit_stmtContext _localctx = new Commit_stmtContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_commit_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(142);
        match(K_COMMIT);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Create_db_stmtContext extends ParserRuleContext {
    public TerminalNode K_CREATE() {
      return getToken(SQLParser.K_CREATE, 0);
    }

    public TerminalNode K_DATABASE() {
      return getToken(SQLParser.K_DATABASE, 0);
    }

    public Database_nameContext database_name() {
      return getRuleContext(Database_nameContext.class, 0);
    }

    public Create_db_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_create_db_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterCreate_db_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitCreate_db_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitCreate_db_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Create_db_stmtContext create_db_stmt() throws RecognitionException {
    Create_db_stmtContext _localctx = new Create_db_stmtContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_create_db_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(144);
        match(K_CREATE);
        setState(145);
        match(K_DATABASE);
        setState(146);
        database_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Drop_db_stmtContext extends ParserRuleContext {
    public TerminalNode K_DROP() {
      return getToken(SQLParser.K_DROP, 0);
    }

    public TerminalNode K_DATABASE() {
      return getToken(SQLParser.K_DATABASE, 0);
    }

    public Database_nameContext database_name() {
      return getRuleContext(Database_nameContext.class, 0);
    }

    public TerminalNode K_IF() {
      return getToken(SQLParser.K_IF, 0);
    }

    public TerminalNode K_EXISTS() {
      return getToken(SQLParser.K_EXISTS, 0);
    }

    public Drop_db_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_drop_db_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterDrop_db_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitDrop_db_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitDrop_db_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Drop_db_stmtContext drop_db_stmt() throws RecognitionException {
    Drop_db_stmtContext _localctx = new Drop_db_stmtContext(_ctx, getState());
    enterRule(_localctx, 12, RULE_drop_db_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(148);
        match(K_DROP);
        setState(149);
        match(K_DATABASE);
        setState(152);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_IF) {
          {
            setState(150);
            match(K_IF);
            setState(151);
            match(K_EXISTS);
          }
        }

        setState(154);
        database_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Create_user_stmtContext extends ParserRuleContext {
    public TerminalNode K_CREATE() {
      return getToken(SQLParser.K_CREATE, 0);
    }

    public TerminalNode K_USER() {
      return getToken(SQLParser.K_USER, 0);
    }

    public User_nameContext user_name() {
      return getRuleContext(User_nameContext.class, 0);
    }

    public TerminalNode K_IDENTIFIED() {
      return getToken(SQLParser.K_IDENTIFIED, 0);
    }

    public TerminalNode K_BY() {
      return getToken(SQLParser.K_BY, 0);
    }

    public PasswordContext password() {
      return getRuleContext(PasswordContext.class, 0);
    }

    public Create_user_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_create_user_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterCreate_user_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitCreate_user_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitCreate_user_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Create_user_stmtContext create_user_stmt() throws RecognitionException {
    Create_user_stmtContext _localctx = new Create_user_stmtContext(_ctx, getState());
    enterRule(_localctx, 14, RULE_create_user_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(156);
        match(K_CREATE);
        setState(157);
        match(K_USER);
        setState(158);
        user_name();
        setState(159);
        match(K_IDENTIFIED);
        setState(160);
        match(K_BY);
        setState(161);
        password();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Drop_user_stmtContext extends ParserRuleContext {
    public TerminalNode K_DROP() {
      return getToken(SQLParser.K_DROP, 0);
    }

    public TerminalNode K_USER() {
      return getToken(SQLParser.K_USER, 0);
    }

    public User_nameContext user_name() {
      return getRuleContext(User_nameContext.class, 0);
    }

    public TerminalNode K_IF() {
      return getToken(SQLParser.K_IF, 0);
    }

    public TerminalNode K_EXISTS() {
      return getToken(SQLParser.K_EXISTS, 0);
    }

    public Drop_user_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_drop_user_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterDrop_user_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitDrop_user_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitDrop_user_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Drop_user_stmtContext drop_user_stmt() throws RecognitionException {
    Drop_user_stmtContext _localctx = new Drop_user_stmtContext(_ctx, getState());
    enterRule(_localctx, 16, RULE_drop_user_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(163);
        match(K_DROP);
        setState(164);
        match(K_USER);
        setState(167);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_IF) {
          {
            setState(165);
            match(K_IF);
            setState(166);
            match(K_EXISTS);
          }
        }

        setState(169);
        user_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Create_table_stmtContext extends ParserRuleContext {
    public TerminalNode K_CREATE() {
      return getToken(SQLParser.K_CREATE, 0);
    }

    public TerminalNode K_TABLE() {
      return getToken(SQLParser.K_TABLE, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public List<Column_defContext> column_def() {
      return getRuleContexts(Column_defContext.class);
    }

    public Column_defContext column_def(int i) {
      return getRuleContext(Column_defContext.class, i);
    }

    public Table_constraintContext table_constraint() {
      return getRuleContext(Table_constraintContext.class, 0);
    }

    public Create_table_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_create_table_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterCreate_table_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitCreate_table_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitCreate_table_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Create_table_stmtContext create_table_stmt() throws RecognitionException {
    Create_table_stmtContext _localctx = new Create_table_stmtContext(_ctx, getState());
    enterRule(_localctx, 18, RULE_create_table_stmt);
    int _la;
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(171);
        match(K_CREATE);
        setState(172);
        match(K_TABLE);
        setState(173);
        table_name();
        setState(174);
        match(T__1);
        setState(175);
        column_def();
        setState(180);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            {
              {
                setState(176);
                match(T__2);
                setState(177);
                column_def();
              }
            }
          }
          setState(182);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
        }
        setState(185);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__2) {
          {
            setState(183);
            match(T__2);
            setState(184);
            table_constraint();
          }
        }

        setState(187);
        match(T__3);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Show_meta_stmtContext extends ParserRuleContext {
    public TerminalNode K_SHOW() {
      return getToken(SQLParser.K_SHOW, 0);
    }

    public TerminalNode K_TABLE() {
      return getToken(SQLParser.K_TABLE, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public Show_meta_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_show_meta_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterShow_meta_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitShow_meta_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitShow_meta_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Show_meta_stmtContext show_meta_stmt() throws RecognitionException {
    Show_meta_stmtContext _localctx = new Show_meta_stmtContext(_ctx, getState());
    enterRule(_localctx, 20, RULE_show_meta_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(189);
        match(K_SHOW);
        setState(190);
        match(K_TABLE);
        setState(191);
        table_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Grant_stmtContext extends ParserRuleContext {
    public TerminalNode K_GRANT() {
      return getToken(SQLParser.K_GRANT, 0);
    }

    public List<Auth_levelContext> auth_level() {
      return getRuleContexts(Auth_levelContext.class);
    }

    public Auth_levelContext auth_level(int i) {
      return getRuleContext(Auth_levelContext.class, i);
    }

    public TerminalNode K_ON() {
      return getToken(SQLParser.K_ON, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public TerminalNode K_TO() {
      return getToken(SQLParser.K_TO, 0);
    }

    public User_nameContext user_name() {
      return getRuleContext(User_nameContext.class, 0);
    }

    public Grant_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_grant_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterGrant_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitGrant_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitGrant_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Grant_stmtContext grant_stmt() throws RecognitionException {
    Grant_stmtContext _localctx = new Grant_stmtContext(_ctx, getState());
    enterRule(_localctx, 22, RULE_grant_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(193);
        match(K_GRANT);
        setState(194);
        auth_level();
        setState(199);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__2) {
          {
            {
              setState(195);
              match(T__2);
              setState(196);
              auth_level();
            }
          }
          setState(201);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(202);
        match(K_ON);
        setState(203);
        table_name();
        setState(204);
        match(K_TO);
        setState(205);
        user_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Revoke_stmtContext extends ParserRuleContext {
    public TerminalNode K_REVOKE() {
      return getToken(SQLParser.K_REVOKE, 0);
    }

    public List<Auth_levelContext> auth_level() {
      return getRuleContexts(Auth_levelContext.class);
    }

    public Auth_levelContext auth_level(int i) {
      return getRuleContext(Auth_levelContext.class, i);
    }

    public TerminalNode K_ON() {
      return getToken(SQLParser.K_ON, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public TerminalNode K_FROM() {
      return getToken(SQLParser.K_FROM, 0);
    }

    public User_nameContext user_name() {
      return getRuleContext(User_nameContext.class, 0);
    }

    public Revoke_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_revoke_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterRevoke_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitRevoke_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitRevoke_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Revoke_stmtContext revoke_stmt() throws RecognitionException {
    Revoke_stmtContext _localctx = new Revoke_stmtContext(_ctx, getState());
    enterRule(_localctx, 24, RULE_revoke_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(207);
        match(K_REVOKE);
        setState(208);
        auth_level();
        setState(213);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__2) {
          {
            {
              setState(209);
              match(T__2);
              setState(210);
              auth_level();
            }
          }
          setState(215);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(216);
        match(K_ON);
        setState(217);
        table_name();
        setState(218);
        match(K_FROM);
        setState(219);
        user_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Use_db_stmtContext extends ParserRuleContext {
    public TerminalNode K_USE() {
      return getToken(SQLParser.K_USE, 0);
    }

    public Database_nameContext database_name() {
      return getRuleContext(Database_nameContext.class, 0);
    }

    public Use_db_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_use_db_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterUse_db_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitUse_db_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitUse_db_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Use_db_stmtContext use_db_stmt() throws RecognitionException {
    Use_db_stmtContext _localctx = new Use_db_stmtContext(_ctx, getState());
    enterRule(_localctx, 26, RULE_use_db_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(221);
        match(K_USE);
        setState(222);
        database_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Delete_stmtContext extends ParserRuleContext {
    public TerminalNode K_DELETE() {
      return getToken(SQLParser.K_DELETE, 0);
    }

    public TerminalNode K_FROM() {
      return getToken(SQLParser.K_FROM, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public TerminalNode K_WHERE() {
      return getToken(SQLParser.K_WHERE, 0);
    }

    public Multiple_conditionContext multiple_condition() {
      return getRuleContext(Multiple_conditionContext.class, 0);
    }

    public Delete_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_delete_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterDelete_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitDelete_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitDelete_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Delete_stmtContext delete_stmt() throws RecognitionException {
    Delete_stmtContext _localctx = new Delete_stmtContext(_ctx, getState());
    enterRule(_localctx, 28, RULE_delete_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(224);
        match(K_DELETE);
        setState(225);
        match(K_FROM);
        setState(226);
        table_name();
        setState(229);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_WHERE) {
          {
            setState(227);
            match(K_WHERE);
            setState(228);
            multiple_condition(0);
          }
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Drop_table_stmtContext extends ParserRuleContext {
    public TerminalNode K_DROP() {
      return getToken(SQLParser.K_DROP, 0);
    }

    public TerminalNode K_TABLE() {
      return getToken(SQLParser.K_TABLE, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public TerminalNode K_IF() {
      return getToken(SQLParser.K_IF, 0);
    }

    public TerminalNode K_EXISTS() {
      return getToken(SQLParser.K_EXISTS, 0);
    }

    public Drop_table_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_drop_table_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterDrop_table_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitDrop_table_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitDrop_table_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Drop_table_stmtContext drop_table_stmt() throws RecognitionException {
    Drop_table_stmtContext _localctx = new Drop_table_stmtContext(_ctx, getState());
    enterRule(_localctx, 30, RULE_drop_table_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(231);
        match(K_DROP);
        setState(232);
        match(K_TABLE);
        setState(235);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_IF) {
          {
            setState(233);
            match(K_IF);
            setState(234);
            match(K_EXISTS);
          }
        }

        setState(237);
        table_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Show_db_stmtContext extends ParserRuleContext {
    public TerminalNode K_SHOW() {
      return getToken(SQLParser.K_SHOW, 0);
    }

    public TerminalNode K_DATABASES() {
      return getToken(SQLParser.K_DATABASES, 0);
    }

    public Show_db_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_show_db_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterShow_db_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitShow_db_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitShow_db_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Show_db_stmtContext show_db_stmt() throws RecognitionException {
    Show_db_stmtContext _localctx = new Show_db_stmtContext(_ctx, getState());
    enterRule(_localctx, 32, RULE_show_db_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(239);
        match(K_SHOW);
        setState(240);
        match(K_DATABASES);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Quit_stmtContext extends ParserRuleContext {
    public TerminalNode K_QUIT() {
      return getToken(SQLParser.K_QUIT, 0);
    }

    public Quit_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_quit_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterQuit_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitQuit_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitQuit_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Quit_stmtContext quit_stmt() throws RecognitionException {
    Quit_stmtContext _localctx = new Quit_stmtContext(_ctx, getState());
    enterRule(_localctx, 34, RULE_quit_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(242);
        match(K_QUIT);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Show_table_stmtContext extends ParserRuleContext {
    public TerminalNode K_SHOW() {
      return getToken(SQLParser.K_SHOW, 0);
    }

    public TerminalNode K_TABLES() {
      return getToken(SQLParser.K_TABLES, 0);
    }

    public Show_table_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_show_table_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterShow_table_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitShow_table_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitShow_table_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Show_table_stmtContext show_table_stmt() throws RecognitionException {
    Show_table_stmtContext _localctx = new Show_table_stmtContext(_ctx, getState());
    enterRule(_localctx, 36, RULE_show_table_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(244);
        match(K_SHOW);
        setState(245);
        match(K_TABLES);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Insert_stmtContext extends ParserRuleContext {
    public TerminalNode K_INSERT() {
      return getToken(SQLParser.K_INSERT, 0);
    }

    public TerminalNode K_INTO() {
      return getToken(SQLParser.K_INTO, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public TerminalNode K_VALUES() {
      return getToken(SQLParser.K_VALUES, 0);
    }

    public List<Value_entryContext> value_entry() {
      return getRuleContexts(Value_entryContext.class);
    }

    public Value_entryContext value_entry(int i) {
      return getRuleContext(Value_entryContext.class, i);
    }

    public List<Column_nameContext> column_name() {
      return getRuleContexts(Column_nameContext.class);
    }

    public Column_nameContext column_name(int i) {
      return getRuleContext(Column_nameContext.class, i);
    }

    public Insert_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_insert_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterInsert_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitInsert_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitInsert_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Insert_stmtContext insert_stmt() throws RecognitionException {
    Insert_stmtContext _localctx = new Insert_stmtContext(_ctx, getState());
    enterRule(_localctx, 38, RULE_insert_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(247);
        match(K_INSERT);
        setState(248);
        match(K_INTO);
        setState(249);
        table_name();
        setState(261);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__1) {
          {
            setState(250);
            match(T__1);
            setState(251);
            column_name();
            setState(256);
            _errHandler.sync(this);
            _la = _input.LA(1);
            while (_la == T__2) {
              {
                {
                  setState(252);
                  match(T__2);
                  setState(253);
                  column_name();
                }
              }
              setState(258);
              _errHandler.sync(this);
              _la = _input.LA(1);
            }
            setState(259);
            match(T__3);
          }
        }

        setState(263);
        match(K_VALUES);
        setState(264);
        value_entry();
        setState(269);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__2) {
          {
            {
              setState(265);
              match(T__2);
              setState(266);
              value_entry();
            }
          }
          setState(271);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Value_entryContext extends ParserRuleContext {
    public List<Literal_valueContext> literal_value() {
      return getRuleContexts(Literal_valueContext.class);
    }

    public Literal_valueContext literal_value(int i) {
      return getRuleContext(Literal_valueContext.class, i);
    }

    public Value_entryContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_value_entry;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterValue_entry(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitValue_entry(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitValue_entry(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Value_entryContext value_entry() throws RecognitionException {
    Value_entryContext _localctx = new Value_entryContext(_ctx, getState());
    enterRule(_localctx, 40, RULE_value_entry);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(272);
        match(T__1);
        setState(273);
        literal_value();
        setState(278);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__2) {
          {
            {
              setState(274);
              match(T__2);
              setState(275);
              literal_value();
            }
          }
          setState(280);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(281);
        match(T__3);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Select_stmtContext extends ParserRuleContext {
    public TerminalNode K_SELECT() {
      return getToken(SQLParser.K_SELECT, 0);
    }

    public List<Result_columnContext> result_column() {
      return getRuleContexts(Result_columnContext.class);
    }

    public Result_columnContext result_column(int i) {
      return getRuleContext(Result_columnContext.class, i);
    }

    public TerminalNode K_FROM() {
      return getToken(SQLParser.K_FROM, 0);
    }

    public List<Table_queryContext> table_query() {
      return getRuleContexts(Table_queryContext.class);
    }

    public Table_queryContext table_query(int i) {
      return getRuleContext(Table_queryContext.class, i);
    }

    public TerminalNode K_WHERE() {
      return getToken(SQLParser.K_WHERE, 0);
    }

    public Multiple_conditionContext multiple_condition() {
      return getRuleContext(Multiple_conditionContext.class, 0);
    }

    public TerminalNode K_DISTINCT() {
      return getToken(SQLParser.K_DISTINCT, 0);
    }

    public TerminalNode K_ALL() {
      return getToken(SQLParser.K_ALL, 0);
    }

    public Select_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_select_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterSelect_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitSelect_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitSelect_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Select_stmtContext select_stmt() throws RecognitionException {
    Select_stmtContext _localctx = new Select_stmtContext(_ctx, getState());
    enterRule(_localctx, 42, RULE_select_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(283);
        match(K_SELECT);
        setState(285);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_ALL || _la == K_DISTINCT) {
          {
            setState(284);
            _la = _input.LA(1);
            if (!(_la == K_ALL || _la == K_DISTINCT)) {
              _errHandler.recoverInline(this);
            } else {
              if (_input.LA(1) == Token.EOF) matchedEOF = true;
              _errHandler.reportMatch(this);
              consume();
            }
          }
        }

        setState(287);
        result_column();
        setState(292);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__2) {
          {
            {
              setState(288);
              match(T__2);
              setState(289);
              result_column();
            }
          }
          setState(294);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(295);
        match(K_FROM);
        setState(296);
        table_query();
        setState(301);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__2) {
          {
            {
              setState(297);
              match(T__2);
              setState(298);
              table_query();
            }
          }
          setState(303);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(306);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_WHERE) {
          {
            setState(304);
            match(K_WHERE);
            setState(305);
            multiple_condition(0);
          }
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Create_view_stmtContext extends ParserRuleContext {
    public TerminalNode K_CREATE() {
      return getToken(SQLParser.K_CREATE, 0);
    }

    public TerminalNode K_VIEW() {
      return getToken(SQLParser.K_VIEW, 0);
    }

    public View_nameContext view_name() {
      return getRuleContext(View_nameContext.class, 0);
    }

    public TerminalNode K_AS() {
      return getToken(SQLParser.K_AS, 0);
    }

    public Select_stmtContext select_stmt() {
      return getRuleContext(Select_stmtContext.class, 0);
    }

    public Create_view_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_create_view_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterCreate_view_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitCreate_view_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitCreate_view_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Create_view_stmtContext create_view_stmt() throws RecognitionException {
    Create_view_stmtContext _localctx = new Create_view_stmtContext(_ctx, getState());
    enterRule(_localctx, 44, RULE_create_view_stmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(308);
        match(K_CREATE);
        setState(309);
        match(K_VIEW);
        setState(310);
        view_name();
        setState(311);
        match(K_AS);
        setState(312);
        select_stmt();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Drop_view_stmtContext extends ParserRuleContext {
    public TerminalNode K_DROP() {
      return getToken(SQLParser.K_DROP, 0);
    }

    public TerminalNode K_VIEW() {
      return getToken(SQLParser.K_VIEW, 0);
    }

    public View_nameContext view_name() {
      return getRuleContext(View_nameContext.class, 0);
    }

    public TerminalNode K_IF() {
      return getToken(SQLParser.K_IF, 0);
    }

    public TerminalNode K_EXISTS() {
      return getToken(SQLParser.K_EXISTS, 0);
    }

    public Drop_view_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_drop_view_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterDrop_view_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitDrop_view_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitDrop_view_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Drop_view_stmtContext drop_view_stmt() throws RecognitionException {
    Drop_view_stmtContext _localctx = new Drop_view_stmtContext(_ctx, getState());
    enterRule(_localctx, 46, RULE_drop_view_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(314);
        match(K_DROP);
        setState(315);
        match(K_VIEW);
        setState(318);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_IF) {
          {
            setState(316);
            match(K_IF);
            setState(317);
            match(K_EXISTS);
          }
        }

        setState(320);
        view_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Update_stmtContext extends ParserRuleContext {
    public TerminalNode K_UPDATE() {
      return getToken(SQLParser.K_UPDATE, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public TerminalNode K_SET() {
      return getToken(SQLParser.K_SET, 0);
    }

    public Column_nameContext column_name() {
      return getRuleContext(Column_nameContext.class, 0);
    }

    public TerminalNode EQ() {
      return getToken(SQLParser.EQ, 0);
    }

    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class, 0);
    }

    public TerminalNode K_WHERE() {
      return getToken(SQLParser.K_WHERE, 0);
    }

    public Multiple_conditionContext multiple_condition() {
      return getRuleContext(Multiple_conditionContext.class, 0);
    }

    public Update_stmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_update_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterUpdate_stmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitUpdate_stmt(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitUpdate_stmt(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Update_stmtContext update_stmt() throws RecognitionException {
    Update_stmtContext _localctx = new Update_stmtContext(_ctx, getState());
    enterRule(_localctx, 48, RULE_update_stmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(322);
        match(K_UPDATE);
        setState(323);
        table_name();
        setState(324);
        match(K_SET);
        setState(325);
        column_name();
        setState(326);
        match(EQ);
        setState(327);
        expression(0);
        setState(330);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == K_WHERE) {
          {
            setState(328);
            match(K_WHERE);
            setState(329);
            multiple_condition(0);
          }
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Column_defContext extends ParserRuleContext {
    public Column_nameContext column_name() {
      return getRuleContext(Column_nameContext.class, 0);
    }

    public Type_nameContext type_name() {
      return getRuleContext(Type_nameContext.class, 0);
    }

    public List<Column_constraintContext> column_constraint() {
      return getRuleContexts(Column_constraintContext.class);
    }

    public Column_constraintContext column_constraint(int i) {
      return getRuleContext(Column_constraintContext.class, i);
    }

    public Column_defContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_column_def;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterColumn_def(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitColumn_def(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitColumn_def(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Column_defContext column_def() throws RecognitionException {
    Column_defContext _localctx = new Column_defContext(_ctx, getState());
    enterRule(_localctx, 50, RULE_column_def);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(332);
        column_name();
        setState(333);
        type_name();
        setState(337);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == K_NOT || _la == K_PRIMARY) {
          {
            {
              setState(334);
              column_constraint();
            }
          }
          setState(339);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Type_nameContext extends ParserRuleContext {
    public TerminalNode T_INT() {
      return getToken(SQLParser.T_INT, 0);
    }

    public TerminalNode T_LONG() {
      return getToken(SQLParser.T_LONG, 0);
    }

    public TerminalNode T_FLOAT() {
      return getToken(SQLParser.T_FLOAT, 0);
    }

    public TerminalNode T_DOUBLE() {
      return getToken(SQLParser.T_DOUBLE, 0);
    }

    public TerminalNode T_STRING() {
      return getToken(SQLParser.T_STRING, 0);
    }

    public TerminalNode NUMERIC_LITERAL() {
      return getToken(SQLParser.NUMERIC_LITERAL, 0);
    }

    public Type_nameContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_type_name;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterType_name(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitType_name(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitType_name(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Type_nameContext type_name() throws RecognitionException {
    Type_nameContext _localctx = new Type_nameContext(_ctx, getState());
    enterRule(_localctx, 52, RULE_type_name);
    try {
      setState(348);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
        case T_INT:
          enterOuterAlt(_localctx, 1);
          {
            setState(340);
            match(T_INT);
          }
          break;
        case T_LONG:
          enterOuterAlt(_localctx, 2);
          {
            setState(341);
            match(T_LONG);
          }
          break;
        case T_FLOAT:
          enterOuterAlt(_localctx, 3);
          {
            setState(342);
            match(T_FLOAT);
          }
          break;
        case T_DOUBLE:
          enterOuterAlt(_localctx, 4);
          {
            setState(343);
            match(T_DOUBLE);
          }
          break;
        case T_STRING:
          enterOuterAlt(_localctx, 5);
          {
            setState(344);
            match(T_STRING);
            setState(345);
            match(T__1);
            setState(346);
            match(NUMERIC_LITERAL);
            setState(347);
            match(T__3);
          }
          break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Column_constraintContext extends ParserRuleContext {
    public TerminalNode K_PRIMARY() {
      return getToken(SQLParser.K_PRIMARY, 0);
    }

    public TerminalNode K_KEY() {
      return getToken(SQLParser.K_KEY, 0);
    }

    public TerminalNode K_NOT() {
      return getToken(SQLParser.K_NOT, 0);
    }

    public TerminalNode K_NULL() {
      return getToken(SQLParser.K_NULL, 0);
    }

    public Column_constraintContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_column_constraint;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterColumn_constraint(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitColumn_constraint(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitColumn_constraint(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Column_constraintContext column_constraint() throws RecognitionException {
    Column_constraintContext _localctx = new Column_constraintContext(_ctx, getState());
    enterRule(_localctx, 54, RULE_column_constraint);
    try {
      setState(354);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
        case K_PRIMARY:
          enterOuterAlt(_localctx, 1);
          {
            setState(350);
            match(K_PRIMARY);
            setState(351);
            match(K_KEY);
          }
          break;
        case K_NOT:
          enterOuterAlt(_localctx, 2);
          {
            setState(352);
            match(K_NOT);
            setState(353);
            match(K_NULL);
          }
          break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Multiple_conditionContext extends ParserRuleContext {
    public ConditionContext condition() {
      return getRuleContext(ConditionContext.class, 0);
    }

    public List<Multiple_conditionContext> multiple_condition() {
      return getRuleContexts(Multiple_conditionContext.class);
    }

    public Multiple_conditionContext multiple_condition(int i) {
      return getRuleContext(Multiple_conditionContext.class, i);
    }

    public TerminalNode AND() {
      return getToken(SQLParser.AND, 0);
    }

    public TerminalNode OR() {
      return getToken(SQLParser.OR, 0);
    }

    public Multiple_conditionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_multiple_condition;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterMultiple_condition(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitMultiple_condition(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitMultiple_condition(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Multiple_conditionContext multiple_condition() throws RecognitionException {
    return multiple_condition(0);
  }

  private Multiple_conditionContext multiple_condition(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    Multiple_conditionContext _localctx = new Multiple_conditionContext(_ctx, _parentState);
    Multiple_conditionContext _prevctx = _localctx;
    int _startState = 56;
    enterRecursionRule(_localctx, 56, RULE_multiple_condition, _p);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        {
          setState(357);
          condition();
        }
        _ctx.stop = _input.LT(-1);
        setState(367);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null) triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(365);
              _errHandler.sync(this);
              switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
                case 1:
                  {
                    _localctx = new Multiple_conditionContext(_parentctx, _parentState);
                    pushNewRecursionContext(_localctx, _startState, RULE_multiple_condition);
                    setState(359);
                    if (!(precpred(_ctx, 2)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                    setState(360);
                    match(AND);
                    setState(361);
                    multiple_condition(3);
                  }
                  break;
                case 2:
                  {
                    _localctx = new Multiple_conditionContext(_parentctx, _parentState);
                    pushNewRecursionContext(_localctx, _startState, RULE_multiple_condition);
                    setState(362);
                    if (!(precpred(_ctx, 1)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                    setState(363);
                    match(OR);
                    setState(364);
                    multiple_condition(2);
                  }
                  break;
              }
            }
          }
          setState(369);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  public static class ConditionContext extends ParserRuleContext {
    public List<ExpressionContext> expression() {
      return getRuleContexts(ExpressionContext.class);
    }

    public ExpressionContext expression(int i) {
      return getRuleContext(ExpressionContext.class, i);
    }

    public ComparatorContext comparator() {
      return getRuleContext(ComparatorContext.class, 0);
    }

    public ConditionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_condition;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterCondition(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitCondition(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitCondition(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ConditionContext condition() throws RecognitionException {
    ConditionContext _localctx = new ConditionContext(_ctx, getState());
    enterRule(_localctx, 58, RULE_condition);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(370);
        expression(0);
        setState(371);
        comparator();
        setState(372);
        expression(0);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ComparerContext extends ParserRuleContext {
    public Column_full_nameContext column_full_name() {
      return getRuleContext(Column_full_nameContext.class, 0);
    }

    public Literal_valueContext literal_value() {
      return getRuleContext(Literal_valueContext.class, 0);
    }

    public ComparerContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_comparer;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterComparer(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitComparer(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitComparer(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ComparerContext comparer() throws RecognitionException {
    ComparerContext _localctx = new ComparerContext(_ctx, getState());
    enterRule(_localctx, 60, RULE_comparer);
    try {
      setState(376);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
        case IDENTIFIER:
          enterOuterAlt(_localctx, 1);
          {
            setState(374);
            column_full_name();
          }
          break;
        case K_NULL:
        case NUMERIC_LITERAL:
        case STRING_LITERAL:
          enterOuterAlt(_localctx, 2);
          {
            setState(375);
            literal_value();
          }
          break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ComparatorContext extends ParserRuleContext {
    public TerminalNode EQ() {
      return getToken(SQLParser.EQ, 0);
    }

    public TerminalNode NE() {
      return getToken(SQLParser.NE, 0);
    }

    public TerminalNode LE() {
      return getToken(SQLParser.LE, 0);
    }

    public TerminalNode GE() {
      return getToken(SQLParser.GE, 0);
    }

    public TerminalNode LT() {
      return getToken(SQLParser.LT, 0);
    }

    public TerminalNode GT() {
      return getToken(SQLParser.GT, 0);
    }

    public ComparatorContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_comparator;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterComparator(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitComparator(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitComparator(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ComparatorContext comparator() throws RecognitionException {
    ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
    enterRule(_localctx, 62, RULE_comparator);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(378);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0
            && ((1L << _la)
                    & ((1L << EQ) | (1L << NE) | (1L << LT) | (1L << GT) | (1L << LE) | (1L << GE)))
                != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ExpressionContext extends ParserRuleContext {
    public ComparerContext comparer() {
      return getRuleContext(ComparerContext.class, 0);
    }

    public List<ExpressionContext> expression() {
      return getRuleContexts(ExpressionContext.class);
    }

    public ExpressionContext expression(int i) {
      return getRuleContext(ExpressionContext.class, i);
    }

    public TerminalNode MUL() {
      return getToken(SQLParser.MUL, 0);
    }

    public TerminalNode DIV() {
      return getToken(SQLParser.DIV, 0);
    }

    public TerminalNode ADD() {
      return getToken(SQLParser.ADD, 0);
    }

    public TerminalNode SUB() {
      return getToken(SQLParser.SUB, 0);
    }

    public ExpressionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_expression;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterExpression(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitExpression(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitExpression(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ExpressionContext expression() throws RecognitionException {
    return expression(0);
  }

  private ExpressionContext expression(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
    ExpressionContext _prevctx = _localctx;
    int _startState = 64;
    enterRecursionRule(_localctx, 64, RULE_expression, _p);
    int _la;
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(386);
        _errHandler.sync(this);
        switch (_input.LA(1)) {
          case K_NULL:
          case IDENTIFIER:
          case NUMERIC_LITERAL:
          case STRING_LITERAL:
            {
              setState(381);
              comparer();
            }
            break;
          case T__1:
            {
              setState(382);
              match(T__1);
              setState(383);
              expression(0);
              setState(384);
              match(T__3);
            }
            break;
          default:
            throw new NoViableAltException(this);
        }
        _ctx.stop = _input.LT(-1);
        setState(396);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 31, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null) triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(394);
              _errHandler.sync(this);
              switch (getInterpreter().adaptivePredict(_input, 30, _ctx)) {
                case 1:
                  {
                    _localctx = new ExpressionContext(_parentctx, _parentState);
                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                    setState(388);
                    if (!(precpred(_ctx, 3)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                    setState(389);
                    _la = _input.LA(1);
                    if (!(_la == MUL || _la == DIV)) {
                      _errHandler.recoverInline(this);
                    } else {
                      if (_input.LA(1) == Token.EOF) matchedEOF = true;
                      _errHandler.reportMatch(this);
                      consume();
                    }
                    setState(390);
                    expression(4);
                  }
                  break;
                case 2:
                  {
                    _localctx = new ExpressionContext(_parentctx, _parentState);
                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                    setState(391);
                    if (!(precpred(_ctx, 2)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                    setState(392);
                    _la = _input.LA(1);
                    if (!(_la == ADD || _la == SUB)) {
                      _errHandler.recoverInline(this);
                    } else {
                      if (_input.LA(1) == Token.EOF) matchedEOF = true;
                      _errHandler.reportMatch(this);
                      consume();
                    }
                    setState(393);
                    expression(3);
                  }
                  break;
              }
            }
          }
          setState(398);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 31, _ctx);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  public static class Table_constraintContext extends ParserRuleContext {
    public TerminalNode K_PRIMARY() {
      return getToken(SQLParser.K_PRIMARY, 0);
    }

    public TerminalNode K_KEY() {
      return getToken(SQLParser.K_KEY, 0);
    }

    public List<Column_nameContext> column_name() {
      return getRuleContexts(Column_nameContext.class);
    }

    public Column_nameContext column_name(int i) {
      return getRuleContext(Column_nameContext.class, i);
    }

    public Table_constraintContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_table_constraint;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterTable_constraint(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitTable_constraint(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitTable_constraint(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Table_constraintContext table_constraint() throws RecognitionException {
    Table_constraintContext _localctx = new Table_constraintContext(_ctx, getState());
    enterRule(_localctx, 66, RULE_table_constraint);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(399);
        match(K_PRIMARY);
        setState(400);
        match(K_KEY);
        setState(401);
        match(T__1);
        setState(402);
        column_name();
        setState(407);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__2) {
          {
            {
              setState(403);
              match(T__2);
              setState(404);
              column_name();
            }
          }
          setState(409);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(410);
        match(T__3);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Result_columnContext extends ParserRuleContext {
    public TerminalNode MUL() {
      return getToken(SQLParser.MUL, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public Column_full_nameContext column_full_name() {
      return getRuleContext(Column_full_nameContext.class, 0);
    }

    public Result_columnContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_result_column;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterResult_column(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitResult_column(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitResult_column(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Result_columnContext result_column() throws RecognitionException {
    Result_columnContext _localctx = new Result_columnContext(_ctx, getState());
    enterRule(_localctx, 68, RULE_result_column);
    try {
      setState(418);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 33, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(412);
            match(MUL);
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(413);
            table_name();
            setState(414);
            match(T__4);
            setState(415);
            match(MUL);
          }
          break;
        case 3:
          enterOuterAlt(_localctx, 3);
          {
            setState(417);
            column_full_name();
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Table_queryContext extends ParserRuleContext {
    public List<Table_nameContext> table_name() {
      return getRuleContexts(Table_nameContext.class);
    }

    public Table_nameContext table_name(int i) {
      return getRuleContext(Table_nameContext.class, i);
    }

    public TerminalNode K_ON() {
      return getToken(SQLParser.K_ON, 0);
    }

    public Multiple_conditionContext multiple_condition() {
      return getRuleContext(Multiple_conditionContext.class, 0);
    }

    public List<TerminalNode> K_JOIN() {
      return getTokens(SQLParser.K_JOIN);
    }

    public TerminalNode K_JOIN(int i) {
      return getToken(SQLParser.K_JOIN, i);
    }

    public Table_queryContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_table_query;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterTable_query(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitTable_query(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitTable_query(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Table_queryContext table_query() throws RecognitionException {
    Table_queryContext _localctx = new Table_queryContext(_ctx, getState());
    enterRule(_localctx, 70, RULE_table_query);
    int _la;
    try {
      setState(431);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 35, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(420);
            table_name();
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(421);
            table_name();
            setState(424);
            _errHandler.sync(this);
            _la = _input.LA(1);
            do {
              {
                {
                  setState(422);
                  match(K_JOIN);
                  setState(423);
                  table_name();
                }
              }
              setState(426);
              _errHandler.sync(this);
              _la = _input.LA(1);
            } while (_la == K_JOIN);
            setState(428);
            match(K_ON);
            setState(429);
            multiple_condition(0);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Auth_levelContext extends ParserRuleContext {
    public TerminalNode K_SELECT() {
      return getToken(SQLParser.K_SELECT, 0);
    }

    public TerminalNode K_INSERT() {
      return getToken(SQLParser.K_INSERT, 0);
    }

    public TerminalNode K_UPDATE() {
      return getToken(SQLParser.K_UPDATE, 0);
    }

    public TerminalNode K_DELETE() {
      return getToken(SQLParser.K_DELETE, 0);
    }

    public TerminalNode K_DROP() {
      return getToken(SQLParser.K_DROP, 0);
    }

    public Auth_levelContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_auth_level;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterAuth_level(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitAuth_level(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitAuth_level(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Auth_levelContext auth_level() throws RecognitionException {
    Auth_levelContext _localctx = new Auth_levelContext(_ctx, getState());
    enterRule(_localctx, 72, RULE_auth_level);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(433);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0
            && ((1L << _la)
                    & ((1L << K_DELETE)
                        | (1L << K_DROP)
                        | (1L << K_INSERT)
                        | (1L << K_SELECT)
                        | (1L << K_UPDATE)))
                != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Literal_valueContext extends ParserRuleContext {
    public TerminalNode NUMERIC_LITERAL() {
      return getToken(SQLParser.NUMERIC_LITERAL, 0);
    }

    public TerminalNode STRING_LITERAL() {
      return getToken(SQLParser.STRING_LITERAL, 0);
    }

    public TerminalNode K_NULL() {
      return getToken(SQLParser.K_NULL, 0);
    }

    public Literal_valueContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_literal_value;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterLiteral_value(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitLiteral_value(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitLiteral_value(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Literal_valueContext literal_value() throws RecognitionException {
    Literal_valueContext _localctx = new Literal_valueContext(_ctx, getState());
    enterRule(_localctx, 74, RULE_literal_value);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(435);
        _la = _input.LA(1);
        if (!(((((_la - 46)) & ~0x3f) == 0
            && ((1L << (_la - 46))
                    & ((1L << (K_NULL - 46))
                        | (1L << (NUMERIC_LITERAL - 46))
                        | (1L << (STRING_LITERAL - 46))))
                != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Column_full_nameContext extends ParserRuleContext {
    public Column_nameContext column_name() {
      return getRuleContext(Column_nameContext.class, 0);
    }

    public Table_nameContext table_name() {
      return getRuleContext(Table_nameContext.class, 0);
    }

    public Column_full_nameContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_column_full_name;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterColumn_full_name(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitColumn_full_name(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitColumn_full_name(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Column_full_nameContext column_full_name() throws RecognitionException {
    Column_full_nameContext _localctx = new Column_full_nameContext(_ctx, getState());
    enterRule(_localctx, 76, RULE_column_full_name);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(440);
        _errHandler.sync(this);
        switch (getInterpreter().adaptivePredict(_input, 36, _ctx)) {
          case 1:
            {
              setState(437);
              table_name();
              setState(438);
              match(T__4);
            }
            break;
        }
        setState(442);
        column_name();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Database_nameContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SQLParser.IDENTIFIER, 0);
    }

    public Database_nameContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_database_name;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterDatabase_name(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitDatabase_name(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitDatabase_name(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Database_nameContext database_name() throws RecognitionException {
    Database_nameContext _localctx = new Database_nameContext(_ctx, getState());
    enterRule(_localctx, 78, RULE_database_name);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(444);
        match(IDENTIFIER);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Table_nameContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SQLParser.IDENTIFIER, 0);
    }

    public Table_nameContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_table_name;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterTable_name(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitTable_name(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitTable_name(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Table_nameContext table_name() throws RecognitionException {
    Table_nameContext _localctx = new Table_nameContext(_ctx, getState());
    enterRule(_localctx, 80, RULE_table_name);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(446);
        match(IDENTIFIER);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class User_nameContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SQLParser.IDENTIFIER, 0);
    }

    public User_nameContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_user_name;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterUser_name(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitUser_name(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitUser_name(this);
      else return visitor.visitChildren(this);
    }
  }

  public final User_nameContext user_name() throws RecognitionException {
    User_nameContext _localctx = new User_nameContext(_ctx, getState());
    enterRule(_localctx, 82, RULE_user_name);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(448);
        match(IDENTIFIER);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Column_nameContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SQLParser.IDENTIFIER, 0);
    }

    public Column_nameContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_column_name;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterColumn_name(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitColumn_name(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitColumn_name(this);
      else return visitor.visitChildren(this);
    }
  }

  public final Column_nameContext column_name() throws RecognitionException {
    Column_nameContext _localctx = new Column_nameContext(_ctx, getState());
    enterRule(_localctx, 84, RULE_column_name);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(450);
        match(IDENTIFIER);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class View_nameContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SQLParser.IDENTIFIER, 0);
    }

    public View_nameContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_view_name;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterView_name(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitView_name(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitView_name(this);
      else return visitor.visitChildren(this);
    }
  }

  public final View_nameContext view_name() throws RecognitionException {
    View_nameContext _localctx = new View_nameContext(_ctx, getState());
    enterRule(_localctx, 86, RULE_view_name);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(452);
        match(IDENTIFIER);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class PasswordContext extends ParserRuleContext {
    public TerminalNode STRING_LITERAL() {
      return getToken(SQLParser.STRING_LITERAL, 0);
    }

    public PasswordContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_password;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).enterPassword(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SQLListener) ((SQLListener) listener).exitPassword(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SQLVisitor)
        return ((SQLVisitor<? extends T>) visitor).visitPassword(this);
      else return visitor.visitChildren(this);
    }
  }

  public final PasswordContext password() throws RecognitionException {
    PasswordContext _localctx = new PasswordContext(_ctx, getState());
    enterRule(_localctx, 88, RULE_password);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(454);
        match(STRING_LITERAL);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
      case 28:
        return multiple_condition_sempred((Multiple_conditionContext) _localctx, predIndex);
      case 32:
        return expression_sempred((ExpressionContext) _localctx, predIndex);
    }
    return true;
  }

  private boolean multiple_condition_sempred(Multiple_conditionContext _localctx, int predIndex) {
    switch (predIndex) {
      case 0:
        return precpred(_ctx, 2);
      case 1:
        return precpred(_ctx, 1);
    }
    return true;
  }

  private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
    switch (predIndex) {
      case 2:
        return precpred(_ctx, 3);
      case 3:
        return precpred(_ctx, 2);
    }
    return true;
  }

  public static final String _serializedATN =
      "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3H\u01cb\4\2\t\2\4"
          + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
          + "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
          + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
          + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
          + "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"
          + ",\t,\4-\t-\4.\t.\3\2\3\2\3\3\7\3`\n\3\f\3\16\3c\13\3\3\3\3\3\6\3g\n\3"
          + "\r\3\16\3h\3\3\7\3l\n\3\f\3\16\3o\13\3\3\3\7\3r\n\3\f\3\16\3u\13\3\3\4"
          + "\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"
          + "\4\3\4\3\4\5\4\u008c\n\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3"
          + "\b\3\b\5\b\u009b\n\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"
          + "\n\5\n\u00aa\n\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00b5"
          + "\n\13\f\13\16\13\u00b8\13\13\3\13\3\13\5\13\u00bc\n\13\3\13\3\13\3\f\3"
          + "\f\3\f\3\f\3\r\3\r\3\r\3\r\7\r\u00c8\n\r\f\r\16\r\u00cb\13\r\3\r\3\r\3"
          + "\r\3\r\3\r\3\16\3\16\3\16\3\16\7\16\u00d6\n\16\f\16\16\16\u00d9\13\16"
          + "\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\5\20"
          + "\u00e8\n\20\3\21\3\21\3\21\3\21\5\21\u00ee\n\21\3\21\3\21\3\22\3\22\3"
          + "\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\7\25\u0101"
          + "\n\25\f\25\16\25\u0104\13\25\3\25\3\25\5\25\u0108\n\25\3\25\3\25\3\25"
          + "\3\25\7\25\u010e\n\25\f\25\16\25\u0111\13\25\3\26\3\26\3\26\3\26\7\26"
          + "\u0117\n\26\f\26\16\26\u011a\13\26\3\26\3\26\3\27\3\27\5\27\u0120\n\27"
          + "\3\27\3\27\3\27\7\27\u0125\n\27\f\27\16\27\u0128\13\27\3\27\3\27\3\27"
          + "\3\27\7\27\u012e\n\27\f\27\16\27\u0131\13\27\3\27\3\27\5\27\u0135\n\27"
          + "\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\5\31\u0141\n\31\3\31"
          + "\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u014d\n\32\3\33\3\33"
          + "\3\33\7\33\u0152\n\33\f\33\16\33\u0155\13\33\3\34\3\34\3\34\3\34\3\34"
          + "\3\34\3\34\3\34\5\34\u015f\n\34\3\35\3\35\3\35\3\35\5\35\u0165\n\35\3"
          + "\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\7\36\u0170\n\36\f\36\16\36"
          + "\u0173\13\36\3\37\3\37\3\37\3\37\3 \3 \5 \u017b\n \3!\3!\3\"\3\"\3\"\3"
          + "\"\3\"\3\"\5\"\u0185\n\"\3\"\3\"\3\"\3\"\3\"\3\"\7\"\u018d\n\"\f\"\16"
          + "\"\u0190\13\"\3#\3#\3#\3#\3#\3#\7#\u0198\n#\f#\16#\u019b\13#\3#\3#\3$"
          + "\3$\3$\3$\3$\3$\5$\u01a5\n$\3%\3%\3%\3%\6%\u01ab\n%\r%\16%\u01ac\3%\3"
          + "%\3%\5%\u01b2\n%\3&\3&\3\'\3\'\3(\3(\3(\5(\u01bb\n(\3(\3(\3)\3)\3*\3*"
          + "\3+\3+\3,\3,\3-\3-\3.\3.\3.\2\4:B/\2\4\6\b\n\f\16\20\22\24\26\30\32\34"
          + "\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\2\b\4\2\32\32$$\3\2\b\r\3\2"
          + "\20\21\3\2\16\17\7\2##%%++\65\65<<\5\2\60\60CCEE\2\u01d9\2\\\3\2\2\2\4"
          + "a\3\2\2\2\6\u008b\3\2\2\2\b\u008d\3\2\2\2\n\u0090\3\2\2\2\f\u0092\3\2"
          + "\2\2\16\u0096\3\2\2\2\20\u009e\3\2\2\2\22\u00a5\3\2\2\2\24\u00ad\3\2\2"
          + "\2\26\u00bf\3\2\2\2\30\u00c3\3\2\2\2\32\u00d1\3\2\2\2\34\u00df\3\2\2\2"
          + "\36\u00e2\3\2\2\2 \u00e9\3\2\2\2\"\u00f1\3\2\2\2$\u00f4\3\2\2\2&\u00f6"
          + "\3\2\2\2(\u00f9\3\2\2\2*\u0112\3\2\2\2,\u011d\3\2\2\2.\u0136\3\2\2\2\60"
          + "\u013c\3\2\2\2\62\u0144\3\2\2\2\64\u014e\3\2\2\2\66\u015e\3\2\2\28\u0164"
          + "\3\2\2\2:\u0166\3\2\2\2<\u0174\3\2\2\2>\u017a\3\2\2\2@\u017c\3\2\2\2B"
          + "\u0184\3\2\2\2D\u0191\3\2\2\2F\u01a4\3\2\2\2H\u01b1\3\2\2\2J\u01b3\3\2"
          + "\2\2L\u01b5\3\2\2\2N\u01ba\3\2\2\2P\u01be\3\2\2\2R\u01c0\3\2\2\2T\u01c2"
          + "\3\2\2\2V\u01c4\3\2\2\2X\u01c6\3\2\2\2Z\u01c8\3\2\2\2\\]\5\4\3\2]\3\3"
          + "\2\2\2^`\7\3\2\2_^\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2\2ca\3"
          + "\2\2\2dm\5\6\4\2eg\7\3\2\2fe\3\2\2\2gh\3\2\2\2hf\3\2\2\2hi\3\2\2\2ij\3"
          + "\2\2\2jl\5\6\4\2kf\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2ns\3\2\2\2om\3"
          + "\2\2\2pr\7\3\2\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2t\5\3\2\2\2us"
          + "\3\2\2\2v\u008c\5\b\5\2w\u008c\5\n\6\2x\u008c\5\24\13\2y\u008c\5\f\7\2"
          + "z\u008c\5\20\t\2{\u008c\5\16\b\2|\u008c\5\22\n\2}\u008c\5\36\20\2~\u008c"
          + "\5 \21\2\177\u008c\5(\25\2\u0080\u008c\5,\27\2\u0081\u008c\5.\30\2\u0082"
          + "\u008c\5\60\31\2\u0083\u008c\5\30\r\2\u0084\u008c\5\32\16\2\u0085\u008c"
          + "\5\34\17\2\u0086\u008c\5\"\22\2\u0087\u008c\5&\24\2\u0088\u008c\5\26\f"
          + "\2\u0089\u008c\5$\23\2\u008a\u008c\5\62\32\2\u008bv\3\2\2\2\u008bw\3\2"
          + "\2\2\u008bx\3\2\2\2\u008by\3\2\2\2\u008bz\3\2\2\2\u008b{\3\2\2\2\u008b"
          + "|\3\2\2\2\u008b}\3\2\2\2\u008b~\3\2\2\2\u008b\177\3\2\2\2\u008b\u0080"
          + "\3\2\2\2\u008b\u0081\3\2\2\2\u008b\u0082\3\2\2\2\u008b\u0083\3\2\2\2\u008b"
          + "\u0084\3\2\2\2\u008b\u0085\3\2\2\2\u008b\u0086\3\2\2\2\u008b\u0087\3\2"
          + "\2\2\u008b\u0088\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2\2\u008c"
          + "\7\3\2\2\2\u008d\u008e\7\35\2\2\u008e\u008f\7;\2\2\u008f\t\3\2\2\2\u0090"
          + "\u0091\7\37\2\2\u0091\13\3\2\2\2\u0092\u0093\7 \2\2\u0093\u0094\7!\2\2"
          + "\u0094\u0095\5P)\2\u0095\r\3\2\2\2\u0096\u0097\7%\2\2\u0097\u009a\7!\2"
          + "\2\u0098\u0099\7)\2\2\u0099\u009b\7&\2\2\u009a\u0098\3\2\2\2\u009a\u009b"
          + "\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009d\5P)\2\u009d\17\3\2\2\2\u009e"
          + "\u009f\7 \2\2\u009f\u00a0\7>\2\2\u00a0\u00a1\5T+\2\u00a1\u00a2\7*\2\2"
          + "\u00a2\u00a3\7\34\2\2\u00a3\u00a4\5Z.\2\u00a4\21\3\2\2\2\u00a5\u00a6\7"
          + "%\2\2\u00a6\u00a9\7>\2\2\u00a7\u00a8\7)\2\2\u00a8\u00aa\7&\2\2\u00a9\u00a7"
          + "\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\5T+\2\u00ac"
          + "\23\3\2\2\2\u00ad\u00ae\7 \2\2\u00ae\u00af\78\2\2\u00af\u00b0\5R*\2\u00b0"
          + "\u00b1\7\4\2\2\u00b1\u00b6\5\64\33\2\u00b2\u00b3\7\5\2\2\u00b3\u00b5\5"
          + "\64\33\2\u00b4\u00b2\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6"
          + "\u00b7\3\2\2\2\u00b7\u00bb\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba\7\5"
          + "\2\2\u00ba\u00bc\5D#\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd"
          + "\3\2\2\2\u00bd\u00be\7\6\2\2\u00be\25\3\2\2\2\u00bf\u00c0\7\67\2\2\u00c0"
          + "\u00c1\78\2\2\u00c1\u00c2\5R*\2\u00c2\27\3\2\2\2\u00c3\u00c4\7(\2\2\u00c4"
          + "\u00c9\5J&\2\u00c5\u00c6\7\5\2\2\u00c6\u00c8\5J&\2\u00c7\u00c5\3\2\2\2"
          + "\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cc"
          + "\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cc\u00cd\7\61\2\2\u00cd\u00ce\5R*\2\u00ce"
          + "\u00cf\7:\2\2\u00cf\u00d0\5T+\2\u00d0\31\3\2\2\2\u00d1\u00d2\7\64\2\2"
          + "\u00d2\u00d7\5J&\2\u00d3\u00d4\7\5\2\2\u00d4\u00d6\5J&\2\u00d5\u00d3\3"
          + "\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8"
          + "\u00da\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00db\7\61\2\2\u00db\u00dc\5"
          + "R*\2\u00dc\u00dd\7\'\2\2\u00dd\u00de\5T+\2\u00de\33\3\2\2\2\u00df\u00e0"
          + "\7=\2\2\u00e0\u00e1\5P)\2\u00e1\35\3\2\2\2\u00e2\u00e3\7#\2\2\u00e3\u00e4"
          + "\7\'\2\2\u00e4\u00e7\5R*\2\u00e5\u00e6\7A\2\2\u00e6\u00e8\5:\36\2\u00e7"
          + "\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\37\3\2\2\2\u00e9\u00ea\7%\2\2"
          + "\u00ea\u00ed\78\2\2\u00eb\u00ec\7)\2\2\u00ec\u00ee\7&\2\2\u00ed\u00eb"
          + "\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\5R*\2\u00f0"
          + "!\3\2\2\2\u00f1\u00f2\7\67\2\2\u00f2\u00f3\7\"\2\2\u00f3#\3\2\2\2\u00f4"
          + "\u00f5\7\63\2\2\u00f5%\3\2\2\2\u00f6\u00f7\7\67\2\2\u00f7\u00f8\79\2\2"
          + "\u00f8\'\3\2\2\2\u00f9\u00fa\7+\2\2\u00fa\u00fb\7,\2\2\u00fb\u0107\5R"
          + "*\2\u00fc\u00fd\7\4\2\2\u00fd\u0102\5V,\2\u00fe\u00ff\7\5\2\2\u00ff\u0101"
          + "\5V,\2\u0100\u00fe\3\2\2\2\u0101\u0104\3\2\2\2\u0102\u0100\3\2\2\2\u0102"
          + "\u0103\3\2\2\2\u0103\u0105\3\2\2\2\u0104\u0102\3\2\2\2\u0105\u0106\7\6"
          + "\2\2\u0106\u0108\3\2\2\2\u0107\u00fc\3\2\2\2\u0107\u0108\3\2\2\2\u0108"
          + "\u0109\3\2\2\2\u0109\u010a\7?\2\2\u010a\u010f\5*\26\2\u010b\u010c\7\5"
          + "\2\2\u010c\u010e\5*\26\2\u010d\u010b\3\2\2\2\u010e\u0111\3\2\2\2\u010f"
          + "\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110)\3\2\2\2\u0111\u010f\3\2\2\2"
          + "\u0112\u0113\7\4\2\2\u0113\u0118\5L\'\2\u0114\u0115\7\5\2\2\u0115\u0117"
          + "\5L\'\2\u0116\u0114\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116\3\2\2\2\u0118"
          + "\u0119\3\2\2\2\u0119\u011b\3\2\2\2\u011a\u0118\3\2\2\2\u011b\u011c\7\6"
          + "\2\2\u011c+\3\2\2\2\u011d\u011f\7\65\2\2\u011e\u0120\t\2\2\2\u011f\u011e"
          + "\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0126\5F$\2\u0122"
          + "\u0123\7\5\2\2\u0123\u0125\5F$\2\u0124\u0122\3\2\2\2\u0125\u0128\3\2\2"
          + "\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0129\3\2\2\2\u0128\u0126"
          + "\3\2\2\2\u0129\u012a\7\'\2\2\u012a\u012f\5H%\2\u012b\u012c\7\5\2\2\u012c"
          + "\u012e\5H%\2\u012d\u012b\3\2\2\2\u012e\u0131\3\2\2\2\u012f\u012d\3\2\2"
          + "\2\u012f\u0130\3\2\2\2\u0130\u0134\3\2\2\2\u0131\u012f\3\2\2\2\u0132\u0133"
          + "\7A\2\2\u0133\u0135\5:\36\2\u0134\u0132\3\2\2\2\u0134\u0135\3\2\2\2\u0135"
          + "-\3\2\2\2\u0136\u0137\7 \2\2\u0137\u0138\7@\2\2\u0138\u0139\5X-\2\u0139"
          + "\u013a\7\33\2\2\u013a\u013b\5,\27\2\u013b/\3\2\2\2\u013c\u013d\7%\2\2"
          + "\u013d\u0140\7@\2\2\u013e\u013f\7)\2\2\u013f\u0141\7&\2\2\u0140\u013e"
          + "\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0143\5X-\2\u0143"
          + "\61\3\2\2\2\u0144\u0145\7<\2\2\u0145\u0146\5R*\2\u0146\u0147\7\66\2\2"
          + "\u0147\u0148\5V,\2\u0148\u0149\7\b\2\2\u0149\u014c\5B\"\2\u014a\u014b"
          + "\7A\2\2\u014b\u014d\5:\36\2\u014c\u014a\3\2\2\2\u014c\u014d\3\2\2\2\u014d"
          + "\63\3\2\2\2\u014e\u014f\5V,\2\u014f\u0153\5\66\34\2\u0150\u0152\58\35"
          + "\2\u0151\u0150\3\2\2\2\u0152\u0155\3\2\2\2\u0153\u0151\3\2\2\2\u0153\u0154"
          + "\3\2\2\2\u0154\65\3\2\2\2\u0155\u0153\3\2\2\2\u0156\u015f\7\24\2\2\u0157"
          + "\u015f\7\25\2\2\u0158\u015f\7\26\2\2\u0159\u015f\7\27\2\2\u015a\u015b"
          + "\7\30\2\2\u015b\u015c\7\4\2\2\u015c\u015d\7C\2\2\u015d\u015f\7\6\2\2\u015e"
          + "\u0156\3\2\2\2\u015e\u0157\3\2\2\2\u015e\u0158\3\2\2\2\u015e\u0159\3\2"
          + "\2\2\u015e\u015a\3\2\2\2\u015f\67\3\2\2\2\u0160\u0161\7\62\2\2\u0161\u0165"
          + "\7.\2\2\u0162\u0163\7/\2\2\u0163\u0165\7\60\2\2\u0164\u0160\3\2\2\2\u0164"
          + "\u0162\3\2\2\2\u01659\3\2\2\2\u0166\u0167\b\36\1\2\u0167\u0168\5<\37\2"
          + "\u0168\u0171\3\2\2\2\u0169\u016a\f\4\2\2\u016a\u016b\7\22\2\2\u016b\u0170"
          + "\5:\36\5\u016c\u016d\f\3\2\2\u016d\u016e\7\23\2\2\u016e\u0170\5:\36\4"
          + "\u016f\u0169\3\2\2\2\u016f\u016c\3\2\2\2\u0170\u0173\3\2\2\2\u0171\u016f"
          + "\3\2\2\2\u0171\u0172\3\2\2\2\u0172;\3\2\2\2\u0173\u0171\3\2\2\2\u0174"
          + "\u0175\5B\"\2\u0175\u0176\5@!\2\u0176\u0177\5B\"\2\u0177=\3\2\2\2\u0178"
          + "\u017b\5N(\2\u0179\u017b\5L\'\2\u017a\u0178\3\2\2\2\u017a\u0179\3\2\2"
          + "\2\u017b?\3\2\2\2\u017c\u017d\t\3\2\2\u017dA\3\2\2\2\u017e\u017f\b\"\1"
          + "\2\u017f\u0185\5> \2\u0180\u0181\7\4\2\2\u0181\u0182\5B\"\2\u0182\u0183"
          + "\7\6\2\2\u0183\u0185\3\2\2\2\u0184\u017e\3\2\2\2\u0184\u0180\3\2\2\2\u0185"
          + "\u018e\3\2\2\2\u0186\u0187\f\5\2\2\u0187\u0188\t\4\2\2\u0188\u018d\5B"
          + "\"\6\u0189\u018a\f\4\2\2\u018a\u018b\t\5\2\2\u018b\u018d\5B\"\5\u018c"
          + "\u0186\3\2\2\2\u018c\u0189\3\2\2\2\u018d\u0190\3\2\2\2\u018e\u018c\3\2"
          + "\2\2\u018e\u018f\3\2\2\2\u018fC\3\2\2\2\u0190\u018e\3\2\2\2\u0191\u0192"
          + "\7\62\2\2\u0192\u0193\7.\2\2\u0193\u0194\7\4\2\2\u0194\u0199\5V,\2\u0195"
          + "\u0196\7\5\2\2\u0196\u0198\5V,\2\u0197\u0195\3\2\2\2\u0198\u019b\3\2\2"
          + "\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019c\3\2\2\2\u019b\u0199"
          + "\3\2\2\2\u019c\u019d\7\6\2\2\u019dE\3\2\2\2\u019e\u01a5\7\20\2\2\u019f"
          + "\u01a0\5R*\2\u01a0\u01a1\7\7\2\2\u01a1\u01a2\7\20\2\2\u01a2\u01a5\3\2"
          + "\2\2\u01a3\u01a5\5N(\2\u01a4\u019e\3\2\2\2\u01a4\u019f\3\2\2\2\u01a4\u01a3"
          + "\3\2\2\2\u01a5G\3\2\2\2\u01a6\u01b2\5R*\2\u01a7\u01aa\5R*\2\u01a8\u01a9"
          + "\7-\2\2\u01a9\u01ab\5R*\2\u01aa\u01a8\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac"
          + "\u01aa\3\2\2\2\u01ac\u01ad\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01af\7\61"
          + "\2\2\u01af\u01b0\5:\36\2\u01b0\u01b2\3\2\2\2\u01b1\u01a6\3\2\2\2\u01b1"
          + "\u01a7\3\2\2\2\u01b2I\3\2\2\2\u01b3\u01b4\t\6\2\2\u01b4K\3\2\2\2\u01b5"
          + "\u01b6\t\7\2\2\u01b6M\3\2\2\2\u01b7\u01b8\5R*\2\u01b8\u01b9\7\7\2\2\u01b9"
          + "\u01bb\3\2\2\2\u01ba\u01b7\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bc\3\2"
          + "\2\2\u01bc\u01bd\5V,\2\u01bdO\3\2\2\2\u01be\u01bf\7B\2\2\u01bfQ\3\2\2"
          + "\2\u01c0\u01c1\7B\2\2\u01c1S\3\2\2\2\u01c2\u01c3\7B\2\2\u01c3U\3\2\2\2"
          + "\u01c4\u01c5\7B\2\2\u01c5W\3\2\2\2\u01c6\u01c7\7B\2\2\u01c7Y\3\2\2\2\u01c8"
          + "\u01c9\7E\2\2\u01c9[\3\2\2\2\'ahms\u008b\u009a\u00a9\u00b6\u00bb\u00c9"
          + "\u00d7\u00e7\u00ed\u0102\u0107\u010f\u0118\u011f\u0126\u012f\u0134\u0140"
          + "\u014c\u0153\u015e\u0164\u016f\u0171\u017a\u0184\u018c\u018e\u0199\u01a4"
          + "\u01ac\u01b1\u01ba";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}
