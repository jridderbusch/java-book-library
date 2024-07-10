package jr.examples.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jr.examples.entities.Member;

@ApplicationScoped
public class MemberRepository implements PanacheRepository<Member> {
    public Member findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
