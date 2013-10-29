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
		//xStream.setClassLoader(classLoader);
	}

	@Override
	public synchronized void addBook(Book newBook) {
		long newID = ++maxID;
		newBook.setId(newID);
		List<Book> books = read();

		books.add(newBook);
		System.out.println(books);
		writeListIntoFile(books);
		notifyOnChangeDataListeners();
	}

	@Override
	public synchronized void removeBook(Book bookToDelete) {
		List<Book> readedList = read();
		readedList.remove(bookToDelete);
		writeListIntoFile(readedList);
		notifyOnChangeDataListeners();
	}

	@Override
	public void updateBook(Book bookToUpdate) {
		List<Book> readedList = read();
		for (Book book : readedList) {
			if (book.getId() == bookToUpdate.getId()) {
				readedList.remove(book);
				readedList.add(bookToUpdate);
			}
		}
		writeListIntoFile(readedList);
		notifyOnChangeDataListeners();
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
			if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
				return book;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized List<Book> getAllBooks() {
		return (List<Book>) read();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List read() {
		List<Book> readedList;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(xmlFile);
			try {
				
				byte[] readed = new byte[100000];
				fileInputStream.read(readed);
				System.out.println(readed.length+" znaków: \""+(new String(readed))+"\"");
				readedList = (List) xStream.fromXML(new String(readed));
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
			LogManager.getLogger(getClass()).log(Level.ERROR, "read", e);
			return new LinkedList<Book>();
		}
	}

	private void writeListIntoFile(@SuppressWarnings("rawtypes") List listToWrite) {
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
			LogManager.getLogger(getClass()).log(Level.ERROR, "writeListIntoFile", e);
		}

	}
}
