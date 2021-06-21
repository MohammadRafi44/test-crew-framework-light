package com.example.base;

import com.example.listener.SeleniumListener;
import com.example.utils.ConfigManager;
import java.net.URL;
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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

/**
 * Selenium webDriver manager
 *
 * @author Author Name
 */
public class DriverManager {

  private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
  private static final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

  private DriverManager() {
  }

  public static WebDriver getDriver() {
    return webDriverThreadLocal.get();
  }

  private static void setDriver(WebDriver driver) {
    webDriverThreadLocal.set(driver);
  }

  public static void quitDriver() {
    WebDriver driver = webDriverThreadLocal.get();
    if (driver != null) {
      driver.quit();
    }
  }

  public static void setBrowser(Browser browser) {
    setBrowser(browser, null);
  }

  public static void setBrowser(Browser browser, DesiredCapabilities userProvidedCapabilities) {
    try {
      DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
      if (userProvidedCapabilities != null) {
        desiredCapabilities.merge(userProvidedCapabilities);
      } else {
        LOGGER.debug("User provided capability is null. Ignoring...");
      }
      LOGGER.info("Browser : {}", browser);
      EventFiringWebDriver eventFiringWebDriver;
      if (browser.toString().equalsIgnoreCase("chrome")) {
        System.setProperty("webdriver.chrome.driver", ConfigManager.getConfigProperty("chrome.driver.binary.path"));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(desiredCapabilities);
        WebDriver driver = new ChromeDriver(chromeOptions);
        eventFiringWebDriver = new EventFiringWebDriver(driver);
        eventFiringWebDriver.register(new SeleniumListener());
      } else if (browser.toString().equalsIgnoreCase("firefox")) {
        System.setProperty("webdriver.gecko.driver", ConfigManager.getConfigProperty("firefox.driver.binary.path"));
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        Assert.assertNotNull(ConfigManager.getConfigProperty("firefox.browser.path"),
            "Please set firefox browser installation path in config.properties");
        firefoxOptions.setBinary(ConfigManager.getConfigProperty("firefox.browser.path"));
        firefoxOptions.merge(desiredCapabilities);
        WebDriver driver = new FirefoxDriver(firefoxOptions);
        eventFiringWebDriver = new EventFiringWebDriver(driver);
        eventFiringWebDriver.register(new SeleniumListener());
      } else if (browser.toString().equalsIgnoreCase("edge")) {
        System.setProperty("webdriver.edge.driver", ConfigManager.getConfigProperty("edge.driver.binary.path"));
        WebDriver driver = new EdgeDriver();
        eventFiringWebDriver = new EventFiringWebDriver(driver);
        eventFiringWebDriver.register(new SeleniumListener());
      } else if (browser.toString().equalsIgnoreCase("mobile")) {
        URL url;
        try {
          url = new URL(ConfigManager.getConfigProperty("appium.url"));
        } catch (Exception e) {
          LOGGER.error(e);
          throw new RuntimeException(e);
        }
        WebDriver driver = new RemoteWebDriver(url, desiredCapabilities);
        eventFiringWebDriver = new EventFiringWebDriver(driver);
        eventFiringWebDriver.register(new SeleniumListener());
      } else {
        throw new IllegalArgumentException(
            String.format("%s is invalid value. Enter valid browser value in config.properties", browser));
      }
      eventFiringWebDriver.manage().timeouts().implicitlyWait(Integer.parseInt(ConfigManager.getConfigProperty("implicit.wait.time")),
          TimeUnit.SECONDS);
      eventFiringWebDriver.manage().timeouts()
          .pageLoadTimeout(Integer.parseInt(ConfigManager.getConfigProperty("page.load.wait.time")), TimeUnit.SECONDS);
      eventFiringWebDriver.manage().timeouts()
          .setScriptTimeout(Integer.parseInt(ConfigManager.getConfigProperty("page.load.wait.time")), TimeUnit.SECONDS);
      eventFiringWebDriver.manage().window().maximize();
      DriverManager.setDriver(eventFiringWebDriver);
    } catch (Exception e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
  }
}
