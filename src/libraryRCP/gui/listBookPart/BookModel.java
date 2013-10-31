package libraryRCP.gui.listBookPart;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import libraryRCP.data.OnChangeDataListener;
import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepository;
import libraryRCP.data.book.model.BookRepositoryFactory;
import libraryRCP.gui.MyEventConstants;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

public class BookModel implements OnChangeDataListener {

	@Inject
	private IEventBroker eventBroker;

	public List<Book> getBooks() {
		List<Book> books = new LinkedList<>();
		BookRepository repository = BookRepositoryFactory.getInstance();
		repository.registerOnChangeDataListener(this);
		books = repository.getAllBooks();

		return books;
	}

	@Override
	public void onDataChange(Book changedBook) {
		eventBroker.post(MyEventConstants.TOPIC_BOOKS_DATA_MODIFIED, new Object());
	}
}
