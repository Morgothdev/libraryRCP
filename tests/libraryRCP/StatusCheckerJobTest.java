package libraryRCP;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import libraryRCP.data.Book;
import libraryRCP.data.Books;
import libraryRCP.data.BooksDataBase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


public class StatusCheckerJobTest {

	StatusCheckerJob tested;
	@Mock Books mockedBooks;
	@Mock Book mockedBook;
	Map<Long,Book> listWithMockedBooks =  new HashMap<>();
	@Mock OnBookStatusChangedListener mockedListener;
	
	@Before
	public void setUp() throws Exception {
		Book mocked = mock(Book.class);
		when(mocked.getId()).thenReturn(new Long(1));
		when(mocked.getStatus()).thenReturn(Book.STATUS.AVAIBLE);
		listWithMockedBooks.put(new Long(1),mocked);
		
		mocked = mock(Book.class);
		when(mocked.getId()).thenReturn(new Long(2));
		when(mocked.getStatus()).thenReturn(Book.STATUS.AVAIBLE);
		listWithMockedBooks.put(new Long(2),mocked);
		
		mockedBook = mock(Book.class);
		when(mockedBook.getId()).thenReturn(new Long(3));
		when(mockedBook.getStatus()).thenReturn(Book.STATUS.AVAIBLE);
		listWithMockedBooks.put(new Long(3),mocked);
		
		mockedBooks = mock(BooksDataBase.class);
		
		when(mockedBooks.getAllBooks()).thenReturn(new LinkedList<Book>(listWithMockedBooks.values()));
		
		mockedListener = mock(OnBookStatusChangedListener.class);
		
		tested = new StatusCheckerJob("test job", mockedBooks);
		tested.setOnBookStatusChangeListener(mockedListener);
	}

	@Test
	public void testReactionOnChangedStatusOfOneBook() {
		listWithMockedBooks.remove(new Long(3));
		when(mockedBook.getStatus()).thenReturn(Book.STATUS.LOANED);
		listWithMockedBooks.put(new Long(3), mockedBook);
		when(mockedBooks.getAllBooks()).thenReturn(new LinkedList<Book>(listWithMockedBooks.values()));
		tested.run(null);
		verify(mockedListener, atLeastOnce()).onChange(mockedBook);
	}

}
