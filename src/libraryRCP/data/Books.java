package libraryRCP.data;

public interface Books {
	
	public void addBook(Book newBook);
	public void removeBook(Book bookToDelete);
	public Book getBookByID(long bookID);
	public Book getBook(String author, String title);
}
