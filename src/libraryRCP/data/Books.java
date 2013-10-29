package libraryRCP.data;

import java.util.List;

public interface Books {

	public void addBook(Book newBook);

	public void removeBook(Book bookToDelete);

	public Book getBookByID(long bookID);

	public Book getBook(String author, String title);

	public List<Book> getAllBooks();

	public void updateBook(Book bookToUpdate);

	public void registerOnChangeDataListener(OnChangeDataListener listenerToRegister);

	public void removeOnChangeDataListener(Object listenerToRemove);

	public void notifyOnChangeDataListeners();
}
