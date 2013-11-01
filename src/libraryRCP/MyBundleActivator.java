package libraryRCP;

import libraryRCP.data.book.model.BookRepositoryFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class MyBundleActivator implements BundleActivator {

	private BundleContext context;
	private static MyBundleActivator instance;

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		BookRepositoryFactory.configure(MyWorkspaceFilesRepositor.getProperties());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		this.context = context;
	}

	public static BundleContext getContext() {
		return instance.context;
	}

}
