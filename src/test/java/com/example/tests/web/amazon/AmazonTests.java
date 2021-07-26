package com.example.tests.web.amazon;

import static com.example.objects.amazon.AmazonObjects.GO_BUTTON;
import static com.example.objects.amazon.AmazonObjects.SEARCH_BOX;

import com.example.base.Actions;
import com.example.base.BaseTest;
import com.example.modules.amazon.AmazonModule;
import com.example.utils.Helper;
import java.util.Map;
import org.testng.annotations.Test;

public class AmazonTests extends BaseTest {

  // In line web actions example
  @Test()
  public void amazonSearch(Map<String, String> data) {
    Actions.openUrl(data.get("Url"));
    Actions.takeScreenshot();
    Actions.enterText(SEARCH_BOX, data.get("SearchProduct"), "Searched for : " + data.get("SearchProduct"));
    Actions.takeScreenshot();
    Actions.click(GO_BUTTON, "Clicked Go button next to search box");
    Actions.sleep(5);
    Actions.takeScreenshot();
    Helper.log("My custom comment to extent from the test");
  }

  // Test with separate module class example
  @Test()
  public void amazonSearchWithModuleApproach(Map<String, String> data) {
    AmazonModule amazonModule = new AmazonModule();
    amazonModule.launchAmazon(data)
        .search(data);
    Helper.log("My custom comment to extent from the test");
  }
}
