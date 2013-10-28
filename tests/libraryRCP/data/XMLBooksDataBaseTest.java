package libraryRCP.data;

import java.util.Properties;

public class XMLBooksDataBaseTest extends BooksDataBaseTest {

	@Override
	protected BooksDataBase getTestedBooksDataBase() {
		Properties properties = new Properties();
		
		
		return new XMLBooksDataBase(properties);
	}
}
