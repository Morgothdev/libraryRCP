package libraryRCP.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

public class BookManager {

	private static Books instance;

	private BookManager() {
	};

	public static void configure() {
		Properties properties = new Properties();
		properties.put("data.access.class", "libraryRCP.data.ClassBooksDataBase");
		configure(properties);
		instance.addBook(new Book("Author of First Book","Title of First Book"));
		instance.addBook(new Book("Author of Second Book","Title of Second Book"));
	}

	public static void configure(Properties properties) throws IllegalArgumentException {
		try {

			String className = properties.getProperty("data.access.class");
			Class<?> classType = Class.forName(className);
			Constructor<?> constructor = classType.getConstructor(Properties.class);
			instance = (Books) constructor.newInstance(properties);
			LogManager.getLogger(BookManager.class).info("Loaded implementation {}",
					instance.getClass().getName());
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException
				| NullPointerException e) {
			throw new IllegalArgumentException(e);
		}

	}

	public static Books getInstance() {
		return instance;
	}

}
