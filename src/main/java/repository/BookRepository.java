package repository;

import java.util.HashSet;
import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Book;
import model.Loan;

@Dependent
public class BookRepository {

	@Inject
	private EntityManager em;

	@Transactional
	public Book createBook(Book book) {
		return em.merge(book);
	}

	@Transactional
	public List<Book> getAllBooks() {
		List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();

		for (Book book : books) {
			List<Loan> loans = em.createQuery("SELECT l FROM Loan l WHERE l.book.id = :bookId", Loan.class)
					.setParameter("bookId", book.getId()).getResultList();

			book.setLoans(new HashSet<>(loans));
		}

		return books;
	}
}