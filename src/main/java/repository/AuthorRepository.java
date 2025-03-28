package repository;

import java.util.HashSet;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Author;
import model.Book;

@Dependent
public class AuthorRepository {

    @Inject
    private EntityManager em;

    @Transactional
    public Author createAuthor(Author author) {
        return em.merge(author);
    }

    @Transactional
    public List<Author> getAllAuthors() {
        List<Author> authors = em.createNamedQuery(Author.GET_ALL_AUTHORS, Author.class).getResultList();
        
        for (Author author : authors) {
            List<Book> books = em.createNamedQuery(Book.GET_BOOKS_FOR_AUTHOR, Book.class)
                    .setParameter("id", author.getId())
                    .getResultList();
            
            author.setBooks(new HashSet<>(books));
        }

        return authors;
    }

    @Transactional
    public Author findById(Long id) {
        return em.find(Author.class, id);
    }
}