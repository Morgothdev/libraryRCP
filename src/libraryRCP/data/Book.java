package libraryRCP.data;

import java.util.Date;

public class Book {

	public enum STATUS {
		ORDERED, LOANED, AVAIBLE;
	}

	private long id;

	private String author;
	private String title;

	private Book.STATUS status;
	private Date loanedDate;
	private Date dateOfReturn;

	public Book(String author, String title) {
		this.author = author;
		this.title = title;
		status = Book.STATUS.AVAIBLE;
		id = -1;
		loanedDate = null;
		dateOfReturn = null;
	}

	@Override
	public boolean equals(Object another){
		Book anotherBook = (Book)another;
		if(another==null){
			return false;
		}
		return author.equals(anotherBook.author)&&title.equals(anotherBook.title);
	}

	public Book.STATUS getStatus() {
		return status;
	}

	public Book setStatus(Book.STATUS status) {
		this.status = status;
		return this;
	}

	public Date getLoanedDate() {
		return loanedDate;
	}

	public Book setLoanedDate(Date loanedDate) {
		this.loanedDate = loanedDate;
		return this;
	}

	public Date getDateOfReturn() {
		return dateOfReturn;
	}

	public Book setDateOfReturn(Date dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
		return this;
	}

	public long getId() {
		return id;
	}

	public Book setId(long id) {
		this.id = id;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}
}
