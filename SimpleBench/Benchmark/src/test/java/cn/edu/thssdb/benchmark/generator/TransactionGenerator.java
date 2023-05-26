package cn.edu.thssdb.benchmark.generator;

import cn.edu.thssdb.benchmark.transaction.Transaction;
import cn.edu.thssdb.benchmark.transaction.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionGenerator.class);
  private SQLGenerator sqlGenerator;
  private TransactionFactory transactionFactory;

  public TransactionGenerator(BaseDataGenerator dataGenerator, int dataSeed) {
    sqlGenerator = new SQLGenerator(dataGenerator, dataSeed);
    transactionFactory = new TransactionFactory(sqlGenerator);
  }

  public Transaction generateTransaction(TransactionType type) {
    return transactionFactory.newTransaction(type);
  }

  private static class TransactionFactory {
    private SQLGenerator sqlGenerator;

    public TransactionFactory(SQLGenerator sqlGenerator) {
      this.sqlGenerator = sqlGenerator;
    }

    public Transaction newTransaction(TransactionType type) {
      switch (type) {
        case T1:
          return generateT1();
        case T2:
          return generateT2();
        case T3:
          return generateT3();
        case T4:
          return generateT4();
        default:
          LOGGER.error("Invalid transaction type.");
      }
      return null;
    }

    private Transaction generateT1() {
      String[] sqlList = new String[10];
      for (int i = 0; i < 10; i++) {
        sqlList[i] = sqlGenerator.generateInsertSQL();
      }
      return new Transaction(sqlList);
    }

    private Transaction generateT2() {
      String[] sqlList = new String[3];
      sqlList[0] = sqlGenerator.generateUpdateSQL();
      sqlList[1] = sqlGenerator.generateQuerySQL();
      sqlList[2] = sqlGenerator.generateInsertSQL();
      return new Transaction(sqlList);
    }

    private Transaction generateT3() {
      String[] sqlList = new String[4];
      sqlList[0] = sqlGenerator.generateJoinSQL();
      sqlList[1] = sqlGenerator.generateQuerySQL();
      sqlList[2] = sqlGenerator.generateQuerySQL();
      sqlList[3] = sqlGenerator.generateJoinSQL();
      return new Transaction(sqlList);
    }

    private Transaction generateT4() {
      String[] sqlList = new String[3];
      sqlList[0] = sqlGenerator.generateQuerySQL();
      sqlList[1] = sqlGenerator.generateUpdateSQL();
      sqlList[2] = sqlGenerator.generateDeleteSQL();
      return new Transaction(sqlList);
    }
  }
}
