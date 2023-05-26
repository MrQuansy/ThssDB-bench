package cn.edu.thssdb.benchmark.generator;

import cn.edu.thssdb.benchmark.common.PreparedStatement;

public class SQLGenerator {

  // 注意并发问题

  BaseDataGenerator dataGenerator;

  public SQLGenerator(BaseDataGenerator dataGenerator) {
    this.dataGenerator = dataGenerator;
  }

  PreparedStatement insertStatement = new PreparedStatement("Insert into ? values(?,?,?,?,?)");
  PreparedStatement updateStatement = new PreparedStatement("update ? set ? = ? where id = ?");
  PreparedStatement deleteStatement = new PreparedStatement("delete from ? where id = ?");
  PreparedStatement queryStatement = new PreparedStatement("select ? from ? where id = ?");
  PreparedStatement joinStatement = new PreparedStatement("select ? from ? join ? where ");

  public String generateInsertSQL() {
    insertStatement.setString(0, "test_table1");
    return insertStatement.getSQL();
  }

  public String generateUpdateSQL() {
    updateStatement.setString(0, "test_table1");
    return updateStatement.getSQL();
  }

  public String generateDeleteSQL() {
    deleteStatement.setString(0, "test_table1");
    return deleteStatement.getSQL();
  }

  public String generateQuerySQL() {
    queryStatement.setString(0, "test_table1");
    return queryStatement.getSQL();
  }

  public String generateJoinSQL() {
    joinStatement.setString(0, "test_table1");
    return joinStatement.getSQL();
  }
}
