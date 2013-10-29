 
package libraryRCP.gui.listBookPart;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ListBookPart {
	public static final String ID = "libraryRCP.gui.ListBookPart";
	  private ListViewer viewer;

	  @PostConstruct
	  public void createPartControl(Composite parent) {
	    viewer = new ListViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	    viewer.setContentProvider(new ListContentProvider());
	    viewer.setLabelProvider(new ListItemBooksLabelProvider());
	    viewer.setInput(new BookModel());

	    //viewer.addDoubleClickListener();
	  }

	  @Focus
	  public void setFocus() {
	    viewer.getControl().setFocus();
	  }
}