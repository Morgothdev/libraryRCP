package libraryRCP;

import libraryRCP.data.Book;

public interface OnBookStatusChangedListener {

	public void onChange(Book changedBook);
}
