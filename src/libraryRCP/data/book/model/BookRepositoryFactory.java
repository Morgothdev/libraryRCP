package libraryRCP.data.book.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

public class BookRepositoryFactory {

	private static BookRepository instance;
	private static IEclipseContext eclipseContext;

	private BookRepositoryFactory() {
	};

	public static void configure(Properties properties) throws IllegalArgumentException {
		String className = properties.getProperty("data.access.class");
		System.out.println(className);
		if (className == null) {
			try {
				Class<?> classType = Class.forName(className);
				Constructor<?> constructor = classType.getConstructor(Properties.class);
				instance = (BookRepository) constructor.newInstance(properties);
				ContextInjectionFactory.inject(instance, eclipseContext);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException
					| NoSuchMethodException | SecurityException | ClassNotFoundException
					| NullPointerException e) {
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

	public static void setEclipseContext(IEclipseContext eclipseContext) {
		BookRepositoryFactory.eclipseContext = eclipseContext;
	}
}
