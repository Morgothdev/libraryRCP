package libraryRCP.data.bookManager;

import java.util.Properties;

public class BookManagerWithClassImplementationTest extends
		BookManagerWithAbstractImplementationTest {

	@Override
	public void setUpProperties(Properties properties) {
		properties.put("data.access.class",
				"libraryRCP.data.ClassBooksDataBase");
	}
	
	@Override
	public String getValidDataBaseClassToLoad(){
		return "libraryRCP.data.ClassBooksDataBase";
	}

}
