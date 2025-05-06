package repository;

import java.util.HashSet;
import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Author;
import model.Book;
import model.client.BookAuthor;
import model.Loan;
import exception.BookException;
import model.client.Holiday;

@Dependent
public class BookRepository {

	@Inject
	private EntityManager em;

	@Transactional
	public Book createBook(Book b) {
		return em.merge(b);
	}

	@Transactional
	public Author createAuthor(Author a) {
		return em.merge(a);
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

	public List<Book> getBooksByTitle(String title) throws BookException {
		List<Book> books = em.createNamedQuery(Book.GET_BOOKS_BY_TITLE, Book.class).setParameter("title", title)
				.getResultList();

		if (books.isEmpty()) {
			throw new BookException("Ne postoje knjige sa tim naslovom");
		}

		return books;
	}

	@Transactional
	public BookAuthor createBookAuthor(BookAuthor ba) {
		return em.merge(ba);
	}

	@Transactional
	public void addAuthorToBook(Long bookId, Long authorId) throws BookException {
		Book book = em.find(Book.class, bookId);
		Author author = em.find(Author.class, authorId);
		if (book == null || author == null) {
			throw new BookException("Knjiga ili autor nisu pronađeni");
		}
		BookAuthor bookAuthor = new BookAuthor(book, author);
		em.persist(bookAuthor);
	}

	@Transactional
	public void saveHolidays(List<Holiday> holidays) {
		if (holidays == null || holidays.isEmpty()) {
			return;
		}
		for (Holiday holiday : holidays) {
			if (holiday.getDate() == null || holiday.getCountryCode() == null) {
				continue;
			}
			String year = holiday.getDate().substring(0, 4);
			Holiday existing = findHoliday(year, holiday.getCountryCode());
			if (existing == null) {
				em.persist(holiday);
			}
		}
	}

	public boolean checkHolidaysSaved(String countryCode, int expectedCount) {
		Long count = (Long) em.createQuery("SELECT COUNT(h) FROM Holiday h WHERE h.countryCode = :code")
				.setParameter("code", countryCode).getSingleResult();
		return count == expectedCount;
	}

	private Holiday findHoliday(String year, String countryCode) {
		try {
			return em
					.createQuery("SELECT h FROM Holiday h WHERE h.date LIKE :year AND h.countryCode = :code",
							Holiday.class)
					.setParameter("year", year + "%").setParameter("code", countryCode).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}