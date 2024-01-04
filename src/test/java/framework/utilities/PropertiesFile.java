package framework.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

    /**
     * Parse the given properties file.
     *
     * @param path absolute path to the properties file.
     * @return Properties instance of file.
     */

    public static Properties read(String path) {
        Properties prop = new Properties();
        FileInputStream file = null;

        try {
            file = new FileInputStream(path);
            prop.load(file);

        } catch (Exception e) {
            System.out.println("Failed to load properties file.");
        } finally {
            if (file != null)
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return prop;
    }

}
