package libraryRCP.handlers;

import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepositoryFactory;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MContext;

public class DeleteBookHandler {

	@Execute
	public void execute(Book bookToDelete) {
		BookRepositoryFactory.getInstance().removeBook(bookToDelete.getId());
	}

	@CanExecute
	public boolean canExecute(MContext context) {
		if (context == null) {
			return false;
		}
		Book bookToDelete = (Book) context.getContext().get(Book.class);
		System.out.println("I'm deleting book "+bookToDelete);
		return bookToDelete != null;
	}

}