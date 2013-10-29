package libraryRCP.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class XMLBooksDataBaseTest extends BooksDataBaseTest {

	protected Book mockedBook;
	protected Books testedClassBooks;
	volatile int count = 0;
	
	protected Books getTestedBooksDataBase() throws IOException {
		Properties properties = new Properties();
		String filename = "test"+Integer.toString(count++)+".xml";
		properties.put("data.access.filePath", filename);
		(new File(filename)).delete();
		return new XMLBooksDataBase(properties);
	}
	
	@Before
	public void setUp() throws IOException {
		mockedBook = new Book(BOOK_AUTHOR,BOOK_TITLE);
		testedClassBooks = getTestedBooksDataBase();
		testedClassBooks.addBook(mockedBook);
	}

	@Test
	public void testAddBook() {
		assertEquals(mockedBook,
				testedClassBooks.getBook(BOOK_AUTHOR, BOOK_TITLE));
	}

	@Test
	public void testRemoveBook() {
		testedClassBooks.removeBook(mockedBook);
		assertNull(testedClassBooks.getBook(BOOK_AUTHOR, BOOK_TITLE));
		assertNull(testedClassBooks.getBookByID(mockedBook.getId()));
	}

	@Test
	public void testGetBookByID() {
		assertEquals(mockedBook,
				testedClassBooks.getBookByID(mockedBook.getId()));
	}

	@Test
	public void testGetBooks() {
		Book secondBook = new Book("Another Author","Another Title");
		testedClassBooks.addBook(secondBook);
	
		List<Book> booksList = testedClassBooks.getAllBooks();
		assertTrue(booksList.contains(mockedBook));
		assertTrue(booksList.contains(secondBook));
	}
}
