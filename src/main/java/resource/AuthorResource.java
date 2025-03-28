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
import model.Author;
import repository.AuthorRepository;

@Path("/author")
public class AuthorResource {

	@Inject
	private AuthorRepository authorRepository;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
	public Response addAuthor(Author author) {
		if (author == null || author.getName() == null) {
			return Response.status(400).entity("Author name is required").build();
		}
		Author created = authorRepository.createAuthor(author);
		return Response.ok(created).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public Response getAllAuthors() {
		List<Author> authors = authorRepository.getAllAuthors();
		return Response.ok().entity(authors).build();
	}
}