package jr.examples.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Author extends PanacheEntity {
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public OffsetDateTime dob;
    @OneToMany(mappedBy = "author")
    public List<Book> books;
}
