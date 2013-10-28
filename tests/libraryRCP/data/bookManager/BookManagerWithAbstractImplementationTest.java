package libraryRCP.data.bookManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Properties;

import libraryRCP.data.BookManager;
import libraryRCP.data.Books;

import org.junit.Before;
import org.junit.Test;

public abstract class BookManagerWithAbstractImplementationTest {

	BookManager bookManager;
	private Properties properties;

	public BookManagerWithAbstractImplementationTest() {
		super();
	}

	protected abstract void setUpProperties(Properties properties);

	protected abstract String getValidDataBaseClassToLoad();

	@Before
	public void setUp() throws Exception {
		properties = new Properties();
		setUpProperties(properties);
	}

	@Test
	public void testConfigureClassImplementationOfBooksWithValidData() {
		BookManager.configure(properties);
		Books books = BookManager.getInstance();

		assertNotNull(books);
		assertEquals(books.getClass().getName(), getValidDataBaseClassToLoad());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConfigureClassImplementationOfBooksWithInvalidData() {
		properties.put("data.access.class", "another.package.another.class");
		BookManager.configure(properties);
		fail("should throw excpetion");
	}

}