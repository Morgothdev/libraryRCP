package libraryRCP.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class BookManager {

	private static Books instance;
	
	private BookManager(){};
	
	public static void configure(Properties properties) throws IllegalArgumentException{
			try {
				
				String className = properties.getProperty("data.access.class");
				Class<?> classType = Class.forName(className);
				Constructor<?> constructor = classType.getConstructor(Properties.class);
				instance = (Books) constructor.newInstance(properties);
				
			} catch (InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException | ClassNotFoundException | NullPointerException e) {
				throw new IllegalArgumentException(e);
			}
		
		
	}
	
	public static Books getInstance(){
		return instance;
	}
	
}
