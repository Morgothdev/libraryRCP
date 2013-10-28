package libraryRCP.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class ClassBooksDataBase extends AbstractBooksDataBase {

	private Map<Long, Book> db = new HashMap<Long, Book>();
	long maxID = 0;
	
	
	public ClassBooksDataBase(Properties properties) {
		super(properties);
	}

	@Override
	public void addBook(Book newBook) {
		newBook.setId(maxID++);
		db.put(newBook.getId(), newBook);
	}

	@Override
	public void removeBook(Book bookToDelete) {
		db.remove(bookToDelete.getId());
	}

	@Override
	public Book getBookByID(long bookID) {
		return db.get(bookID);
	}

	@Override
	public Book getBook(String author, String title) {
		for(Entry<Long, Book> book : db.entrySet()){
			if(book.getValue().getAuthor().equals(author)&& book.getValue().getTitle().equals(title)){
				return book.getValue();
			}
		}
		return null;
	}

}
