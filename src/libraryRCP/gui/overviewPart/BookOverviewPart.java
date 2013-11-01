package libraryRCP.gui.overviewPart;

import java.util.Calendar;

import javax.inject.Inject;

import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.Book.STATUS;
import libraryRCP.data.book.model.BookRepositoryFactory;
import libraryRCP.gui.MyEventConstants;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;

public class BookOverviewPart {

    private Label titleLabel;
    private Label authorLabel;
    private Label yearOfPublicationLabel;
    private Composite parent;

    private Book overviewedBook;
    @Inject protected IEventBroker eventBroker;

    private Combo statusWidget;
    private DateTime dateOfReturnWidget;
    private DateTime loanedDateWidget;

    @Inject private MDirtyable dirty;

    @Inject
    public BookOverviewPart(Composite parent) {
        this.parent = parent;

        GridLayout layout = new GridLayout(2, true);
        parent.setLayout(layout);

        Label label;

        label = new Label(parent, SWT.None);
        label.setText("Title:");
        titleLabel = new Label(parent, SWT.None);

        label = new Label(parent, SWT.None);
        label.setText("Author:");
        authorLabel = new Label(parent, SWT.None);

        label = new Label(parent, SWT.None);
        label.setText("Year of publication:");
        yearOfPublicationLabel = new Label(parent, SWT.None);

        label = new Label(parent, SWT.None);
        label.setText("Loaned date:");
        loanedDateWidget = new DateTime(parent, SWT.None);
        setDateTimeContainsEmptyDate(loanedDateWidget);

        label = new Label(parent, SWT.None);
        label.setText("Date of return:");
        dateOfReturnWidget = new DateTime(parent, SWT.None);
        setDateTimeContainsEmptyDate(dateOfReturnWidget);

        label = new Label(parent, SWT.None);
        label.setText("Status:");
        statusWidget = new Combo(parent, SWT.None);
        statusWidget.setBounds(50, 50, 150, 65);
        setComboContainsEmptyStatus(statusWidget);

        Button saveButton = new Button(parent, SWT.CENTER);
        saveButton.setText("Save");
        saveButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String selectedStatus = statusWidget.getItem(statusWidget.getSelectionIndex());
                overviewedBook.setStatus(Book.STATUS.valueOf(selectedStatus.toUpperCase()));
                BookRepositoryFactory.getInstance().updateBook(overviewedBook);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }

    @Inject
    @Optional
    private void getNotifiedOnSelected(
            @UIEventTopic(MyEventConstants.TOPIC_BOOK_SELECTED) Book selectedBook) {
        updateControls(selectedBook);
    }

    @Inject
    @Optional
    private void getNotifiedOnUpdated(
            @UIEventTopic(MyEventConstants.TOPIC_BOOKS_DATA_MODIFIED) Book selectedBook) {
        if (selectedBook.equals(overviewedBook)) {
            updateControls(selectedBook);
        }
    }

    private void updateControls(Book selectedBook) {
        overviewedBook = selectedBook;

        titleLabel.setText(selectedBook.getTitle());
        titleLabel.redraw();
        authorLabel.setText(selectedBook.getAuthor());
        authorLabel.redraw();
        yearOfPublicationLabel.setText(selectedBook.getYearOfPublication().toString());
        yearOfPublicationLabel.redraw();

        setCompositeContainsDate(loanedDateWidget, selectedBook.getLoanedDate());
        setCompositeContainsDate(dateOfReturnWidget, selectedBook.getDateOfReturn());
        setCompositeContainsStatus(statusWidget, selectedBook.getStatus());

        parent.layout();
        parent.redraw();
        parent.pack(true);
    }

    private void setCompositeContainsStatus(Combo comboWidget, STATUS status) {
        if (status == null) {
            setComboContainsEmptyStatus(comboWidget);
        } else {
            setComboContainsFullStatus(comboWidget, status);
        }
    }

    private void setComboContainsFullStatus(Combo comboWidget, STATUS status) {
        comboWidget.setEnabled(true);
        STATUS[] statuses = Book.STATUS.values();
        String[] statusesNames = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusesNames[i] = statuses[i].name().toLowerCase();
        }
        comboWidget.setItems(statusesNames);
        comboWidget.select(comboWidget.indexOf(status.name().toLowerCase()));
    }

    private void setComboContainsEmptyStatus(Combo comboWidget) {
        comboWidget.setEnabled(false);
    }

    private void setCompositeContainsDate(DateTime dateTimeWidget, Calendar date) {
        if (date == null) {
            setDateTimeContainsEmptyDate(dateTimeWidget);
        } else {
            setDateTimeContainsFullDate(dateTimeWidget, date);
        }
    }

    private void setDateTimeContainsFullDate(DateTime dateTimeWidget, Calendar date) {
        dateTimeWidget.setEnabled(true);
        dateTimeWidget.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH));
    }

    private void setDateTimeContainsEmptyDate(DateTime dateTimeWidget) {
        dateTimeWidget.setEnabled(false);
    }
}