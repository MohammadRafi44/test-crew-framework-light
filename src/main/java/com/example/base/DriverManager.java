package com.example.base;

import com.example.listener.SeleniumListener;
import com.example.utils.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;


/**
 * Selenium webDriver manager
 *
 * @author Author Name
 */
public class DriverManager {

  private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
  private static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

  private DriverManager() {
  }

  public static WebDriver getDriver() {
    return WEB_DRIVER_THREAD_LOCAL.get();
  }

  private static void setDriver(WebDriver driver) {
    WEB_DRIVER_THREAD_LOCAL.set(driver);
  }

  public static void quitDriver() {
    WebDriver driver = WEB_DRIVER_THREAD_LOCAL.get();
    if (driver != null) {
      driver.quit();
    }
  }

  public static void initDriver(Browser browser, DesiredCapabilities userProvidedCapabilities) {
    try {
      DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
      if (userProvidedCapabilities != null) {
        LOGGER.info("Merging user provided selenium capabilities");
        desiredCapabilities.merge(userProvidedCapabilities);
        LOGGER.info("Capabilities : [{}]", desiredCapabilities);
      } else {
        LOGGER.info("User provided capability is null. Ignoring...");
      }
      LOGGER.info("Browser : {}", browser);
      WebDriver driver;
      if (browser.toString().equalsIgnoreCase("chrome")) {
        if (ConfigManager.isDriverAutoDownload()) {
          WebDriverManager.chromedriver().setup();
        } else {
          System.setProperty("webdriver.chrome.driver", ConfigManager.getConfigProperty("chrome.driver.binary.path"));
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(desiredCapabilities);
        driver = new ChromeDriver(chromeOptions);
      } else if (browser.toString().equalsIgnoreCase("firefox")) {
        System.setProperty("webdriver.gecko.driver", ConfigManager.getConfigProperty("firefox.driver.binary.path"));
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        Assert.assertNotNull(ConfigManager.getConfigProperty("firefox.browser.path"),
            "Please set firefox browser installation path in config.properties");
        firefoxOptions.setBinary(ConfigManager.getConfigProperty("firefox.browser.path"));
        firefoxOptions.merge(desiredCapabilities);
        driver = new FirefoxDriver(firefoxOptions);
      } else if (browser.toString().equalsIgnoreCase("edge")) {
        System.setProperty("webdriver.edge.driver", ConfigManager.getConfigProperty("edge.driver.binary.path"));
        driver = new EdgeDriver();
      } else {
        throw new IllegalArgumentException(
            String.format("%s is invalid value. Enter valid browser value in config.properties", browser));
      }
      EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
      eventFiringWebDriver.manage().timeouts()
          .implicitlyWait(Integer.parseInt(ConfigManager.getConfigProperty("implicit.wait.time")),
              TimeUnit.SECONDS);
      eventFiringWebDriver.manage().timeouts()
          .pageLoadTimeout(Integer.parseInt(ConfigManager.getConfigProperty("page.load.wait.time")), TimeUnit.SECONDS);
      eventFiringWebDriver.manage().timeouts()
          .setScriptTimeout(Integer.parseInt(ConfigManager.getConfigProperty("page.load.wait.time")), TimeUnit.SECONDS);
      eventFiringWebDriver.manage().window().maximize();
      eventFiringWebDriver.register(new SeleniumListener());
      DriverManager.setDriver(eventFiringWebDriver);
    } catch (Exception e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
  }
}
