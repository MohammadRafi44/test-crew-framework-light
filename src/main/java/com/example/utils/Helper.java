package com.example.utils;

import com.example.base.DriverManager;
import com.example.report.ExtentTestManager;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.Reporter;

/**
 * The Helper class
 *
 * @author Author Name
 */
public class Helper {

  private static final Logger LOGGER = LogManager.getLogger(Helper.class);

  private Helper() {
  }

  public static void sleep(int sleepInSeconds) {
    try {
      LOGGER.info("Waiting for seconds: " + sleepInSeconds);
      Thread.sleep(sleepInSeconds * 1000L);
    } catch (Exception e) {
      //
    }
  }

  public static byte[] takeScreenshot() {
    return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
  }

  public static String log(String message) {
    LOGGER.info(message);
    Reporter.log(message);
    ExtentTestManager.getTest().info(message);
    return message;
  }

  public static String fileToString(String path) {
    try {
      return IOUtils.toString(Objects.requireNonNull(Helper.class.getResourceAsStream(path)), StandardCharsets.UTF_8);
    } catch (Exception e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
  }

  public static String getStringFromContext(ITestContext context, String key) {
    return (String) context.getAttribute(key);
  }

  public static Integer getIntFromContext(ITestContext context, String key) {
    return (Integer) context.getAttribute(key);
  }

  public static String decode(String stringToDecode) {
    byte[] decode = Base64.getDecoder().decode(stringToDecode);
    return new String(decode);
  }

  public static String encode(String stringToEncode) {
    return Base64.getEncoder().encodeToString(stringToEncode.getBytes(StandardCharsets.UTF_8));
  }

  public static String getAppFile(String fileName){
    return Paths.get(System.getProperty("user.dir"), "src", "test", "resources", fileName).toString();
  }

}
