### To run tests via maven : clean test

### To run test via testng : /testng.xml

### Run manager excel file : src/test/resources/Run_Manager.xlsx

### To access webDriver anytime, anywhere : WebDriver driver = DriverManager.getDriver();

### To access Config manager anytime, anywhere : ConfigManager.getConfigProperty("key")

### Config file : src/test/resources/config.properties

### Driver : src/test/resources/drivers

### Report : /extent-reports

### You may change the name of workbook Run_Manager.xlsx if you want in src/test/resources/config.properties
run.manager.workbook.name=Run_Manager.xlsx

### You may change the name of worksheet Controller if you want in src/test/resources/config.properties
run.manager.workbook.name=Run_Manager.xlsx
controller.worksheet.name=Controller

### For mobile sample test please install this app before running the test
https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk