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
	}

	public Book.STATUS getStatus() {
		return status;
	}

	public void setStatus(Book.STATUS status) {
		this.status = status;
	}

	public Date getLoanedDate() {
		return loanedDate;
	}

	public void setLoanedDate(Date loanedDate) {
		this.loanedDate = loanedDate;
	}

	public Date getDateOfReturn() {
		return dateOfReturn;
	}

	public void setDateOfReturn(Date dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}

	public long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}
}
