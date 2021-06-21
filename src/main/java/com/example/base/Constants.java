package com.example.base;

import com.example.utils.ConfigManager;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

  public static final String CURRENT_DIR = System.getProperty("user.dir");
  public static final String TEST_RESOURCES_DIR = Paths.get(CURRENT_DIR, "src", "test", "resources").toString();
  public static final Path RUN_MANAGER_WORKBOOK = Paths.get(TEST_RESOURCES_DIR, ConfigManager.getConfigProperty("run.manager.workbook.name"));
  public static final String CONTROLLER_SHEET_NAME = "Controller";
}
