package com.example.tests.mobile;

import static com.example.objects.swagmobile.SwagMobileOR.SWAG_ADD_TO_CART;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_CART_IMAGE;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_CHECKOUT;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_CONTINUE;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_FIRST_NAME;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_LAST_NAME;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_LOGIN_BUTTON;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_PASSWORD;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_POSTAL_CODE;
import static com.example.objects.swagmobile.SwagMobileOR.SWAG_USER_NAME;
import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;

import com.example.base.BaseTest;
import com.example.base.MobileActions;
import com.example.utils.Helper;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.util.Map;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class SwagMobileAppTest extends BaseTest {

  @Override
  public DesiredCapabilities addCapabilities() {
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    desiredCapabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
    desiredCapabilities.setCapability(AndroidMobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
    desiredCapabilities.setCapability(AndroidMobileCapabilityType.VERSION, "10");
    desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.swaglabsmobileapp");
    desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.swaglabsmobileapp.SplashActivity");
    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ANDROID_UIAUTOMATOR2);
    return desiredCapabilities;
  }

  @Test
  public void swagLoginTest(Map<String, String> data) {
    Helper.sleep(5);
    MobileActions.enterText(SWAG_USER_NAME, data.get("Username"), "Entered User name");
    MobileActions.enterText(SWAG_PASSWORD, data.get("Password"), "Entered Password");
    MobileActions.takeScreenshot();
    MobileActions.click(SWAG_LOGIN_BUTTON, "Clicked Login button");
    Helper.sleep(5);
    MobileActions.click(SWAG_ADD_TO_CART, "Clicked Add to cart button");
    MobileActions.takeScreenshot();
    MobileActions.click(SWAG_CART_IMAGE, "Clicked cart image");
    MobileActions.takeScreenshot();
    MobileActions.click(SWAG_CHECKOUT, "Clicked Checkout button");
    MobileActions.takeScreenshot();
    MobileActions.enterText(SWAG_FIRST_NAME, data.get("FirstName"), "Entered First Name");
    MobileActions.enterText(SWAG_LAST_NAME, data.get("LastName"), "Entered Last Name");
    MobileActions.enterText(SWAG_POSTAL_CODE, data.get("PostalCode"), "Entered Postal Code");
    MobileActions.takeScreenshot();
    MobileActions.click(SWAG_CONTINUE, "Clicked Continue button");
    MobileActions.takeScreenshot();
    MobileActions.scrollToElementAndClick("Finish", "Clicked Finish button");
    MobileActions.takeScreenshot();
    Helper.sleep(5);
    MobileActions.takeScreenshot();
  }
}
