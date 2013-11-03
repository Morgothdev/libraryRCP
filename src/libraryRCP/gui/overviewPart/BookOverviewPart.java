package libraryRCP.gui.overviewPart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.Book.STATUS;
import libraryRCP.data.book.model.BookRepositoryFactory;
import libraryRCP.gui.MyEventConstants;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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

    private Label statusWidget;
    private Label dateOfReturnWidget;
    private DateTime loanedDateWidget;

    private Button loanButton;
    private Button returnedButton;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);

    private Color redColor;
    private Color blackColor;

    @Inject
    public BookOverviewPart(Composite parent) {
        this.parent = parent;
        GridLayout layout = new GridLayout(3, true);
        parent.setLayout(layout);

        createTitleControls(parent);
        createAuthorControls(parent);
        createYearOfPublicationControls(parent);
        createBookStatusControls(parent);
        createLoanDateControls(parent);
        createDateOfReturnControls(parent);
    }

    private void createTitleControls(Composite parent) {
        Label label;
        label = new Label(parent, SWT.None);
        label.setText("Title:");
        titleLabel = new Label(parent, SWT.None);
        new Label(parent, SWT.None);
        setEmptyTitleControls();
    }

    private void createAuthorControls(Composite parent) {
        Label label;
        label = new Label(parent, SWT.None);
        label.setText("Author:");
        authorLabel = new Label(parent, SWT.None);
        new Label(parent, SWT.None);
        setEmptyAuthorControls();
    }

    private void createYearOfPublicationControls(Composite parent) {
        Label label;
        label = new Label(parent, SWT.None);
        label.setText("Year of publication:");
        yearOfPublicationLabel = new Label(parent, SWT.None);
        new Label(parent, SWT.None);
        setEmptyYearOfPublicationControls();
    }

    private void createBookStatusControls(Composite parent) {
        Label label;
        label = new Label(parent, SWT.None);
        label.setText("Status:");
        statusWidget = new Label(parent, SWT.None);
        new Label(parent, SWT.None);
        setEmptyStatusControls();
    }

    private void createLoanDateControls(Composite parent) {
        Label label;
        label = new Label(parent, SWT.None);
        label.setText("Loaned date:");
        loanedDateWidget = new DateTime(parent, SWT.CALENDAR);
        loanButton = new Button(parent, SWT.None);
        loanButton.setText("Loan");
        loanButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Calendar newLoanedDate = new GregorianCalendar(loanedDateWidget.getYear(),
                        loanedDateWidget.getMonth(), loanedDateWidget.getDay());
                overviewedBook.setLoanedDate(newLoanedDate);
                overviewedBook.setDateOfReturn(getReturnDateFromLoanedDate(newLoanedDate));
                overviewedBook.setStatus(STATUS.LOANED);
                BookRepositoryFactory.getInstance().updateBook(overviewedBook);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        setEmptyLoanControls();
    }

    private void createDateOfReturnControls(Composite parent) {
        Label label;
        label = new Label(parent, SWT.None);
        label.setText("Date of return:");

        dateOfReturnWidget = new Label(parent, SWT.None);
        redColor = new Color(dateOfReturnWidget.getDisplay(), 255, 0, 0);
        blackColor = new Color(dateOfReturnWidget.getDisplay(), 0, 0, 0);
        dateOfReturnWidget.addDisposeListener(new DisposeListener() {

            @Override
            public void widgetDisposed(DisposeEvent e) {
                blackColor.dispose();
                redColor.dispose();
            }
        });

        returnedButton = new Button(parent, SWT.None);
        returnedButton.setText("Return");
        returnedButton.setText("Return");
        returnedButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                overviewedBook.setStatus(Book.STATUS.AVAIBLE);
                overviewedBook.setDateOfReturn(null);
                overviewedBook.setLoanedDate(null);
                BookRepositoryFactory.getInstance().updateBook(overviewedBook);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
        setEmptyReturnControls();
    }

    private void setEmptyTitleControls() {
        titleLabel.setText("----------");
    }

    private void setEmptyAuthorControls() {
        authorLabel.setText("----------");
    }

    private void setEmptyYearOfPublicationControls() {
        yearOfPublicationLabel.setText("----------");
    }

    private void setEmptyStatusControls() {
        statusWidget.setText("----------");
    }

    private void setEmptyLoanControls() {
        loanedDateWidget.setEnabled(false);
        loanButton.setEnabled(false);
    }

    private void setEmptyReturnControls() {
        dateOfReturnWidget.setText("----------");
        dateOfReturnWidget.setForeground(blackColor);
        returnedButton.setEnabled(false);
    }

    private void updateTitleControls(Book selectedBook) {
        titleLabel.setText(selectedBook.getTitle());
    }

    private void updateAuthorControls(Book selectedBook) {
        authorLabel.setText(selectedBook.getAuthor());
    }

    private void updateYearOfPublicationControls(Book selectedBook) {
        yearOfPublicationLabel.setText(selectedBook.getYearOfPublication().toString());
    }

    private void updateBookStatusControls() {
        statusWidget.setText(overviewedBook.getStatus().name());
    }

    private void updateLoanedControls() {
        if (overviewedBook.getStatus().equals(Book.STATUS.AVAIBLE)) {
            Calendar timeNow = Calendar.getInstance();
            loanedDateWidget.setDate(timeNow.get(Calendar.YEAR), timeNow.get(Calendar.MONTH),
                    timeNow.get(Calendar.DAY_OF_MONTH));
            loanButton.setEnabled(true);
            loanedDateWidget.setEnabled(true);
        } else {
            loanedDateWidget.setDate(overviewedBook.getLoanedDate().get(Calendar.YEAR),
                    overviewedBook.getLoanedDate().get(Calendar.MONTH), overviewedBook
                            .getLoanedDate().get(Calendar.DAY_OF_MONTH));
            loanButton.setEnabled(false);
            loanedDateWidget.setEnabled(false);
        }
    }

    private void updateReturnContols() {
        if (overviewedBook.getStatus().equals(Book.STATUS.LOANED)) {
            dateOfReturnWidget.setText(dateFormatter.format(overviewedBook.getDateOfReturn()
                    .getTime()));
            Calendar timeNow = Calendar.getInstance();
            if (timeNow.after(overviewedBook.getDateOfReturn())) {
                dateOfReturnWidget.setForeground(redColor);
            } else {
                dateOfReturnWidget.setForeground(blackColor);
            }
            returnedButton.setEnabled(true);
        } else {
            setEmptyReturnControls();
        }
    }

    @Inject
    @Optional
    private void getNotifiedOnSelected(
            @UIEventTopic(MyEventConstants.TOPIC_BOOK_SELECTED) Book selectedBook) {
        if (selectedBook != null) {
            updateControls(selectedBook);
        } else {
           setEmptyControls();
        }
    }

    @Inject
    @Optional
    private void getNotifiedOnUpdated(
            @UIEventTopic(MyEventConstants.TOPIC_BOOKS_DATA_MODIFIED) Book selectedBook) {
        if (selectedBook != null && selectedBook.equals(overviewedBook)) {
            updateControls(selectedBook);
        }
    }

    private void updateControls(Book selectedBook) {
        overviewedBook = selectedBook;

        updateTitleControls(selectedBook);
        updateAuthorControls(selectedBook);
        updateYearOfPublicationControls(selectedBook);

        updateLoanedControls();
        updateReturnContols();
        updateBookStatusControls();

        parent.layout();
        parent.redraw();
    }
    
    private void setEmptyControls(){
        setEmptyTitleControls();
        setEmptyAuthorControls();
        setEmptyYearOfPublicationControls();
        setEmptyStatusControls();
        setEmptyLoanControls();
        setEmptyReturnControls();
    }

    private Calendar getReturnDateFromLoanedDate(Calendar loanedDate) {
        Calendar returnDate = (Calendar) loanedDate.clone();
        returnDate.add(Calendar.MONTH, 1);
        return returnDate;
    }
}