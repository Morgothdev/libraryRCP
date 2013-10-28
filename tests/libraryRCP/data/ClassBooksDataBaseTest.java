package libraryRCP.data;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.And;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ClassBooksDataBaseTest {

	private static final String BOOK_AUTHOR = "Author of Book";
	private static final String BOOK_TITLE = "Title of Book";
	@Mock
	private Book mockedBook;
	private ClassBooksDataBase testedClassBooks;

	@Before
	public void setUp() throws Exception {
		mockedBook = mock(Book.class);
		when(mockedBook.getAuthor()).thenReturn(BOOK_AUTHOR);
		when(mockedBook.getTitle()).thenReturn(BOOK_TITLE);
		testedClassBooks = new ClassBooksDataBase(new Properties());
		testedClassBooks.addBook(mockedBook);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("called with arguments "
						+ invocation.getArguments());
				return null;
			}
		}).when(mockedBook).setId(anyLong());
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
}
