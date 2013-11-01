package libraryRCP.data.book.model.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import libraryRCP.data.book.model.Book;

public class ClassBooksRepository extends BooksRepositoryBase {

    private Map<Long, Book> db = new HashMap<Long, Book>();
    long maxID = 0;

    public ClassBooksRepository(Properties properties) {
        super(properties);
    }

    @Override
    public synchronized void addBook(Book newBook) {
        long bookID = maxID++;
        newBook.setId(bookID);
        db.put(bookID, newBook);
        notifyOnChangeDataListeners(null);
    }

    @Override
    public synchronized void removeBook(Book bookToRemove) {
        db.remove(bookToRemove.getId());
        notifyOnChangeDataListeners(null);
    }

    @Override
    public void updateBook(Book bookToUpdate) {
        notifyOnChangeDataListeners(null);
    }

    @Override
    public synchronized Book getBookByID(long bookID) {
        return db.get(bookID);
    }

    @Override
    public synchronized Book getBook(String author, String title) {
        for (Entry<Long, Book> book : db.entrySet()) {
            if (book.getValue().getAuthor().equals(author)
                    && book.getValue().getTitle().equals(title)) {
                return book.getValue();
            }
        }
        return null;
    }

    @Override
    public synchronized List<Book> getAllBooks() {
        return new ArrayList<Book>(db.values());
    }
}
