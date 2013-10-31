package libraryRCP.gui.listBookPart;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ListContentProvider implements IStructuredContentProvider {

	private BookModel model;

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.model=(BookModel)newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model.getBooks().toArray();
	}

}