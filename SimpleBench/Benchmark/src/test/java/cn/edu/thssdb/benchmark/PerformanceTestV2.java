package cn.edu.thssdb.benchmark;

import cn.edu.thssdb.benchmark.executor.PerformanceTestExecutorV2;
import org.apache.thrift.TException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceTestV2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceTestV2.class);
  private static PerformanceTestExecutorV2 performanceTestExecutor;

  @BeforeClass
  public static void setUp() throws Exception {
    performanceTestExecutor = new PerformanceTestExecutorV2();
  }

  @AfterClass
  public static void tearDown() {
    performanceTestExecutor.close();
  }

  @Test
  public void simpleTest() throws TException {
    // create database
    LOGGER.info("======================== Performance test  ======================== ");
    performanceTestExecutor.test();
  }
}
