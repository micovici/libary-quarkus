package model;

import java.util.List;

public class Book {
	private String id;
	private String title;
	private int year;
	private List<Loan> loans;
	private List<Author> authors;

	public Book(String id, String title, int year, List<Loan> loans, List<Author> authors) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.loans = loans;
		this.authors = authors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}