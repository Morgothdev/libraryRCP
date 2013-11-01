package libraryRCP.gui.listBookPart;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import libraryRCP.data.book.model.Book;
import libraryRCP.gui.MyEventConstants;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ListBookPart {

	public static final String ID = "libraryRCP.gui.ListBookPart";
	private ListViewer viewer;

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private IEclipseContext eclipseContext;

	@PostConstruct
	@Inject
	public void createPartControl(Composite parent) {
		viewer = new ListViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ListContentProvider());
		viewer.setLabelProvider(new ListItemBooksLabelProvider());
		BookModel bookModel = ContextInjectionFactory.make(BookModel.class, eclipseContext);
		viewer.setInput(bookModel);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Book selectedBook = (Book) thisSelection.getFirstElement();
				eventBroker.post(MyEventConstants.TOPIC_BOOK_SELECTED, selectedBook);
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