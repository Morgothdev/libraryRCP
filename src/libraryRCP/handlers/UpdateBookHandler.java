package libraryRCP.handlers;

import javax.inject.Inject;

import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepositoryFactory;
import libraryRCP.gui.MyEventConstants;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

public class UpdateBookHandler {

    @Execute
    public void execute(Book bookToUpdate) {
        updateBook(bookToUpdate);
    }

    @CanExecute
    public boolean canExecute(Book bookToUpdate) {
        System.out.println("can update execute: " + bookToUpdate != null);
        return bookToUpdate != null;
    }

    @Inject
    @Optional
    public void getNotified(@UIEventTopic(MyEventConstants.TOPIC_BOOK_UPDATE) Book bookToUpdate) {
        updateBook(bookToUpdate);
    }

    private void updateBook(Book bookToUpdate) {
        // TODO logger
        System.out.println("I'm updatingBook book " + bookToUpdate);
        BookRepositoryFactory.getInstance().updateBook(bookToUpdate);
    }

}
