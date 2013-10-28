package libraryRCP.data.bookManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Properties;

import libraryRCP.data.BookManager;
import libraryRCP.data.Books;

import org.junit.Before;
import org.junit.Test;

public class BookManagerWithClassImplementationTest {

	BookManager bookManager;
	private Properties properties;

	@Before
	public void setUp() throws Exception {
		properties = new Properties();
		properties.put("data.access.class", "libraryRCP.data.ClassBooksDataBase");
	}

	@Test
	public void testConfigureClassImplementationOfBooksWithValidData() {
		BookManager.configure(properties);
		Books books = BookManager.getInstance();

		assertNotNull(books);
		assertEquals(books.getClass().getName(),
				"libraryRCP.data.ClassBooksDataBase");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConfigureClassImplementationOfBooksWithInvalidData() {
		properties.put("data.access.class", "another.package.another.class");
		BookManager.configure(properties);
		fail("should throw excpetion");
	}
}
