package libraryRCP.data.book.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {

	public static final String TITLE_ID= Book.class.getName()+".title";
	public static final String AUTHOR_ID= Book.class.getName()+".author";
	public static final String YEAR_OF_PUBLICATION_ID= Book.class.getName()+".year";
	
	
	public enum STATUS {
		ORDERED, LOANED, AVAIBLE;
	}

	@XmlElement
	private long id;
	@XmlElement
	private String author;
	@XmlElement
	private String title;
	@XmlElement
	private Book.STATUS status;
	@XmlElement
	private Date loanedDate;
	@XmlElement
	private Date dateOfReturn;
	@XmlElement
	private Integer yearOfPublication;

	public Book() {
	}

	public Book(String author, String title, String yearOfPublication) throws NumberFormatException {
		this.author = author;
		this.title = title;
		this.yearOfPublication = Integer.parseInt(yearOfPublication);
		status = Book.STATUS.AVAIBLE;
		id = -1;
		loanedDate = null;
		dateOfReturn = null;
	}

	public Book(long id, String author, String title, STATUS status, Date loanedDate,
			Date dateOfReturn, Integer yearOfPublication) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.status = status;
		this.loanedDate = loanedDate;
		this.dateOfReturn = dateOfReturn;
		this.yearOfPublication = yearOfPublication;
	}

	@Override
	public boolean equals(Object another) {
		Book anotherBook = (Book) another;
		if (another == null) {
			return false;
		}
		return author.equals(anotherBook.author) && title.equals(anotherBook.title);
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

	public Integer getYearOfPublication() {
		return yearOfPublication;
	}
}
