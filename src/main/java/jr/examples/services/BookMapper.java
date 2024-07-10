package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jr.examples.controllers.model.BookDto;
import jr.examples.entities.Book;
import jr.examples.repositories.AuthorRepository;

import java.util.List;

@ApplicationScoped
public class BookMapper {
    @Inject
    AuthorRepository authorRepository;

    public BookDto toDto(Book book) {
        if (book == null) return null;

        BookDto dto = new BookDto();
        dto.setId(book.id);
        dto.setTitle(book.title);
        dto.setGenre(book.genre);
        dto.setPrice(book.price);
        dto.setAuthorId(book.author.id);
        return dto;
    }

    public List<BookDto> toDtoList(List<Book> bookEntities) {
        return bookEntities.stream().map(this::toDto).toList();
    }
}
