package libraryRCP.data;

import java.util.Properties;

public class ClassBooksDataBaseTest extends BooksDataBaseTest {
	
	@Override
	protected BooksDataBase getTestedBooksDataBase(){
		return new ClassBooksDataBase(new Properties());
	}
}
