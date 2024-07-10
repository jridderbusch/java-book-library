package jr.examples.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jr.examples.entities.Book;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {
}