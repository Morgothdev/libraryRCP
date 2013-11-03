package libraryRCP.gui.addBookPart;

import javax.inject.Inject;

import libraryRCP.data.book.model.Book;
import libraryRCP.data.book.model.BookRepositoryFactory;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddBookPart {

    public static final String ID = "libraryRCP.gui.addBookPart.AddBookPart.class";

    private Text titleInput;
    private Text authorInput;
    private Text yearOfPublicationInput;

    @Inject private MDirtyable dirty;

    private Composite parent;

    @Inject
    public AddBookPart(Composite parent) {
        this.parent = parent;
        GridLayout layout = new GridLayout(2, false);
        parent.setLayout(layout);

        Label label;
        ModifyListener dirtyMaker = new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                dirty.setDirty(true);
            }
        };
        GridData gd = new GridData();
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalAlignment = SWT.FILL;

        label = new Label(parent, SWT.None);
        label.setText("Title:");
        titleInput = new Text(parent, SWT.None);
        titleInput.setLayoutData(gd);
        titleInput.addModifyListener(dirtyMaker);
        titleInput.setMessage("Enter title of new book!");
        
        label = new Label(parent, SWT.None);
        label.setText("Author:");
        authorInput = new Text(parent, SWT.FILL);
        authorInput.setLayoutData(gd);
        authorInput.addModifyListener(dirtyMaker);
        authorInput.setMessage("Enter author of new book!");

        label = new Label(parent, SWT.None);
        label.setText("Year of publication:");
        yearOfPublicationInput = new Text(parent, SWT.None);
        yearOfPublicationInput.setLayoutData(gd);
        yearOfPublicationInput.addModifyListener(dirtyMaker);
        yearOfPublicationInput.setMessage("Enter year of publication of new book!");
  
        Button addButton = new Button(parent, SWT.None);
        addButton.setText("Save");
        addButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                save();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                System.out.println("widgetDefaultSelected");
            }
        });
    }

    @Focus
    public void onFocus() {
    }

    @Persist
    public void save() {
        Book newBook;
        try {
            newBook = new Book(authorInput.getText(), titleInput.getText(),
                    yearOfPublicationInput.getText());
            BookRepositoryFactory.getInstance().addBook(newBook);
            dirty.setDirty(false);
          //authorInput.setText("");
           // titleInput.setText("");
         //   yearOfPublicationInput.setText("");
        } catch (NumberFormatException e) {
            MessageDialog.openError(parent.getShell(), "Couldn't add new book",
                    "Illegal value in Year field");
        }
    }

}