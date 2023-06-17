package cn.edu.benchmark;

import cn.edu.benchmark.executor.TransactionTestExecutor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionTest.class);

  private static TransactionTestExecutor transactionTestExecutor;

  @Before
  public void setUp() throws Exception {
    transactionTestExecutor = new TransactionTestExecutor();
    LOGGER.info("======================== Create database  ======================== ");
    transactionTestExecutor.createAndUseDB();
    LOGGER.info("======================== Prepare data  ======================== ");
    transactionTestExecutor.prepareData();
  }

  @After
  public void tearDown() {
    transactionTestExecutor.close();
  }

  @Test
  public void testLostUpdate() throws Exception {
    LOGGER.info("======================== Lost update ========================");
    transactionTestExecutor.testLostUpdate();
    Assert.fail();
  }

  @Test
  public void testDirtyRead() throws Exception {
    LOGGER.info("======================== Dirty read ========================");
    transactionTestExecutor.testDirtyRead();
  }
}
