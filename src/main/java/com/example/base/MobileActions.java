package com.example.base;

import static io.restassured.RestAssured.given;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.example.report.ExtentTestManager;
import com.example.utils.ConfigManager;
import com.example.utils.Helper;
import io.appium.java_client.MobileElement;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public abstract class MobileActions {

  private static final Logger LOGGER = LogManager.getLogger(MobileActions.class);

  public static void takeScreenshot() {
    String base64Screenshot = ((TakesScreenshot) Objects.requireNonNull(MobileDriverManager.getDriver()))
        .getScreenshotAs(OutputType.BASE64);
    ExtentTestManager.getTest()
        .info("Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
  }

  public static void sleep(int sleepInSeconds) {
    try {
      LOGGER.info("Waiting for {} Seconds", sleepInSeconds);
      Thread.sleep(sleepInSeconds * 1000L);
    } catch (Exception e) {
      //
    }
  }

  public static void openUrl(String url) {
    openUrl(url, "Opened url : " + url);
  }

  public static void openUrl(String url, String message) {
    MobileDriverManager.getDriver().get(url);
    Helper.log(message);
  }

  public static WebElement find(By by) {
    WebDriverWait wait = new WebDriverWait(MobileDriverManager.getDriver(),
        Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  public static WebElement find(WebElement webElement) {
    WebDriverWait wait = new WebDriverWait(MobileDriverManager.getDriver(),
        Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
    return wait.until(ExpectedConditions.elementToBeClickable(webElement));
  }

  public static void click(By by) {
    click(by, "Clicked : " + by.toString());
  }

  public static void click(By by, String message) {
    click(find(by), message);
  }

  public static void click(WebElement webElement) {
    webElement.click();
  }

  public static void click(WebElement webElement, String message) {
    webElement.click();
    Helper.log(message);
  }

  public static void waitUntilClickable(By by) {
    WebDriverWait wait = new WebDriverWait(MobileDriverManager.getDriver(),
        Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
    wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  public static void waitUntilClickable(WebElement webElement) {
    WebDriverWait wait = new WebDriverWait(MobileDriverManager.getDriver(),
        Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
    wait.until(ExpectedConditions.elementToBeClickable(webElement));
  }

  public static boolean checkIfWebElementExists(By by) {
    try {
      if (MobileDriverManager.getDriver().findElement(by).isDisplayed()) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public static boolean checkIfWebElementExists(WebElement webelement) {
    try {
      if (webelement.isDisplayed()) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public static boolean waitForElementAtIntervals(By by, int interval, int maxWait) {
    boolean elementExists = false;
    try {
      int initialWait = 0;
      while (initialWait <= maxWait) {
        if (checkIfWebElementExists(by)) {
          elementExists = true;
          LOGGER.info("Found element [{}] after waiting for [{}]", by.toString(), initialWait);
          initialWait = maxWait + 1;
        } else {
          sleep(1);
          initialWait = initialWait + interval;
        }
      }
      if (!elementExists) {
        LOGGER.info("WebElement [{}] not found", by.toString());
      }
    } catch (Exception e) {
      //
    }
    return elementExists;
  }

  public static boolean waitForElementAtIntervalsAndClick(By by, int interval, int maxTime) {
    boolean flag = waitForElementAtIntervals(by, interval, maxTime);
    if (flag) {
      click(by);
    }
    return flag;
  }

  public static void clickByJS(By by) {
    clickByJS(MobileDriverManager.getDriver().findElement(by), "Clicked web element by JS : " + by.toString());
  }

  public static void clickByJS(WebElement webElement, String message) {
    JavascriptExecutor executor = MobileDriverManager.getDriver();
    executor.executeScript("arguments[0].click();", webElement);
    Helper.log(message);
  }

  public static void enterText(By by, String value) {
    enterText(find(by), value, String.format("Entered text [%s] in webElement [%s]", value, by.toString()));
  }

  public static void enterText(By by, String value, String message) {
    enterText(find(by), value, message);
    LOGGER.debug("Entered text :  [{}] in Web element : [{}]", value, by.toString());
  }

  public static void enterText(WebElement webElement, String value, String message) {
    webElement.sendKeys(value);
    Helper.log(message.concat(" ==> ").concat(value));
    LOGGER.debug("Web element : [{}] | Value entered : [{}]", webElement, value);
  }

  public static String getText(By by) {
    return find(by).getText();
  }

  public static String getText(WebElement webElement) {
    return webElement.getText();
  }

  public static Object executeJs(String javaScript) {
    JavascriptExecutor executor = MobileDriverManager.getDriver();
    return executor.executeScript(javaScript);
  }

  public static Response getRequest(String url, RequestSpecBuilder requestSpecBuilder) {
    return getRequest(url, requestSpecBuilder, RestAssuredConfig.newConfig());
  }

  public static Response postRequest(String url, RequestSpecBuilder requestSpecBuilder) {
    return postRequest(url, requestSpecBuilder, RestAssuredConfig.newConfig());
  }

  public static Response putRequest(String url, RequestSpecBuilder requestSpecBuilder) {
    return putRequest(url, requestSpecBuilder, RestAssuredConfig.newConfig());
  }

  public static Response deleteRequest(String url, RequestSpecBuilder requestSpecBuilder) {
    return deleteRequest(url, requestSpecBuilder, RestAssuredConfig.newConfig());
  }

  public static Response optionsRequest(String url, RequestSpecBuilder requestSpecBuilder) {
    return optionsRequest(url, requestSpecBuilder, RestAssuredConfig.newConfig());
  }

  public static Response patchRequest(String url, RequestSpecBuilder requestSpecBuilder) {
    return getRequest(url, requestSpecBuilder, RestAssuredConfig.newConfig());
  }

  public static Response soapRequest(String url, RequestSpecBuilder requestSpecBuilder) {
    return soapRequest(url, requestSpecBuilder, RestAssuredConfig.newConfig());
  }

  public static Response getRequest(String url, RequestSpecBuilder requestSpecBuilder, RestAssuredConfig restAssuredConfig) {
    Response response = given(requestSpecBuilder.build()).log().all(true)
        .config(restAssuredConfig)
        .when()
        .get(url)
        .then().log().all(true)
        .and().extract().response();
    LOGGER.debug("Response {} ", response.asString());
    return response;
  }

  public static Response postRequest(String url, RequestSpecBuilder requestSpecBuilder, RestAssuredConfig restAssuredConfig) {
    Response response = RestAssured.given(requestSpecBuilder.build()).log().all(true)
        .config(restAssuredConfig)
        .when()
        .post(url)
        .then().log().all(true)
        .and().extract().response();
    LOGGER.debug("Response {} ", response.asString());
    return response;
  }

  public static Response putRequest(String url, RequestSpecBuilder requestSpecBuilder, RestAssuredConfig restAssuredConfig) {
    Response response = RestAssured.given(requestSpecBuilder.build()).log().all(true)
        .config(restAssuredConfig)
        .when()
        .put(url)
        .then().log().all(true)
        .and().extract().response();
    LOGGER.debug("Response {} ", response.asString());
    return response;
  }

  public static Response deleteRequest(String url, RequestSpecBuilder requestSpecBuilder, RestAssuredConfig restAssuredConfig) {
    Response response = RestAssured.given(requestSpecBuilder.build()).log().all(true)
        .config(restAssuredConfig)
        .when()
        .delete(url)
        .then().log().all(true)
        .and().extract().response();
    LOGGER.debug("Response {} ", response.asString());
    return response;
  }

  public static Response optionsRequest(String url, RequestSpecBuilder requestSpecBuilder, RestAssuredConfig restAssuredConfig) {
    Response response = RestAssured.given(requestSpecBuilder.build()).log().all(true)
        .config(restAssuredConfig)
        .when()
        .options(url)
        .then().log().all(true)
        .and().extract().response();
    LOGGER.debug("Response {} ", response.asString());
    return response;
  }

  public static Response patchRequest(String url, RequestSpecBuilder requestSpecBuilder, RestAssuredConfig restAssuredConfig) {
    Response response = RestAssured.given(requestSpecBuilder.build()).log().all(true)
        .config(restAssuredConfig)
        .when()
        .patch(url)
        .then().log().all(true)
        .and().extract().response();
    LOGGER.debug("Response {} ", response.asString());
    return response;
  }

  public static Response soapRequest(String url, RequestSpecBuilder requestSpecBuilder, RestAssuredConfig restAssuredConfig) {
    Response response = RestAssured.given(requestSpecBuilder.build()).log().all(true)
        .config(restAssuredConfig)
        .when()
        .post(url)
        .then().log().all(true)
        .and().extract().response();
    LOGGER.debug("Response {} ", response.asString());
    return response;
  }

  public static void validateResponseIsNotNull(Response response) {
    Assert.assertNotNull(response);
  }

  public static void validateApiResponseStatusCode(Response response, int expectedCode) {
    Assert.assertEquals(response.getStatusCode(), expectedCode);
  }

  public static void scrollToElementAndClick(String elementText) {
    MobileElement elementByAndroidUIAutomator = MobileDriverManager.getDriver().findElementByAndroidUIAutomator(
        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + elementText
            + "\").instance(0))");
    click(elementByAndroidUIAutomator);
  }

  public static void scrollToElementAndClick(String elementText, String message) {
    MobileElement elementByAndroidUIAutomator = MobileDriverManager.getDriver().findElementByAndroidUIAutomator(
        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + elementText
            + "\").instance(0))");
    click(elementByAndroidUIAutomator, message);
  }
}
