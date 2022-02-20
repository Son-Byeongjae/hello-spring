package com.sonny.hellospring.service;

import com.sonny.hellospring.domain.Member;
import com.sonny.hellospring.repository.MemberRepository;
import com.sonny.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    // 서비스를 만드려면 회원 레포지토리가 있어야지!
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원X
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(() -> {
            throw new IllegalAccessError("이미 존재하는 회원입니다.")
        });*/

        // Optional을 반환하는 것은 권장되지 않음. 일단 안 이쁨. 그래서 아래 처럼 작성한다.
        // 그런데 findByName부터 뭔가 로직이 쭉 이어짐. 이런 경우는 메소드를 뽑는게 좋다.
        // [Ctrl + Alt + Shift + T] => Extract Method
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalAccessException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
