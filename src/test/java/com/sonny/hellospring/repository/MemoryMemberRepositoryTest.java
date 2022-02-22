package com.sonny.hellospring.repository;

import com.sonny.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 메소드가 끝날 때마다 이 메소드 실행시킴. 콜백 메소드라고 보면 된다.
    public void afterEach() {
        repository.clearStore(); // 테스트 하나 끝날 때마다 저장소를 clear해주니 순서와 상관 없어진다!
    }

    @Test
    public void save() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get(); //get으로 바로꺼내는 방식은 좋지 않다.
        //System.out.println("result = " + (result == member)); // 단순히 이런식으로 출력해봐도 됨.

        // 그런데 내가 계속 글자로 볼 수는 없으니.. assert라는 기능을 사용한다.
        // Assertions.assertEquals(member, result);

        // 요즘엔 assertj의 assertThat을 사용한다!
        //Assertions.assertThat(member).isEqualTo(result);

        // Assertions를 static import하면 다음부터는 아래처럼 단축해서 사용가능!
        assertThat(member).isEqualTo(result);

        /* 실무에서는 이 테스트 케이스를 빌드 툴이랑 엮어서 빌드 툴에서 빌드할 때 Test Case 통과하지 못하면
            다음 단계로 못넘어가게 막아버린다.
         */
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2  = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

}
