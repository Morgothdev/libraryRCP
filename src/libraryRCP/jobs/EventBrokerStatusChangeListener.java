package libraryRCP.jobs;

import javax.inject.Inject;

import libraryRCP.data.book.model.Book;
import libraryRCP.gui.MyEventConstants;

import org.eclipse.e4.core.services.events.IEventBroker;

public class EventBrokerStatusChangeListener implements OnBookStatusChangedListener {

    @Inject private IEventBroker eventBroker;

    @Override
    public void onChange(Book changedBook) {
        eventBroker.post(MyEventConstants.TOPIC_BOOKS_DATA_MODIFIED, changedBook);
    }

}
