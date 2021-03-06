package libraryRCP.gui.listBookPart;

import libraryRCP.data.book.model.Book;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ListItemBooksLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {
        return ((Book) element).getTitle() + ", " + ((Book) element).getAuthor();
    }

    @Override
    public Image getImage(Object element) {
        return null;
    }
}
