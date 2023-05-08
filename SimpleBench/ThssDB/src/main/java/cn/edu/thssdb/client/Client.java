package cn.edu.thssdb.client;

import cn.edu.thssdb.rpc.thrift.*;
import cn.edu.thssdb.utils.Global;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Client {

  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  static final String HOST_ARGS = "h";
  static final String HOST_NAME = "host";

  static final String HELP_ARGS = "help";
  static final String HELP_NAME = "help";

  static final String PORT_ARGS = "p";
  static final String PORT_NAME = "port";

  private static final PrintStream SCREEN_PRINTER = new PrintStream(System.out);
  private static final Scanner SCANNER = new Scanner(System.in);

  private static TTransport transport;
  private static TProtocol protocol;
  private static IService.Client client;
  private static CommandLine commandLine;

  private static long sessionId;   //client识别符，用于多client的情况，判断是否已经连接以及连接的对象

  public static void main(String[] args) {
    commandLine = parseCmd(args);
    if (commandLine.hasOption(HELP_ARGS)) {
      showHelp();
      return;
    }
    try {
      echoStarting();
      String host = commandLine.getOptionValue(HOST_ARGS, Global.DEFAULT_SERVER_HOST);
      int port = Integer.parseInt(commandLine.getOptionValue(PORT_ARGS, String.valueOf(Global.DEFAULT_SERVER_PORT)));
      transport = new TSocket(host, port);
      transport.open();
      protocol = new TBinaryProtocol(transport);
      client = new IService.Client(protocol);
      boolean open = true;
      sessionId = -1;
      while (true) {
        print(Global.CLI_PREFIX);
        String msg = SCANNER.nextLine();
        long startTime = System.currentTimeMillis();
        switch (msg.trim()) {
          case Global.SHOW_TIME:
            getTime();
            break;
          case Global.QUIT:
            open = false;
            break;
          case Global.CONNECT:
            connect();
            break;
          case Global.DISCONNECT:
            disconnect();
            break;
          default:
            handleStatements(msg.trim());
//            println("Invalid statements!");
            break;
        }
        long endTime = System.currentTimeMillis();
        println("It costs " + (endTime - startTime) + " ms.");
        if (!open) {
          break;
        }
      }
      transport.close();
    } catch (TTransportException e) {
      logger.error(e.getMessage());
    }
  }

  private static void getTime() {
    GetTimeReq req = new GetTimeReq();
    try {
      println(client.getTime(req).getTime());
    } catch (TException e) {
      logger.error(e.getMessage());
    }
  }

  private static void connect() {
    if(sessionId != -1) {
      println("Have connected!");
    }else{
      println("Please input your username and password:");
      print("username:");
      String username = SCANNER.nextLine();
      print("password:");
      String password = SCANNER.nextLine();
      ConnectReq req = new ConnectReq(username, password);
      try {
        ConnectResp resp = client.connect(req);
        if(resp.getStatus().getCode() == Global.FAILURE_CODE ){
          println(resp.getStatus().getMsg());
        }else {
          sessionId = resp.getSessionId();
          println("Successfully connect!");
//          System.out.println(sessionId);
        }
      }catch (TException e){
        logger.error(e.getMessage());
      }

    }
  }

  private static void disconnect(){
    if(sessionId == -1){
      println("Haven't connected!");
    }else{
      DisconnetReq req = new DisconnetReq(sessionId);
      try {
        DisconnetResp resp = client.disconnect(req);
        if(resp.getStatus().getCode() == Global.FAILURE_CODE ){
          println(resp.getStatus().getMsg());
        }else {
          sessionId = -1;
          println("Successfully disconnect!");
        }

      }catch (TException e){
        logger.error(e.getMessage());
        println("No connection!");
      }
    }
  }

  private static void handleStatements(String msg){
    if(sessionId == -1) {
      println("Haven't connected!");
    }else {
      ExecuteStatementReq req = new ExecuteStatementReq(sessionId, msg);
      try {
        ExecuteStatementResp resp = client.executeStatement(req);
//        println(resp.getStatus().getMsg());
        if(resp.getStatus().getCode() == Global.FAILURE_CODE){
          println(resp.getStatus().getMsg());
        }else {
          boolean isAbort = resp.isIsAbort();
          boolean hasResult = resp.isHasResult();
//           对于查询语句：
          if(! isAbort){
            if(hasResult){
              List<String> columnsList = resp.getColumnsList();
              println(columnsList.toString());
              List<List<String>> rowList = resp.getRowList();
              for(List<String> row: rowList){
                println(row.toString());
              }
            }else {
              println(resp.getStatus().getMsg());
            }
          }else {
            println(resp.getStatus().getMsg());
          }
        }
      }catch (TException e){
        logger.error(e.getMessage());
        println("No connection!");
      }
    }
  }
  static Options createOptions() {
    Options options = new Options();
    options.addOption(Option.builder(HELP_ARGS)
            .argName(HELP_NAME)
            .desc("Display help information(optional)")
            .hasArg(false)
            .required(false)
            .build()
    );
    options.addOption(Option.builder(HOST_ARGS)
            .argName(HOST_NAME)
            .desc("Host (optional, default 127.0.0.1)")
            .hasArg(false)
            .required(false)
            .build()
    );
    options.addOption(Option.builder(PORT_ARGS)
            .argName(PORT_NAME)
            .desc("Port (optional, default 6667)")
            .hasArg(false)
            .required(false)
            .build()
    );
    return options;
  }

  static CommandLine parseCmd(String[] args) {
    Options options = createOptions();
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      logger.error(e.getMessage());
      println("Invalid command line argument!");
      System.exit(-1);
    }
    return cmd;
  }

  static void showHelp() {
    // TODO
    println("DO IT YOURSELF");
  }

  static void echoStarting() {
    println("----------------------");
    println("Starting ThssDB Client");
    println("----------------------");
  }

  static void print(String msg) {
    SCREEN_PRINTER.print(msg);
  }

  static void println() {
    SCREEN_PRINTER.println();
  }

  static void println(String msg) {
    SCREEN_PRINTER.println(msg);
  }
}
