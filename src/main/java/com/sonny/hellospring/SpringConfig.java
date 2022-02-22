package com.sonny.hellospring;


import com.sonny.hellospring.repository.MemberRepository;
import com.sonny.hellospring.repository.MemoryMemberRepository;
import com.sonny.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
