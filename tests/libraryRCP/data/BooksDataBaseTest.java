package libraryRCP.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class BooksDataBaseTest {

	private static final String BOOK_AUTHOR = "Author of Book";
	private static final String BOOK_TITLE = "Title of Book";
	@Mock
	private Book mockedBook;
	@Mock
	private Book secondMockedBook = mock(Book.class);
	private BooksDataBase testedClassBooks;

	public BooksDataBaseTest() {
		super();
	}

	protected BooksDataBase getTestedBooksDataBase() {
		return null;
	}
	
	@Before
	public void setUp() throws Exception {
		mockedBook = mock(Book.class);
		when(mockedBook.getAuthor()).thenReturn(BOOK_AUTHOR);
		when(mockedBook.getTitle()).thenReturn(BOOK_TITLE);
		testedClassBooks = getTestedBooksDataBase();
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("called with arguments "
						+ invocation.getArguments());
				return null;
			}
		}).when(mockedBook).setId(anyLong());
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
		
		List<Book> booksList = testedClassBooks.getBooks();
		assertTrue(booksList.contains(mockedBook));
		assertTrue(booksList.contains(secondMockedBook));		
	}

}