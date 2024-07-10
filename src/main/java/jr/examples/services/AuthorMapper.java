package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jr.examples.controllers.model.AuthorDto;
import jr.examples.entities.Author;

import java.util.List;

@ApplicationScoped
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        if (author == null) return null;

        AuthorDto dto = new AuthorDto();
        dto.setId(author.id);
        dto.setName(author.name);
        dto.setDob(author.dob);
        dto.setBookIds(author.books.stream().map(it -> it.id).toList());
        return dto;
    }

    public List<AuthorDto> toDtoList(List<Author> authorEntities) {
        return authorEntities.stream().map(this::toDto).toList();
    }
}
