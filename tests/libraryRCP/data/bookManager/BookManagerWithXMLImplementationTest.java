package libraryRCP.data.bookManager;

import java.util.Properties;

public class BookManagerWithXMLImplementationTest extends
		BookManagerWithAbstractImplementationTest {

	@Override
	protected void setUpProperties(Properties properties) {
		properties.put("data.access.class", "libraryRCP.data.XMLBooksDataBase");
		properties.put("data.access.filePath", "test.xml");
	}

	@Override
	protected String getValidDataBaseClassToLoad() {
		return "libraryRCP.data.XMLBooksDataBase";
	}

}
