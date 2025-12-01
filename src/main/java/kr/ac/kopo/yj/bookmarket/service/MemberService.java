package kr.ac.kopo.yj.bookmarket.service;

import jakarta.transaction.Transactional;
import kr.ac.kopo.yj.bookmarket.domain.Member;
import kr.ac.kopo.yj.bookmarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //회원 정보 저장
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 특정 memberId를 가진 Member Entity만 반환
    public Member findMemberByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }

    // Member Entity (회원 정보) 삭제
    public void deleteMember(String memberId) {
        Member member = findMemberByMemberId(memberId);
//        memberRepository.delete(member);
        memberRepository.deleteById(member.getNum());
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByMemberId(member.getMemberId());
        if(findMember != null) {
            throw new IllegalStateException("Duplicate member found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = findMemberByMemberId(username);
        if(member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDetails user = User.builder()
                .username(member.getMemberId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
        return user;
    }

    public Member getMemberByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }
}
