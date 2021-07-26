package com.example.objects.swagmobile;

import org.openqa.selenium.By;

public class SwagMobileOR {

  public static final By SWAG_USER_NAME = By.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]");
  public static final By SWAG_PASSWORD = By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]");
  public static final By SWAG_LOGIN_BUTTON = By.xpath("//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]");
  public static final By SWAG_ADD_TO_CART = By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]");
  public static final By SWAG_CART_IMAGE = By.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");
  public static final By SWAG_CHECKOUT = By.xpath("//android.view.ViewGroup[@content-desc=\"test-CHECKOUT\"]");
  public static final By SWAG_FIRST_NAME = By.xpath("//android.widget.EditText[@content-desc=\"test-First Name\"]");
  public static final By SWAG_LAST_NAME = By.xpath("//android.widget.EditText[@content-desc=\"test-Last Name\"]");
  public static final By SWAG_POSTAL_CODE = By.xpath("//android.widget.EditText[@content-desc=\"test-Zip/Postal Code\"]");
  public static final By SWAG_CONTINUE = By.xpath("//android.view.ViewGroup[@content-desc=\"test-CONTINUE\"]");

}
