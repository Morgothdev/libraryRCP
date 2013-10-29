package libraryRCP.gui.listBookPart;

import java.util.LinkedList;
import java.util.List;

import libraryRCP.data.Book;
import libraryRCP.data.BookManager;

public class BookModel {

	public List<Book> getBooks() {
		List<Book> books = new LinkedList<>();
		BookManager.configure();
		books = BookManager.getInstance().getAllBooks();
		return books;
	}

}
