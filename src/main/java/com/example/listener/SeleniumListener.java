package com.example.listener;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class SeleniumListener extends AbstractWebDriverEventListener {

  private static final Logger LOGGER = LogManager.getLogger(SeleniumListener.class);

  @Override
  public void beforeAlertAccept(WebDriver driver) {
    LOGGER.info("Trying to accept an alert");
  }

  @Override
  public void afterAlertAccept(WebDriver driver) {
    LOGGER.info("Accepted an alert");
  }

  @Override
  public void afterAlertDismiss(WebDriver driver) {
    LOGGER.info("Dismissed an alert");
  }

  @Override
  public void beforeAlertDismiss(WebDriver driver) {
    LOGGER.info("Trying to dismiss an alert");
  }

  @Override
  public void beforeNavigateTo(String url, WebDriver driver) {
    LOGGER.debug("Trying to open url : " + url);
  }

  @Override
  public void afterNavigateTo(String url, WebDriver driver) {
    LOGGER.debug("Opened url : " + url);
  }

  @Override
  public void beforeNavigateBack(WebDriver driver) {
    LOGGER.info("Trying to navigate back");
  }

  @Override
  public void afterNavigateBack(WebDriver driver) {
    LOGGER.info("Navigated back");
  }

  @Override
  public void beforeNavigateForward(WebDriver driver) {
    LOGGER.info(String.format("Trying to navigate forward. Current url : %s", driver.getCurrentUrl()));
  }

  @Override
  public void afterNavigateForward(WebDriver driver) {
    LOGGER.info(String.format("Navigated forward. Current url : %s", driver.getCurrentUrl()));
  }

  @Override
  public void beforeNavigateRefresh(WebDriver driver) {
    LOGGER.info(String.format("Trying to refresh the current page. Current url : %s", driver.getCurrentUrl()));
  }

  @Override
  public void afterNavigateRefresh(WebDriver driver) {
    LOGGER.info(String.format("Refreshed the current page. Current url : %s", driver.getCurrentUrl()));
  }

  @Override
  public void beforeFindBy(By by, WebElement element, WebDriver driver) {
    LOGGER.debug(String.format("Trying to find web element : %s ", by.toString()));
  }

  @Override
  public void afterFindBy(By by, WebElement element, WebDriver driver) {
    LOGGER.debug(String.format("Found web element : %s ", by.toString()));
  }

  @Override
  public void beforeClickOn(WebElement element, WebDriver driver) {
    LOGGER.debug(String.format("Trying to click web element : %s ", element.toString()));
  }

  @Override
  public void afterClickOn(WebElement element, WebDriver driver) {
    LOGGER.debug(String.format("Clicked : %s ", element.toString()));
  }

  @Override
  public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
    LOGGER.debug(String.format("Value before send keys : [{}] for web element : [{}]%s | Value : %s", element.toString(),
        Arrays.toString(keysToSend)));
  }

  @Override
  public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
    LOGGER.debug("Entered text : [{}] | Web element : [{}]", Arrays.toString(keysToSend), element.toString());
  }

  @Override
  public void beforeScript(String script, WebDriver driver) {
    LOGGER.debug(String.format("Trying to execute script : %s", script));
  }

  @Override
  public void afterScript(String script, WebDriver driver) {
    LOGGER.info(String.format("Executed script : %s", script));
  }

  @Override
  public void afterSwitchToWindow(String windowName, WebDriver driver) {
    LOGGER.info(String.format("Switched to window : %s", windowName));
  }

  @Override
  public void beforeSwitchToWindow(String windowName, WebDriver driver) {
    LOGGER.info(String.format("Trying to switch to window : %s", windowName));
  }

  @Override
  public void onException(Throwable throwable, WebDriver driver) {
    LOGGER.error("Exception occurred : ", throwable);
  }

  @Override
  public <X> void beforeGetScreenshotAs(OutputType<X> target) {
    LOGGER.debug("Trying to take screenshot");
  }

  @Override
  public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
    LOGGER.debug("Successfully captured screenshot");
  }

  @Override
  public void beforeGetText(WebElement element, WebDriver driver) {
    LOGGER.debug("Trying to get text of web element : [{}]", element.toString());
  }

  @Override
  public void afterGetText(WebElement element, WebDriver driver, String text) {
    LOGGER.info("Captured text : [{}] of web element : [{}]", text, element.toString());
  }

}
