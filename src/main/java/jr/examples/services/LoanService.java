package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jr.examples.controllers.model.LoanDto;
import jr.examples.controllers.model.create.CreateLoanRequest;
import jr.examples.controllers.model.update.UpdateLoanRequest;
import jr.examples.entities.Book;
import jr.examples.entities.Loan;
import jr.examples.entities.Member;
import jr.examples.repositories.BookRepository;
import jr.examples.repositories.LoanRepository;
import jr.examples.repositories.MemberRepository;

import java.util.List;

@ApplicationScoped
public class LoanService {
    @Inject
    LoanRepository loanRepository;
    @Inject
    MemberRepository memberRepository;
    @Inject
    BookRepository bookRepository;
    @Inject
    LoanMapper loanMapper;

    public LoanDto createLoan(CreateLoanRequest loanRequest) {
        if (loanRequest == null
                || loanRequest.getMemberId() == null
                || loanRequest.getBookId() == null
                || loanRequest.getLendDate() == null
                || loanRequest.getDueDate() == null)
            throw new IllegalArgumentException("Mandatory fields are missing");

        var book = bookRepository.findById(loanRequest.getBookId());
        assertBookExists(book);
        var member = memberRepository.findById(loanRequest.getMemberId());
        assertMemberExists(member);
        assertLoanCapacity(member.id);

        var loanEntity = new Loan();
        loanEntity.member = member;
        loanEntity.book = book;
        loanEntity.lendDate = loanRequest.getLendDate();
        loanEntity.dueDate = loanRequest.getDueDate();
        loanRepository.persist(loanEntity);
        return loanMapper.toDto(loanEntity);
    }

    public List<LoanDto> getAllLoans() {
        var loanEntities = loanRepository.listAll();
        return loanMapper.toDtoList(loanEntities);
    }

    public LoanDto getLoanById(Long id) {
        var loanEntity = loanRepository.findById(id);
        return loanMapper.toDto(loanEntity);
    }

    public LoanDto updateLoan(Long id, UpdateLoanRequest loanRequest) {
        if (loanRequest == null
                || loanRequest.getMemberId() == null
                || loanRequest.getBookId() == null
                || loanRequest.getLendDate() == null
                || loanRequest.getDueDate() == null)
            throw new IllegalArgumentException("Mandatory fields are missing");

        var loanEntity = loanRepository.findById(id);
        assertLoanExists(loanEntity);
        var book = bookRepository.findById(loanRequest.getBookId());
        assertBookExists(book);
        var member = memberRepository.findById(loanRequest.getMemberId());
        assertMemberExists(member);
        if (!loanEntity.member.id.equals(member.id))
            assertLoanCapacity(member.id);

        loanEntity.member = member;
        loanEntity.book = book;
        loanEntity.lendDate = loanRequest.getLendDate();
        loanEntity.dueDate = loanRequest.getDueDate();
        loanRepository.persist(loanEntity);
        return loanMapper.toDto(loanEntity);
    }

    public void deleteLoan(Long id) {
        if (!loanRepository.deleteById(id))
            throw new IllegalArgumentException("Loan ID does not exist");
    }

    private void assertLoanExists(Loan loan) {
        if (loan == null)
            throw new IllegalArgumentException("Loan ID does not exist");
    }

    private void assertMemberExists(Member member) {
        if (member == null)
            throw new IllegalArgumentException("Member ID does not exist");
    }

    private void assertBookExists(Book book) {
        if (book == null)
            throw new IllegalArgumentException("Book ID does not exist");
    }

    private void assertLoanCapacity(Long memberId) {
        if (loanRepository.countByMemberId(memberId) >= 5) {
            throw new IllegalStateException("Member has reached the maximum loan capacity");
        }
    }
}
