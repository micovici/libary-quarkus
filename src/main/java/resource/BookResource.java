package resource;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Book;
import repository.BookRepository;

@Path("/book")
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllBooks")
	public Response getAllBooks() {
		List<Book> books = bookRepository.getAllBooks();
		return Response.ok().entity(books).build();
	}
}