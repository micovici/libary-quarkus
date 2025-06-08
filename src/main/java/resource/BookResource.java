package resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import exception.BookException;

import model.Author;
import model.Book;
import model.MultipartRequest;
import model.client.BookAuthor;
import repository.BookRepository;

import java.io.FileOutputStream;

import java.io.IOException;

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

	@POST
	@Path("/upload/{bookId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadBookFile(@PathParam("bookId") Long bookId, MultipartRequest request) {

		FileOutputStream out = null;
		InputStream in = null;
		try {
			Book book = bookRepository.getBookById(bookId);
			if (book == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			String uploadDir = System.getProperty("user.home") + "/book-uploads";
			new File(uploadDir).mkdirs();

			String fileName = request.getName() != null ? request.getName() : request.getFile().fileName();
			String filePath = uploadDir + "/" + fileName;

			in = Files.newInputStream(request.getFile().uploadedFile());
			out = new FileOutputStream(new File(filePath));

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

			book.setFilePath(filePath);
			bookRepository.createBook(book);

			return Response.ok("Fajl je uspješno učitan.").build();

		} catch (Exception e) {
			return Response.serverError().entity("Učitvanje nije uspješno: " + e.getMessage()).build();
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/download/{bookId}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadBookFile(@PathParam("bookId") Long bookId) {
		FileInputStream fis = null;
		try {
			Book book = bookRepository.getBookById(bookId);
			if (book == null || book.getFilePath() == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			File file = new File(book.getFilePath());
			fis = new FileInputStream(file);
			byte[] fileData = new byte[(int) file.length()];
			fis.read(fileData);

			return Response.ok(fileData)
					.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();

		} catch (Exception e) {
			return Response.serverError().entity("Skidanje nije uspješno: " + e.getMessage()).build();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}