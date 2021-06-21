package com.example.objects.demosite;

import org.openqa.selenium.By;

public class DemoSiteObjects {

  public static final By LOGIN_LINK = By.xpath("//a[contains(text(), 'Login')]");
  public static final By USER_NAME = By.name("username");
  public static final By PASSWORD = By.name("password");
  public static final By LOGIN_BUTTON = By.xpath("//input[contains(@value, 'Test Login')]");
  public static final By MESSAGE = By.xpath("/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b");
  public static final By ADD_USER = By.xpath("//a[contains(text(), 'Add a User')]");
  public static final By SAVE_BUTTON = By.xpath("//input[@value='save']");
}
