package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import model.client.BookAuthor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({ @NamedQuery(name = Book.GET_ALL_BOOKS, query = "SELECT b FROM Book b"),
		@NamedQuery(name = Book.GET_BOOKS_BY_TITLE, query = "SELECT b FROM Book b WHERE b.title = :title") })
public class Book {
	public static final String GET_ALL_BOOKS = "Book.getAllBooks";
	public static final String GET_BOOKS_BY_TITLE = "Book.getBooksByTitle";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
	private Long id;
	private String title;
	private int year;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Set<Loan> loans;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BookAuthor> bookAuthors = new HashSet<>();

	public Book() {
	}

	public Book(String title, int year) {
		this.title = title;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Set<Loan> getLoans() {
		return loans;
	}

	public void setLoans(Set<Loan> loans) {
		this.loans = loans;
	}

	public Set<BookAuthor> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(Set<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", year=" + year + "]";
	}
}