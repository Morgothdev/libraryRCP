package libraryRCP;

import libraryRCP.data.book.model.BookRepositoryFactory;
import libraryRCP.jobs.OnBookStatusChangedListenerDefaultImpl;
import libraryRCP.jobs.StatusCheckerJob;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class MyBundleActivator implements BundleActivator {

    private BundleContext context;
    private static MyBundleActivator instance;

    @Override
    public void start(BundleContext context) throws Exception {
        instance = this;
        this.context = context;
        createSingletons();
    }

    private void createSingletons() {
        BookRepositoryFactory.configure(MyWorkspaceFilesRepositor.getProperties());
        StatusCheckerJob bookStatusCheckerJob = StatusCheckerJob.getInstance("checker",
                BookRepositoryFactory.getInstance());
        bookStatusCheckerJob
                .setOnBookStatusChangeListener(new OnBookStatusChangedListenerDefaultImpl());
        bookStatusCheckerJob.schedule();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        this.context = context;
    }

    public static BundleContext getContext() {
        return instance.context;
    }

}
