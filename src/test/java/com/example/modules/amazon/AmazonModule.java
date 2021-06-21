package com.example.modules.amazon;

import static com.example.objects.amazon.AmazonObjects.GO_BUTTON;
import static com.example.objects.amazon.AmazonObjects.SEARCH_BOX;

import com.example.base.Actions;
import java.util.Map;

public class AmazonModule {

  public AmazonModule launchAmazon(Map<String, String> data) {
    Actions.openUrl(data.get("Url"), "Opened amazon url");
    Actions.takeScreenshot();
    return this;
  }

  public AmazonModule search(Map<String, String> data) {
    Actions.openUrl(data.get("Url"));
    Actions.takeScreenshot();
    Actions.enterText(SEARCH_BOX, data.get("SearchProduct"), "Searched for : " + data.get("SearchProduct"));
    Actions.takeScreenshot();
    Actions.click(GO_BUTTON, "Clicked Go button next to search box");
    Actions.sleep(5);
    Actions.takeScreenshot();
    return this;
  }

}
