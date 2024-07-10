package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jr.examples.controllers.model.BookDto;
import jr.examples.controllers.model.create.CreateBookRequest;
import jr.examples.controllers.model.update.UpdateBookRequest;
import jr.examples.entities.Author;
import jr.examples.entities.Book;
import jr.examples.repositories.AuthorRepository;
import jr.examples.repositories.BookRepository;

import java.util.List;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository bookRepository;
    @Inject
    AuthorRepository authorRepository;
    @Inject
    BookMapper bookMapper;

    public BookDto createBook(CreateBookRequest bookRequest) {
        if (bookRequest == null
                || bookRequest.getTitle() == null
                || bookRequest.getGenre() == null
                || bookRequest.getPrice() == null
                || bookRequest.getAuthorId() == null)
            throw new IllegalArgumentException("Mandatory fields are missing");

        var author = authorRepository.findById(bookRequest.getAuthorId());
        assertAuthorExists(author);

        var bookEntity = new Book();
        bookEntity.title = bookRequest.getTitle();
        bookEntity.genre = bookRequest.getGenre();
        bookEntity.price = bookRequest.getPrice();
        bookEntity.author = author;
        bookRepository.persist(bookEntity);
        return bookMapper.toDto(bookEntity);
    }

    public List<BookDto> getAllBooks() {
        var bookEntities = bookRepository.listAll();
        return bookMapper.toDtoList(bookEntities);
    }

    public BookDto getBookById(Long id) {
        var bookEntity = bookRepository.findById(id);
        return bookMapper.toDto(bookEntity);
    }

    public BookDto updateBook(Long id, UpdateBookRequest bookRequest) {
        if (bookRequest == null
                || bookRequest.getTitle() == null
                || bookRequest.getGenre() == null
                || bookRequest.getPrice() == null
                || bookRequest.getAuthorId() == null)
            throw new IllegalArgumentException("Mandatory fields are missing");

        var bookEntity = bookRepository.findById(id);
        if (bookEntity == null)
            throw new NotFoundException("Book ID does not exist");

        var author = authorRepository.findById(bookRequest.getAuthorId());
        assertAuthorExists(author);

        bookEntity.title = bookRequest.getTitle();
        bookEntity.genre = bookRequest.getGenre();
        bookEntity.price = bookRequest.getPrice();
        bookEntity.author = author;
        bookRepository.persist(bookEntity);
        return bookMapper.toDto(bookEntity);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.deleteById(id))
            throw new IllegalArgumentException("Book ID does not exist");
    }

    private void assertAuthorExists(Author author) {
        if (author == null)
            throw new IllegalArgumentException("Author ID does not exist");
    }
}
