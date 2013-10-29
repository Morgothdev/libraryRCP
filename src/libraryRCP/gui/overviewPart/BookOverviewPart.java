package libraryRCP.gui.overviewPart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import libraryRCP.data.Book;
import libraryRCP.gui.MyEventConstants;

import org.apache.logging.log4j.LogManager;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class BookOverviewPart {

	private Label titleLabel;
	private Label authorLabel;
	private Label statusLabel;
	private Label yearOfPublicationLabel;
	private Label loanedDateLabel;
	private Label dateOfReturnLabel;
	private Composite parent;

	@Inject
	public BookOverviewPart(Composite parent) {
		this.parent = parent;
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);

		Label label;

		label = new Label(parent, SWT.None);
		label.setText("Title:");
		titleLabel = new Label(parent, SWT.None);

		label = new Label(parent, SWT.None);
		label.setText("Author:");
		authorLabel = new Label(parent, SWT.None);

		label = new Label(parent, SWT.None);
		label.setText("Status: ");
		statusLabel = new Label(parent, SWT.None);

		label = new Label(parent, SWT.None);
		label.setText("Year of publication:");
		yearOfPublicationLabel = new Label(parent, SWT.None);

		label = new Label(parent, SWT.None);
		label.setText("Loaned date:");
		loanedDateLabel = new Label(parent, SWT.None);

		label = new Label(parent, SWT.None);
		label.setText("Date of return:");
		dateOfReturnLabel = new Label(parent, SWT.None);

	}

	@Focus
	public void onFocus() {

	}

	@Inject
	@Optional
	private void getNotified(@UIEventTopic(MyEventConstants.TOPIC_BOOK_SELECTED) Book selectedBook) {
		LogManager.getLogger(getClass()).info("selected {}", selectedBook.getId());
		titleLabel.setText(selectedBook.getTitle());
		authorLabel.setText(selectedBook.getAuthor());
		statusLabel.setText(selectedBook.getStatus().name().toLowerCase());
		yearOfPublicationLabel.setText(selectedBook.getYearOfPublication().toString());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY MM DD", Locale.ENGLISH);
		Date loanedDate = selectedBook.getLoanedDate();
		String dateString = (loanedDate != null) ? simpleDateFormat.format(loanedDate)
				: "---------";
		loanedDateLabel.setText(dateString);
		Date dateOfReturn = selectedBook.getDateOfReturn();
		String returnDateString = (dateOfReturn != null) ? simpleDateFormat.format(dateOfReturn)
				: "---------";
		dateOfReturnLabel.setText(returnDateString);
		parent.redraw();
		parent.pack(true);
	}

}