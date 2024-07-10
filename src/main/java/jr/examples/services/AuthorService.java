package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jr.examples.controllers.model.AuthorDto;
import jr.examples.controllers.model.create.CreateAuthorRequest;
import jr.examples.controllers.model.update.UpdateAuthorRequest;
import jr.examples.entities.Author;
import jr.examples.repositories.AuthorRepository;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class AuthorService {
    @Inject
    AuthorRepository authorRepository;
    @Inject
    AuthorMapper authorMapper;

    public AuthorDto createAuthor(CreateAuthorRequest authorRequest) {
        var authorEntity = new Author();
        authorEntity.name = authorRequest.getName();
        authorEntity.dob = authorRequest.getDob();
        authorEntity.books = Collections.emptyList();
        authorRepository.persist(authorEntity);
        return authorMapper.toDto(authorEntity);
    }

    public List<AuthorDto> getAllAuthors() {
        var authorEntities = authorRepository.listAll();
        return authorMapper.toDtoList(authorEntities);
    }

    public AuthorDto getAuthorById(Long id) {
        var authorEntity = authorRepository.findById(id);
        return authorMapper.toDto(authorEntity);
    }

    public AuthorDto updateAuthor(Long id, UpdateAuthorRequest authorRequest) {
        var authorEntity = authorRepository.findById(id);
        if (authorEntity == null) return null;

        authorEntity.name = authorRequest.getName();
        authorEntity.dob = authorRequest.getDob();
        authorRepository.persist(authorEntity);
        return authorMapper.toDto(authorEntity);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.deleteById(id))
            throw new IllegalArgumentException("Author ID does not exist");
    }
}
