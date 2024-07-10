package jr.examples.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jr.examples.entities.Loan;

@ApplicationScoped
public class LoanRepository implements PanacheRepository<Loan> {
    public Long countByMemberId(Long memberId) {
        return find("member.id", memberId).count();
    }
}
