package libraryRCP.data.book.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class BookRepositoryFactory {

    private static BookRepository instance;

    private BookRepositoryFactory() {
    };

    public static void configure(Properties properties) throws IllegalArgumentException {
        String className = properties.getProperty("data.access.class");
        System.out.println("class from properties " + className);
        if (className != null) {
            try {
                Class<?> classType = Class.forName(className);
                Constructor<?> constructor = classType.getConstructor(Properties.class);
                instance = (BookRepository) constructor.newInstance(properties);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException | SecurityException | ClassNotFoundException
                    | NullPointerException e) {
                System.out.println("error " + e);
                throw new IllegalArgumentException(e);
            }
        }
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            throw new FactoryNotConfiguredException();
        }
        return instance;
    }
}
