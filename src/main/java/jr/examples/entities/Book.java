package jr.examples.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Book extends PanacheEntity {
    @Column(nullable = false)
    public String title;
    @Column(nullable = false)
    public String genre;
    @Column(nullable = false)
    public Double price;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    public Author author;
}
