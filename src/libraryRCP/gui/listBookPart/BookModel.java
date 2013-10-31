package libraryRCP.gui.listBookPart;

import java.util.LinkedList;
import java.util.List;

import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepositoryFactory;

public class BookModel {

	public List<Book> getBooks() {
		List<Book> books = new LinkedList<>();
		books = BookRepositoryFactory.getInstance().getAllBooks();
		return books;
	}

}
