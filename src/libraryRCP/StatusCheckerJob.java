package libraryRCP;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import libraryRCP.data.OnChangeDataListener;
import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepository;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;

public class StatusCheckerJob extends Job implements OnChangeDataListener {

	private BookRepository books;
	private Map<Long, Book.STATUS> mapOfBookStatuses = new HashMap<>();
	private OnBookStatusChangedListener onBookStatusChangeListener;

	public StatusCheckerJob(String name, BookRepository booksInstance) {
		super(name);
		books = booksInstance;
		loadMapOfStatuses();
		setDefaultOnStatusChangedListener();
	}

	protected void loadMapOfStatuses() {
		for (Book book : books.getAllBooks()) {
			mapOfBookStatuses.put(book.getId(), book.getStatus());
		}
	}

	private void setDefaultOnStatusChangedListener() {
		onBookStatusChangeListener = new OnBookStatusChangedListener() {

			@Override
			public void onChange(Book changedBook) {
				StringBuilder messageBuilder = new StringBuilder();
				messageBuilder.append("Status of book id:").append(changedBook.getId())
						.append("  is canged to ").append(changedBook.getStatus());
				MessageDialog.openInformation(null, "Book status changed",
						messageBuilder.toString());
			}
		};
	}

	public void setOnBookStatusChangeListener(OnBookStatusChangedListener onBookStatusChangeListener) {
		this.onBookStatusChangeListener = onBookStatusChangeListener;
	}

	@Override
	public synchronized void onDataChange(Book changedBook) {
		loadMapOfStatuses();
	}

	@Override
	protected synchronized IStatus run(IProgressMonitor monitor) {
		Logger log = Logger.getLogger(getClass().getName());
		try {
			for (Book book : books.getAllBooks()) {
				Book.STATUS previousStatus = mapOfBookStatuses.get(book.getId());
				Book.STATUS actualStatus = book.getStatus();
				if (actualStatus != previousStatus) {
					mapOfBookStatuses.put(book.getId(), book.getStatus());
					whenBookStatusChanged(book);
					log.info("Status of book " + book.getId() + " changed to " + book.getStatus());
				}
			}
			return Status.OK_STATUS;
		} finally {
			schedule(1000);
		}
	}

	private void whenBookStatusChanged(Book book) {
		onBookStatusChangeListener.onChange(book);
	}
}
