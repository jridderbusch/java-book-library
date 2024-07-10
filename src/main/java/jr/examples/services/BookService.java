package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jr.examples.controllers.model.BookDto;
import jr.examples.controllers.model.CreateBookRequest;
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
        var author = authorRepository.findById(bookRequest.getAuthorId());
        if (author == null) {
            throw new IllegalArgumentException("Author ID does not exist");
        }

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

    public BookDto updateBook(BookDto bookDto) {
        var bookEntity = bookRepository.findById(bookDto.getId());
        if (bookEntity == null) {
            return null;
        }
        var author = authorRepository.findById(bookDto.getAuthorId());
        if (author == null) {
            throw new IllegalArgumentException("Author ID does not exist");
        }

        bookEntity.title = bookDto.getTitle();
        bookEntity.genre = bookDto.getGenre();
        bookEntity.price = bookDto.getPrice();
        bookEntity.author = author;
        bookRepository.persist(bookEntity);
        return bookMapper.toDto(bookEntity);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.deleteById(id))
            throw new IllegalArgumentException("Book ID does not exist");
    }
}
