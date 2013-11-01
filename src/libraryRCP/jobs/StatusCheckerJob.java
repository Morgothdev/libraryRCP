package libraryRCP.jobs;

import java.util.HashMap;
import java.util.Map;

import libraryRCP.data.OnChangeDataListener;
import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepository;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class StatusCheckerJob extends Job implements OnChangeDataListener {

    private static StatusCheckerJob instance;

    public static StatusCheckerJob getInstance(String name, BookRepository booksInstance) {
        if (instance == null) {
            instance = new StatusCheckerJob(name, booksInstance);
        }
        return instance;
    }

    public static StatusCheckerJob getInstance() {
        return instance;
    }

    private BookRepository books;
    private Map<Long, Book.STATUS> mapOfBookStatuses = new HashMap<>();
    private OnBookStatusChangedListener onBookStatusChangeListener;

    private StatusCheckerJob(String name, BookRepository booksInstance) {
        super(name);
        books = booksInstance;
        booksInstance.registerOnChangeDataListener(this);
        loadMapOfStatuses();
    }

    private void loadMapOfStatuses() {
        for (Book book : books.getAllBooks()) {
            mapOfBookStatuses.put(book.getId(), book.getStatus());
        }
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
        System.out.println("scheduler run");
        try {
            for (Book book : books.getAllBooks()) {
                Book.STATUS previousStatus = mapOfBookStatuses.get(book.getId());
                Book.STATUS actualStatus = book.getStatus();
                if (actualStatus != previousStatus) {
                    mapOfBookStatuses.put(book.getId(), book.getStatus());
                    whenBookStatusChanged(book);
                    System.out.println("Status of book " + book.getId() + " changed to "
                            + book.getStatus());
                }
            }
            return Status.OK_STATUS;
        } finally {
            schedule(2000);
        }
    }

    private void whenBookStatusChanged(Book book) {
        if (onBookStatusChangeListener != null) {
            onBookStatusChangeListener.onChange(book);
        }
    }
}
