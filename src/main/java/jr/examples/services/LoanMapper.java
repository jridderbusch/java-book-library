package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jr.examples.controllers.model.LoanDto;
import jr.examples.entities.Loan;
import jr.examples.repositories.BookRepository;
import jr.examples.repositories.MemberRepository;

import java.util.List;

@ApplicationScoped
public class LoanMapper {
    @Inject
    MemberRepository memberRepository;
    @Inject
    BookRepository bookRepository;

    public LoanDto toDto(Loan loan) {
        if (loan == null) return null;

        LoanDto dto = new LoanDto();
        dto.setId(loan.id);
        dto.setMemberId(loan.member.id);
        dto.setBookId(loan.book.id);
        dto.setLendDate(loan.lendDate);
        dto.setDueDate(loan.dueDate);
        return dto;
    }

    public Loan toEntity(LoanDto loanDto) {
        if (loanDto == null) return null;

        Loan entity = new Loan();
        entity.id = loanDto.getId();
        entity.member = memberRepository.findById(loanDto.getMemberId());
        entity.book = bookRepository.findById(loanDto.getBookId());
        entity.lendDate = loanDto.getLendDate();
        entity.dueDate = loanDto.getDueDate();
        return entity;
    }

    public List<LoanDto> toDtoList(List<Loan> loanEntities) {
        return loanEntities.stream().map(this::toDto).toList();
    }
}
