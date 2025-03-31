package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = Loan.GET_LOANS_FOR_BOOK, query = "SELECT l FROM Loan l WHERE l.book.id = :id")
public class Loan {
	public static final String GET_LOANS_FOR_BOOK = "Loan.getLoansForBook";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq")
	private Long id;

	private String loanDate;
	private String returnDate;

	@ManyToOne
	private Book book;

	public Loan() {
		super();
	}

	public Loan(String loanDate, String returnDate) {
		this.loanDate = loanDate;
		this.returnDate = returnDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loanDate == null) ? 0 : loanDate.hashCode());
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
		Loan other = (Loan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loanDate == null) {
			if (other.loanDate != null)
				return false;
		} else if (!loanDate.equals(other.loanDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", loanDate=" + loanDate + ", returnDate=" + returnDate + "]";
	}
}