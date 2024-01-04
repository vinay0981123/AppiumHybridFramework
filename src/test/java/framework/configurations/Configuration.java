package framework.configurations;

import framework.utilities.Path;
import framework.utilities.PropertiesFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public interface Configuration {
    String PROJECT_DIR = Path.getProjectDir();

    Properties appConfig = PropertiesFile.read(PROJECT_DIR + "/Configuration/config.properties");


    String DEVICE_NAME = appConfig.getProperty("device.name");
    String DEVICE_ID = appConfig.getProperty("deviceID");
    String APP_NAME = appConfig.getProperty("app.name");
    String Email_ID = appConfig.getProperty("mail.id");
    String APP_PACKAGE = appConfig.getProperty("app.package");
    String APP_ACTIVITY = appConfig.getProperty("app.activity");
    String APPIUM_HUB = appConfig.getProperty("appium.hub");
    String APPIUM_PORT = appConfig.getProperty("appium.port");
    String APPIUM_JS = appConfig.getProperty("appium.jsFile.path");
    String NODE_PATH = appConfig.getProperty("node.path");
    String TEST_APP = appConfig.getProperty("app.path");
    String BASE_URL = appConfig.getProperty("baseUrl");
    String BROWSER_NAME = appConfig.getProperty("browser.name");


    /**
     * To get remote grid URL
     *
     * @return remote grid URL
     */
    static URL getRemoteGridURL() {
        URL REMOTE_GRID_URL = null;
        try {
            REMOTE_GRID_URL = new URL(getAppiumURL());
        } catch (MalformedURLException e) {
            System.out.println("Error occurred in Remote Grid URL.");
        }
        return REMOTE_GRID_URL;
    }


    /**
     * To get appium URL
     *
     * @return appium URL
     */
    static String getAppiumURL() {
        String APPIUM_URL = null;
        if (System.getenv("APPIUM_URL") != null) {
            APPIUM_URL = System.getenv("APPIUM_URL");
        } else {
            APPIUM_URL = appConfig.getProperty("appium.url");
        }
        return APPIUM_URL;
    }





}
