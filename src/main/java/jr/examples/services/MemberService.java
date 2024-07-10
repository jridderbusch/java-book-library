package jr.examples.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jr.examples.controllers.model.MemberDto;
import jr.examples.controllers.model.create.CreateMemberRequest;
import jr.examples.controllers.model.update.UpdateMemberRequest;
import jr.examples.entities.Member;
import jr.examples.repositories.MemberRepository;

import java.util.List;

@ApplicationScoped
public class MemberService {
    @Inject
    MemberRepository memberRepository;
    @Inject
    MemberMapper memberMapper;

    public MemberDto createMember(CreateMemberRequest memberRequest) {
        if (memberRequest == null
                || memberRequest.getUsername() == null
                || memberRequest.getEmail() == null
                || memberRequest.getAddress() == null
                || memberRequest.getPhoneNumber() == null)
            throw new IllegalArgumentException("Mandatory fields are missing");
        assertUsernameAvailable(memberRequest.getUsername());

        var memberEntity = new Member();
        memberEntity.username = memberRequest.getUsername();
        memberEntity.email = memberRequest.getEmail();
        memberEntity.address = memberRequest.getAddress();
        memberEntity.phoneNumber = memberRequest.getPhoneNumber();
        memberRepository.persist(memberEntity);
        return memberMapper.toDto(memberEntity);
    }

    public List<MemberDto> getAllMembers() {
        var memberEntities = memberRepository.listAll();
        return memberMapper.toDtoList(memberEntities);
    }

    public MemberDto getMemberById(Long id) {
        var memberEntity = memberRepository.findById(id);
        return memberMapper.toDto(memberEntity);
    }

    public MemberDto updateMember(Long id, UpdateMemberRequest memberRequest) {
        if (memberRequest == null
                || memberRequest.getUsername() == null
                || memberRequest.getEmail() == null
                || memberRequest.getAddress() == null
                || memberRequest.getPhoneNumber() == null)
            throw new IllegalArgumentException("Mandatory fields are missing");

        var memberEntity = memberRepository.findById(id);
        if (memberEntity == null)
            throw new NotFoundException("Member ID does not exist");

        if (!memberEntity.username.equals(memberRequest.getUsername()))
            assertUsernameAvailable(memberRequest.getUsername());

        memberEntity.username = memberRequest.getUsername();
        memberEntity.email = memberRequest.getEmail();
        memberEntity.address = memberRequest.getAddress();
        memberEntity.phoneNumber = memberRequest.getPhoneNumber();
        memberRepository.persist(memberEntity);
        return memberMapper.toDto(memberEntity);
    }

    public void deleteMember(Long id) {
        if (!memberRepository.deleteById(id))
            throw new IllegalArgumentException("Member ID does not exist");
    }

    private void assertUsernameAvailable(String username) {
        if (memberRepository.findByUsername(username) != null)
            throw new IllegalArgumentException("Username already exists");
    }
}
