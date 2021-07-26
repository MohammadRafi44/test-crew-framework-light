package com.example.base;

import com.example.utils.ConfigManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;


/**
 * Selenium webDriver manager
 *
 * @author Author Name
 */
public class MobileDriverManager {

  private static final Logger LOGGER = LogManager.getLogger(MobileDriverManager.class);
  private static final ThreadLocal<AndroidDriver<MobileElement>> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

  private MobileDriverManager() {
  }

  public static AndroidDriver<MobileElement> getDriver() {
    return WEB_DRIVER_THREAD_LOCAL.get();
  }

  private static void setDriver(AndroidDriver<MobileElement> androidDriver) {
    WEB_DRIVER_THREAD_LOCAL.set(androidDriver);
  }

  public static void quitDriver() {
    if (WEB_DRIVER_THREAD_LOCAL.get() != null) {
      WEB_DRIVER_THREAD_LOCAL.get().quit();
    }
  }

  public static void initDriver(DesiredCapabilities userProvidedCapabilities) {
    try {
      DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
      if (userProvidedCapabilities != null) {
        LOGGER.info("Merging user provided selenium capabilities");
        desiredCapabilities.merge(userProvidedCapabilities);
        LOGGER.info(desiredCapabilities);
      } else {
        LOGGER.info("User provided capability is null. Ignoring...");
      }
      URL url;
      try {
        url = new URL(ConfigManager.getConfigProperty("appium.url"));
      } catch (Exception e) {
        LOGGER.error(e);
        throw new RuntimeException(e);
      }
      AndroidDriver<MobileElement> androidDriver = new AndroidDriver<>(url, desiredCapabilities);
      MobileDriverManager.setDriver(androidDriver);
      LOGGER.info("Android Driver successfully initialized. Session id : [{}]", androidDriver.getSessionId());
    } catch (Exception e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
  }
}
