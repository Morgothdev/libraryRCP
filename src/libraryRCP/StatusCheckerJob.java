package libraryRCP;

import java.util.HashMap;
import java.util.Map;

import libraryRCP.data.Book;
import libraryRCP.data.Books;
import libraryRCP.data.OnChangeDataListener;
import libraryRCP.data.Book.STATUS;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;

public class StatusCheckerJob extends Job implements OnChangeDataListener {

	private Books books;
	private Map<Long, Book.STATUS> mapOfBookStatuses = new HashMap<>();
	private OnBookStatusChangedListener onBookStatusChangeListener;

	public StatusCheckerJob(String name, Books booksInstance) {
		super(name);
		books = booksInstance;
		loadMapOfStatuses();
	}

	protected void loadMapOfStatuses() {
		for (Book book : books.getAllBooks()) {
			mapOfBookStatuses.put(book.getId(), book.getStatus());
		}
	}

	@Override
	public synchronized void onDataChange() {
		loadMapOfStatuses();
	}

	@Override
	protected synchronized IStatus run(IProgressMonitor monitor) {
		Logger log = LogManager.getLogger(getClass());
		log.entry();
		try {
			for (Book book : books.getAllBooks()) {
				Book.STATUS previousStatus = mapOfBookStatuses.get(book.getId());
				Book.STATUS actualStatus = book.getStatus();
				if (actualStatus != previousStatus) {
					mapOfBookStatuses.put(book.getId(), book.getStatus());
					whenBookStatusChanged(book);
					log.info("Status of book {} changed to {}", book.getId(), book.getStatus());
				}
			}
			return Status.OK_STATUS;
		} finally {
			schedule(1000);
		}
	}

	private void whenBookStatusChanged(Book book) {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("Status of book id:").append(book.getId()).append("  is canged to ").append(book.getStatus());
//		MessageDialog.openInformation(null, "Book status changed",messageBuilder.toString());
		onBookStatusChangeListener.onChange(book);
	}

	public void setOnBookStatusChangeListener(OnBookStatusChangedListener onBookStatusChangeListener) {
		this.onBookStatusChangeListener = onBookStatusChangeListener;
	}
}
