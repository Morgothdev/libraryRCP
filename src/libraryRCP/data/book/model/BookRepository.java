package libraryRCP.data.book.model;

import java.util.List;

import libraryRCP.data.OnChangeDataListener;

public interface BookRepository {

	public void addBook(Book newBook);

	public void removeBook(long l);

	public Book getBookByID(long bookID);

	public Book getBook(String author, String title);

	public List<Book> getAllBooks();

	public void updateBook(Book bookToUpdate);

	public void registerOnChangeDataListener(OnChangeDataListener listenerToRegister);

	public void removeOnChangeDataListener(Object listenerToRemove);

	public void notifyOnChangeDataListeners();
}
