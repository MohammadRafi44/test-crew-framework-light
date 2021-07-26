package com.example.tests.api.rest;

import static io.restassured.RestAssured.given;

import com.example.base.Actions;
import com.example.base.BaseTest;
import com.example.base.ExcelManager;
import com.example.utils.Helper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import java.util.Map;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class TypiCodeRestApiTest extends BaseTest {

  // Raw rest assured use
  @Test()
  public void typiCodeApiGetTestRawUse(Map<String, String> data) {
    Response response = given()
        .log().all(true)
        .get(data.get("ApiUrl"))
        .then().log().all(true)
        .and().statusCode(200)
        .and().extract().response();
    Helper.log(response.asString());
    Helper.log("My custom comment to extent from the test");
    // Writing to controller sheet
    ExcelManager.writeToExcelColumn(data, "Controller", "Comment", "Hello from code");
    // Writing to test data sheet
    ExcelManager.writeToExcelColumn(data, "Typicode", "Comment", "Hello from code");
  }

  // Using action
  @Test()
  public void typiCodeApiGetTestUsingAction(Map<String, String> data) {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    requestSpecBuilder.addHeader("Content-Type", "application/json");
    Response response = Actions.getRequest(data.get("ApiUrl"), requestSpecBuilder);
    Helper.log(response.asString());
    Helper.log("My custom comment to extent from the test");
  }

}
