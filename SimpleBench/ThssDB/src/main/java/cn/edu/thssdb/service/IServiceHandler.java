package cn.edu.thssdb.service;

import cn.edu.thssdb.parser.SQLHandler;
import cn.edu.thssdb.query.QueryResult;
import cn.edu.thssdb.rpc.thrift.ConnectReq;
import cn.edu.thssdb.rpc.thrift.ConnectResp;
import cn.edu.thssdb.rpc.thrift.DisconnectReq;
import cn.edu.thssdb.rpc.thrift.DisconnectResp;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementReq;
import cn.edu.thssdb.rpc.thrift.ExecuteStatementResp;
import cn.edu.thssdb.rpc.thrift.GetTimeReq;
import cn.edu.thssdb.rpc.thrift.GetTimeResp;
import cn.edu.thssdb.rpc.thrift.IService;
import cn.edu.thssdb.rpc.thrift.Status;
import cn.edu.thssdb.schema.Manager;
import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.utils.Global;
import org.apache.thrift.TException;

import java.util.*;

public class IServiceHandler implements IService.Iface {
  private SessionManager sessionManager;
  private SQLHandler sqlHandler;
  private Manager manager;

  public IServiceHandler() {
    manager = Manager.getInstance();
    sessionManager = new SessionManager();
    sqlHandler = new SQLHandler(manager);
  }

  @Override
  public GetTimeResp getTime(GetTimeReq req) throws TException {
    GetTimeResp resp = new GetTimeResp();
    resp.setTime(new Date().toString());
    resp.setStatus(new Status(Global.SUCCESS_CODE));
    return resp;
  }

  @Override
  public ConnectResp connect(ConnectReq req) throws TException {
    ConnectResp resp = new ConnectResp();
    // 身份验证
    String username = req.getUsername();
    String password = req.getPassword();
    if (!checkAuth(username, password)) {
      Status status = new Status(Global.FAILURE_CODE);
      status.setMsg("Wrong username or password! Please input again!");
      //      System.out.println("connect: wrong username or password of username:"+ username);
      resp.setStatus(status);
      return resp;
    }

    // 创建session：通过时间+随机数
    long sessionId = sessionManager.newSession(username);
    System.out.println("connect: " + username);
    resp.setSessionId(sessionId);
    resp.setStatus(new Status(Global.SUCCESS_CODE));
    return resp;
  }

  @Override
  public DisconnectResp disconnect(DisconnectReq req) throws TException {
    DisconnectResp resp = new DisconnectResp();
    long sessionId = req.getSessionId();
    if (!sessionManager.exist(sessionId)) {
      throw new TException("No connection!");
    } else {
      sessionManager.deleteSession(sessionId);
      System.out.println("disconnect");
      resp.setStatus(new Status(Global.SUCCESS_CODE));
      return resp;
    }
  }

  @Override
  public ExecuteStatementResp executeStatement(ExecuteStatementReq req) throws TException {
    ExecuteStatementResp resp = new ExecuteStatementResp();
    long sessionId = req.getSessionId();
    if (!sessionManager.exist(sessionId)) {
      throw new TException("No connection!");
    }

    String statement = req.getStatement();
    if (statement == null || statement.isEmpty()) {
      Status status = new Status(Global.FAILURE_CODE);
      status.setMsg("Empty statement!");
      resp.setStatus(status);
      resp.setHasResult(false);
      return resp;
    }

    String[] statementList = statement.split(";");
    int n = statementList.length;
    ArrayList<QueryResult> list = new ArrayList<>();
    ArrayList<QueryResult> result;
    String[] wal = {"insert", "delete", "update", "select"};
    for (int i = 0; i < n; i++) {
      String cmd = statementList[i].split("\\s+")[0];
      if (cmd != null && cmd.length() > 0) {
        boolean auto = false;
        if (!manager.transaction_session.contains(sessionId)
            && Arrays.asList(wal).contains(cmd.toLowerCase())) {
          auto = true;
        }
        if (auto) sqlHandler.handle("begin transaction", sessionId);
        result = sqlHandler.handle(statementList[i], sessionId);
        if (auto) sqlHandler.handle("commit", sessionId);
        list.addAll(result);
      }
    }
    // 返回结果:
    Status status;
    int size = list.size();
    if (size > 1) { // 如果是多条操作，只返回OK或fail
      boolean success = true;
      StringBuilder message = new StringBuilder();
      for (QueryResult queryResult : list) {
        if (queryResult.isWrong()) {
          success = false;
          message.append(queryResult.getMessage());
        }
      }

      if (success) {
        status = new Status(Global.SUCCESS_CODE);
        status.setMsg("OK.");
      } else {
        status = new Status(Global.FAILURE_CODE);
        status.setMsg(message.toString());
      }
      resp.setStatus(status);
      resp.setHasResult(false);

    } else if (size == 1) {
      QueryResult queryResult = list.get(0);
      if (queryResult.isWrong()) { //
        status = new Status(Global.FAILURE_CODE);
        status.setMsg(queryResult.getMessage());
        resp.setStatus(status);
        resp.setHasResult(false);
      } else {
        status = new Status(Global.SUCCESS_CODE);
        status.setMsg(queryResult.getMessage());
        resp.setStatus(status);
        if (queryResult.getSql().split("\\s+")[0].equalsIgnoreCase(Global.SELECT)) {
          resp.setHasResult(true);
          resp.setColumnsList(queryResult.columnNames);
          List<List<String>> rowList = new ArrayList<>();
          for (Row row : queryResult.results) {
            ArrayList<String> rowData = row.getDataList();
            rowList.add(rowData);
          }
          resp.setRowList(rowList);
        }
      }
    } else {
      status = new Status(Global.FAILURE_CODE);
      status.setMsg("Wrong SQL.");
      resp.setStatus(status);
      resp.setHasResult(false);
    }
    //

    return resp;
  }

  public boolean checkAuth(String username, String password) {
    // TODO
    return true;
  }
}
