package jr.examples.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
public class Loan extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    public Member member;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    public Book book;
    @Column(name = "lend_date", nullable = false)
    public LocalDate lendDate;
    @Column(name = "due_date", nullable = false)
    public LocalDate dueDate;
}
