package jr.examples.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jr.examples.entities.Author;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author> {

}
