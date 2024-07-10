package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jr.examples.controllers.model.MemberDto;
import jr.examples.entities.Member;

import java.util.List;

@ApplicationScoped
public class MemberMapper {
    public MemberDto toDto(Member member) {
        if (member == null) return null;

        MemberDto dto = new MemberDto();
        dto.setId(member.id);
        dto.setUsername(member.username);
        dto.setEmail(member.email);
        dto.setAddress(member.address);
        dto.setPhoneNumber(member.phoneNumber);
        return dto;
    }

    public List<MemberDto> toDtoList(List<Member> memberEntities) {
        return memberEntities.stream().map(this::toDto).toList();
    }
}
