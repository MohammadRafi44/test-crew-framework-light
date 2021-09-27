package com.example.listener;

import com.example.utils.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

  private static final Logger LOGGER = LogManager.getLogger(SuiteListener.class);

  @Override
  public void onStart(ISuite suite) {
    LOGGER.debug("Starting test suite [{}]", suite.getName());
    suite.getXmlSuite().setThreadCount(Integer.parseInt(ConfigManager.getConfigProperty("parallel.test.count")));
  }

  @Override
  public void onFinish(ISuite suite) {
    LOGGER.debug("Finished test suite [{}]", suite.getName());
  }
}
