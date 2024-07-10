package jr.examples.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Member extends PanacheEntity {
    @Column(unique = true, nullable = false)
    public String username;
    @Column(nullable = false)
    public String email;
    @Column(nullable = false)
    public String address;
    @Column(name = "phone_number")
    public String phoneNumber;
}
