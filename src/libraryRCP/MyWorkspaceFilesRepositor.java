package libraryRCP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

public class MyWorkspaceFilesRepositor {

    private static final String F_CONFIGURATION_FILE = "config.properties"; //$NON-NLS-1$
    private static final String F_DATA = "data"; //$NON-NLS-1$
    private static IPath configurationFileLocation = null;
    private static Properties properties = null;
    private static IPath dataLocation;

    public static Properties getProperties() {
        if (properties == null) {
            loadProperties();
        }
        return properties;
    }

    public static boolean loadProperties() {
        try {
            properties = new Properties();
            File configurationFile = getConfigurationFileLocation().toFile();
            if (configurationFile.exists()) {
                properties.load(new FileInputStream(configurationFile));
                properties.put("classLoader", Thread.currentThread().getContextClassLoader());
                System.out.println("properties loaded from file");
                return true;
            } else {
                // BookRepositoryFactory.configureDefault();
                return configurationFile.createNewFile();

                // properties.store(getConfigurationFileLocation().toFile(),
                // "own configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static IPath getConfigurationFileLocation() {
        if (configurationFileLocation == null) {
            IPath path = Platform.getLocation();
            path.toFile().mkdirs();
            configurationFileLocation = path.append(F_CONFIGURATION_FILE);
            try {
                configurationFileLocation.toFile().createNewFile();
            } catch (IOException e) {
                // TODO e.printStack
                e.printStackTrace();
            }
        }
        return configurationFileLocation;
    }

    public static IPath getDataLocation() {
        if (dataLocation == null) {
            IPath path = Platform.getLocation();
            dataLocation = path.append(F_DATA);
            dataLocation.toFile().mkdirs();
        }
        return dataLocation;
    }

}
