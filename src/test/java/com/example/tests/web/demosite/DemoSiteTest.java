package com.example.tests.web.demosite;

import static com.example.objects.demosite.DemoSiteObjects.ADD_USER;
import static com.example.objects.demosite.DemoSiteObjects.LOGIN_BUTTON;
import static com.example.objects.demosite.DemoSiteObjects.LOGIN_LINK;
import static com.example.objects.demosite.DemoSiteObjects.MESSAGE;
import static com.example.objects.demosite.DemoSiteObjects.PASSWORD;
import static com.example.objects.demosite.DemoSiteObjects.SAVE_BUTTON;
import static com.example.objects.demosite.DemoSiteObjects.USER_NAME;

import com.example.base.Actions;
import com.example.base.BaseTest;
import com.example.report.ExtentTestManager;
import com.example.utils.Helper;
import java.util.Map;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoSiteTest extends BaseTest {

  // Test with reusable methods in same class
  @Test(dataProvider = "testDataProvider")
  public void demoSiteCreateUserAndLogin(Map<String, String> data) {
    launchDemoSite(data);
    createUser(data);
    login(data);
    validateLoginIfLoginSuccessful();
    Helper.log("My custom comment to extent from the test");
  }

  private void launchDemoSite(Map<String, String> data) {
    Actions.openUrl(data.get("Url"), "Launched url : " + data.get("Url"));
    Actions.takeScreenshot();
  }

  private void createUser(Map<String, String> data) {
    Actions.click(ADD_USER);
    Actions.enterText(USER_NAME, data.get("Username"), "Entered Username : " + data.get("Username"));
    Actions.enterText(PASSWORD, data.get("Password"), "Entered Password : " + data.get("Password"));
    Actions.takeScreenshot();
    Actions.click(SAVE_BUTTON);
    ExtentTestManager.getTest().info("User created");
  }

  private void login(Map<String, String> data) {
    Actions.click(LOGIN_LINK);
    Actions.enterText(USER_NAME, data.get("Username"), "Entered Username : " + data.get("Username"));
    Actions.enterText(PASSWORD, data.get("Password"), "Entered Password : " + data.get("Password"));
    Actions.takeScreenshot();
    Actions.click(LOGIN_BUTTON);
  }

  private void validateLoginIfLoginSuccessful() {
    String message = Actions.getText(MESSAGE);
    Assert.assertTrue(message.contains("Successful"), "Login failed : " + message);
    Helper.log("Login successful");
  }

  @Override
  public DesiredCapabilities addCapabilities() {
    return null;
  }
}
