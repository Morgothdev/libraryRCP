package libraryRCP.data.book.model.internal;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import libraryRCP.data.OnChangeDataListener;
import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepository;

public abstract class BooksRepositoryBase implements BookRepository {

    private Set<OnChangeDataListener> listeners = new HashSet<>();

    public BooksRepositoryBase(Properties properties) {
    }

    @Override
    public void registerOnChangeDataListener(OnChangeDataListener listenerToRegister) {
        if (listenerToRegister == null) {
            System.out.println("rejestrowanie nulla jako listenera");
        } else {
            listeners.add(listenerToRegister);
        }
    }

    @Override
    public void removeOnChangeDataListener(Object listenerToRemove) {
        listeners.remove(listenerToRemove);
    }

    @Override
    public void notifyOnChangeDataListeners(Book changedBook) {
        for (OnChangeDataListener listener : listeners) {
            listener.onDataChange(changedBook);
        }
    }
}
