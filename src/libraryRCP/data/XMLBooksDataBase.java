package libraryRCP.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XMLBooksDataBase extends BooksDataBase {

	private long maxID = 0;
	private XStream xStream = new XStream(new StaxDriver());
	private File xmlFile;

	public XMLBooksDataBase(Properties properties) throws IOException {
		super(properties);
		String pathname = (String) properties.get("data.access.filePath");
		xmlFile = new File(pathname);
		if (!xmlFile.exists()) {
			xmlFile.createNewFile();
		}
		xStream.alias("Book", Book.class);
		xStream.alias("Library", LinkedList.class);
	}

	@Override
	public synchronized void addBook(Book newBook) {
		long newID = ++maxID;
		newBook.setId(newID);
		List<Book> books = read();

		books.add(newBook);
		System.out.println(books);
		writeListIntoFile(books);
	}

	@Override
	public synchronized void removeBook(Book bookToDelete) {
		List<Book> readedList = read();
		readedList.remove(bookToDelete);
		writeListIntoFile(readedList);
	}

	@Override
	public synchronized Book getBookByID(long bookID) {
		List<Book> readedList = read();
		for (Book book : readedList) {
			if (book.getId() == bookID) {
				return book;
			}
		}
		return null;
	}

	@Override
	public synchronized Book getBook(String author, String title) {
		List<Book> readedList = read();
		for (Book book : readedList) {
			if (book.getTitle().equals(title)
					&& book.getAuthor().equals(author)) {
				return book;
			}
		}
		return null;
	}

	@Override
	public synchronized List<Book> getBooks() {
		return read();
	}
	

	@SuppressWarnings("unchecked")
	private List<Book> read() {
		List<Book> readedList;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(xmlFile);
			try {
				readedList = (List<Book>) xStream.fromXML(fileInputStream);
				maxID = 0;
				for (Book book : readedList) {
					if (book.getId() >= maxID) {
						++maxID;
					}
				}
				return readedList;
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			LogManager.getLogger(getClass()).log(Level.ERROR, "", e);
			return new LinkedList<Book>();
		}
	}

	private void writeListIntoFile(List<Book> listToWrite) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(xmlFile, false);
			try {
				String xmlString = xStream.toXML(listToWrite);
				// LogManager.getLogger(getClass()).log(Level.ERROR,
				//		"serialized xml: " + xmlString);
				outputStream.write(xmlString.getBytes());
			} finally {
				try {
					outputStream.close();
				} catch (IOException e) {
					LogManager.getLogger(getClass()).log(Level.ERROR, "", e);
				}
			}
		} catch (IOException e) {
			LogManager.getLogger(getClass()).log(Level.ERROR, "", e);
		}

	}
}
