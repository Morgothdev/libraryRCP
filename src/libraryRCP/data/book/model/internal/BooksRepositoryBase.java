package libraryRCP.data.book.model.internal;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;

import libraryRCP.data.OnChangeDataListener;
import libraryRCP.data.book.model.BookRepository;
import libraryRCP.gui.MyEventConstants;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.events.EventBrokerFactory;
import org.eclipse.e4.ui.services.internal.events.EventBroker;

public abstract class BooksRepositoryBase implements BookRepository {

	private Set<OnChangeDataListener> listeners = new HashSet<>();

	@Inject
	private IEventBroker eventBroker;

	public BooksRepositoryBase(Properties properties) {
	}

	@Override
	public void registerOnChangeDataListener(OnChangeDataListener listenerToRegister) {
		listeners.add(listenerToRegister);
	}

	@Override
	public void removeOnChangeDataListener(Object listenerToRemove) {
		listeners.remove(listenerToRemove);
	}

	@Override
	public void notifyOnChangeDataListeners() {
		for (OnChangeDataListener listener : listeners) {
			listener.onDataChange();
		}
		eventBroker.post(MyEventConstants.TOPIC_BOOKS_DATA_MODIFIED, new Object());
	}
}
