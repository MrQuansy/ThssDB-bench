package cn.edu.thssdb.utils;

public class Global {
  public static int fanout = 129;

  public static int SUCCESS_CODE = 0;
  public static int FAILURE_CODE = -1;

  public static String DEFAULT_SERVER_HOST = "127.0.0.1";
  public static int DEFAULT_SERVER_PORT = 6667;

  public static String CLI_PREFIX = "ThssDB>";
  public static final String SHOW_TIME = "show time;";
  public static final String QUIT = "quit;";
  public static final String CONNECT = "connect;";
  public static final String DISCONNECT = "disconnect;";
  public static final String SELECT = "select";
  public static final String UPDATE = "update";
  public static final String INSERT = "insert";
  public static final String DELETE = "delete";
  public static final String CREATE = "create";
  public static final String DROP = "drop";
  public static final String SHOW = "show";
  public static final String USE = "use";
  public static final String COMMIT = "commit;";
  public static final String BEGINTRANSACTION = "begin transaction;";
  public static final String ROLLBACK = "rollback;";
  public static final String SAVEPOINT = "savepoint;";


  public static final String S_URL_INTERNAL = "jdbc:default:connection";

  public static final String DATA_DIRECTORY = "data/";
}
