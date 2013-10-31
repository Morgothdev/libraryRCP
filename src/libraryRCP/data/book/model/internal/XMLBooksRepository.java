package libraryRCP.data.book.model.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import libraryRCP.MyWorkspaceFilesRepositor;
import libraryRCP.data.book.model.Book;

import org.eclipse.core.runtime.IPath;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XMLBooksRepository extends BooksRepositoryBase {

	private long maxID = 0;
	private XStream xStream = new XStream(new StaxDriver());
	private File xmlFile;

	public XMLBooksRepository(Properties properties) throws IOException {
		super(properties);
		String pathname = (String) properties.get("data.access.filePath");
		System.out.println("pathname: " + pathname);
		IPath dataLocation = MyWorkspaceFilesRepositor.getDataLocation();
		xmlFile = dataLocation.append(pathname).toFile();
		if (!xmlFile.exists()) {
			xmlFile.createNewFile();
		}
		xStream.setClassLoader(Book.class.getClassLoader());
		xStream.alias("Library", Map.class);
		xStream.alias("Book", Entry.class);
		xStream.alias("book_properties", Book.class);
	}

	@Override
	public synchronized void addBook(Book newBook) {
		long newID = ++maxID;
		newBook.setId(newID);
		Map<Long, Book> books = read();
		books.put(newID, newBook);
		System.out.println(books);
		writeListIntoFile(books);
		notifyOnChangeDataListeners();
	}

	@Override
	public synchronized void removeBook(long bookID) {
		Map<Long, Book> readedList = read();
		readedList.remove(bookID);
		writeListIntoFile(readedList);
		notifyOnChangeDataListeners();
	}

	@Override
	public void updateBook(Book bookToUpdate) {
		Map<Long, Book> readedList = read();
		readedList.put(bookToUpdate.getId(), bookToUpdate);
		writeListIntoFile(readedList);
		notifyOnChangeDataListeners();
	}

	@Override
	public synchronized Book getBookByID(long bookID) {
		Map<Long, Book> readedList = read();
		return readedList.get(bookID);
	}

	@Override
	public synchronized Book getBook(String author, String title) {
		Map<Long, Book> readedList = read();
		for (Book book : readedList.values()) {
			if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
				return book;
			}
		}
		return null;
	}

	@Override
	public synchronized List<Book> getAllBooks() {
		return new LinkedList<Book>(read().values());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<Long, Book> read() {
		Map<Long, Book> readedList;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(xmlFile);
			try {

				byte[] readed = new byte[100000];
				fileInputStream.read(readed);
				System.out.println(readed.length + " znaków: \"" + (new String(readed)) + "\"");
				readedList = (Map) xStream.fromXML(new String(readed));
				maxID = 0;
				for (Book book : readedList.values()) {
					if (book.getId() >= maxID) {
						++maxID;
					}
				}
				return readedList;
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "read", e);
			return new HashMap<Long, Book>();
		}
	}

	private void writeListIntoFile(Map<Long, Book> listToWrite) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(xmlFile, false);
			try {
				String xmlString = xStream.toXML(listToWrite);
				// LogManager.getLogger(getClass()).log(Level.ERROR,
				// "serialized xml: " + xmlString);
				outputStream.write(xmlString.getBytes());
			} finally {
				outputStream.close();
			}
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "writeListIntoFile", e);
		}

	}
}
