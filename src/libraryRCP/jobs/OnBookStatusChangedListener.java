package libraryRCP.jobs;

import libraryRCP.data.book.model.Book;

public interface OnBookStatusChangedListener {

    public void onChange(Book changedBook);
}
