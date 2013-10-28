package libraryRCP.data.bookManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Properties;

import libraryRCP.data.BookManager;

import org.junit.Before;
import org.junit.Test;

public class BookManagerWithClassImplementationTest {

	BookManager bookManager;
	private Properties properties;

	@Before
	public void setUp() throws Exception {
		properties = new Properties();
		properties.put("data.access.class", "libraryRCP.data.ClassDataBase");
	}

	@Test
	public void testConfigureClassImplementationOfBooksWithValidData() {
		BookManager.configure(properties);
		bookManager = BookManager.getInstance();

		assertNotNull(bookManager);
		assertEquals(bookManager.getClass().getName(),
				"libraryRCP.data.ClassDataBase");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConfigureClassImplementationOfBooksWithInvalidData() {
		properties.put("data.access.class", "another.package.another.class");
		BookManager.configure(properties);
		fail("should throw excpetion");
	}
}
