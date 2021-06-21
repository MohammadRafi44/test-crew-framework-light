package com.example.utils;

import com.example.base.Constants;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Config manager to read config.properties
 *
 * @author Author Name
 */
public class ConfigManager {

  private static final String TEST_CONFIG_FILE = Paths.get(Constants.TEST_RESOURCES_DIR, "config.properties").toString();
  private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);
  private static Map<String, String> configMap = new HashMap<>();

  private ConfigManager() {
  }

  public static synchronized String getConfigProperty(String key) {
    if (configMap.size() == 0) {
      Properties properties = new Properties();
      try {
        properties.load(new FileInputStream(TEST_CONFIG_FILE));
        configMap = new HashMap<>(properties.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey().toString(),
                e -> e.getValue().toString())));
        LOGGER.debug("Loaded config properties : " + TEST_CONFIG_FILE);
      } catch (Exception e) {
        LOGGER.error(e);
        throw new RuntimeException(e);
      }
    }
    return configMap.get(key);
  }

}
