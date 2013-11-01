package libraryRCP.jobs;

import libraryRCP.data.book.model.Book;

public class OnBookStatusChangedListenerDefaultImpl implements OnBookStatusChangedListener {

    @Override
    public void onChange(Book changedBook) {

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Status of book id:").append(changedBook.getId())
                .append("  is canged to ").append(changedBook.getStatus());
        System.out.println(messageBuilder.toString());
    }
}
