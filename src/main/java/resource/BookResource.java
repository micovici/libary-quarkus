package resource;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import exception.BookException;
import model.Author;
import model.Book;
import model.client.BookAuthor;
import repository.BookRepository;

@Path("/book/")
public class BookResource {

	@Inject
	private BookRepository bookRepository;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addBook")
	public Response createBook(Book book) {
		Book b = bookRepository.createBook(book);
		return Response.ok().entity(b).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addAuthor")
	public Response createAuthor(Author author) {
		Author a = bookRepository.createAuthor(author);
		return Response.ok().entity(a).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllBooks")
	public Response getAllBooks() {
		List<Book> books = bookRepository.getAllBooks();
		return Response.ok().entity(books).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getBooksByTitle")
	public Response getBooksByTitle(@QueryParam(value = "title") String title) {
		List<Book> books;
		try {
			books = bookRepository.getBooksByTitle(title);
		} catch (BookException e) {
			return Response.ok().entity(e.getMessage()).build();
		}
		return Response.ok().entity(books).build();
	}

	@POST
	@Path("/addAuthorToBook")
	public Response addAuthorToBook(@QueryParam(value = "bookId") Long bookId,
			@QueryParam(value = "authorId") Long authorId) {

		try {
			bookRepository.addAuthorToBook(bookId, authorId);
			return Response.ok().entity("Autor uspješno povezan sa knjigom").build();
		} catch (BookException e) {
			return Response.ok().entity(e.getMessage()).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createBookAuthor")
	public Response createBookAuthor(BookAuthor bookAuthor) {
		BookAuthor ba = bookRepository.createBookAuthor(bookAuthor);
		return Response.ok().entity(ba).build();
	}
}