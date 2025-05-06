package model;

import jakarta.persistence.*;
import model.client.BookAuthor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({ @NamedQuery(name = Author.GET_ALL_AUTHORS, query = "SELECT a FROM Author a"),
		@NamedQuery(name = Author.GET_AUTHORS_BY_NAME, query = "SELECT a FROM Author a WHERE a.name = :name") })
public class Author {
	public static final String GET_ALL_AUTHORS = "Author.getAllAuthors";
	public static final String GET_AUTHORS_BY_NAME = "Author.getAuthorsByName";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
	private Long id;
	private String name;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BookAuthor> bookAuthors = new HashSet<>();

	public Author() {
	}

	public Author(String name) {
		this.name = name;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Author other = (Author) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + "]";
	}
}