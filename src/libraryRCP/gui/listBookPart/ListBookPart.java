package libraryRCP.gui.listBookPart;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import libraryRCP.data.book.model.Book;
import libraryRCP.gui.MyEventConstants;
import libraryRCP.jobs.EventBrokerStatusChangeListener;
import libraryRCP.jobs.StatusCheckerJob;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ListBookPart {

    public static final String ID = "libraryRCP.gui.ListBookPart";
    private ListViewer viewer;

    @Inject private IEventBroker eventBroker;
    @Inject private IEclipseContext eclipseContext;

    @PostConstruct
    @Inject
    public void createPartControl(Composite parent) {
        viewer = new ListViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(new ListContentProvider());
        viewer.setLabelProvider(new ListItemBooksLabelProvider());
        BookModel bookModel = ContextInjectionFactory.make(BookModel.class, eclipseContext);
        viewer.setInput(bookModel);

        EventBrokerStatusChangeListener listener = ContextInjectionFactory.make(
                EventBrokerStatusChangeListener.class, eclipseContext);
        StatusCheckerJob.getInstance().setOnBookStatusChangeListener(listener);

        viewer.addDoubleClickListener(new IDoubleClickListener() {

            @Override
            public void doubleClick(DoubleClickEvent event) {
                IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
                Book selectedBook = (Book) thisSelection.getFirstElement();
                eventBroker.send(MyEventConstants.TOPIC_BOOK_SELECTED, selectedBook);
            }
        });
    }

    @Focus
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    @Inject
    @Optional
    private void getNotified(@UIEventTopic(MyEventConstants.TOPIC_BOOKS_DATA_MODIFIED) Book book) {
        System.out.println("notified");
        viewer.refresh();
    }
}