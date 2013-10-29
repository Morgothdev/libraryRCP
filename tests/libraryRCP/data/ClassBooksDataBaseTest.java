package libraryRCP.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ClassBooksDataBaseTest extends BooksDataBaseTest {
	
	@Mock
	protected Book mockedBook;
	@Mock
	protected Book secondMockedBook = mock(Book.class);
	protected Books testedClassBooks;

	protected Books getTestedBooksDataBase(){
		return new ClassBooksDataBase(new Properties());
	}

	@Before
	public void setUp() throws Exception {
		mockedBook = mock(Book.class);
		when(mockedBook.getAuthor()).thenReturn(BOOK_AUTHOR);
		when(mockedBook.getTitle()).thenReturn(BOOK_TITLE);
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
		when(secondMockedBook.getAuthor()).thenReturn("Another Author");
		when(secondMockedBook.getTitle()).thenReturn("Another Title");
		testedClassBooks.addBook(secondMockedBook);
	
		List<Book> booksList = testedClassBooks.getAllBooks();
		assertTrue(booksList.contains(mockedBook));
		assertTrue(booksList.contains(secondMockedBook));
	}
}
