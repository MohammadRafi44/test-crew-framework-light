package com.example.listener;

import com.example.report.ExtentManager;
import com.example.report.ExtentTestManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

  private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

  @Override
  public void onTestStart(ITestResult result) {
    LOGGER.debug("Executing test method : [{}] in class [{}] | Thread id : [{}]", result.getMethod().getMethodName(),
        result.getTestClass().getName(), Thread.currentThread().getId());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    LOGGER.debug("Passed test method : [{}] in class [{}]", result.getMethod().getMethodName(), result.getTestClass().getName());
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    LOGGER.debug("Failed test method : [{}] in class [{}]", iTestResult.getMethod().getMethodName(),
        iTestResult.getTestClass().getName());
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    LOGGER.debug("Skipped test method : [{}] in class [{}]", result.getMethod().getMethodName(), result.getTestClass().getName());
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  @Override
  public void onStart(ITestContext context) {
    LOGGER.debug("Executing test sequence : [{}] from class [{}]", context.getCurrentXmlTest().getName(),
        context.getCurrentXmlTest().getXmlClasses().get(0).getName());
  }

  @Override
  public void onFinish(ITestContext context) {
    LOGGER.debug("Finished test sequence : [{}] from class [{}]", context.getCurrentXmlTest().getName(),
        context.getCurrentXmlTest().getXmlClasses().get(0).getName());
    ExtentManager.getExtentReports().flush();
  }

}
