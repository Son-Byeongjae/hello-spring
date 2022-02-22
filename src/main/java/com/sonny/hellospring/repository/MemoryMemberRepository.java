package com.sonny.hellospring.repository;

import com.sonny.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence); // store에 넣기 전에 member의 id값 설정. name은 넘어온 상태라고 가정
        store.put(member.getId(), member); // store에 저장하고
        return member; // 스펙에 따라서 저장된 결과를 반환.
    }

    @Override
    public Optional<Member> findById(Long id) {
        // null값 처리를 optional을 이용한다. 이렇게 감싸서 반환해주면 클라이언트에서 뭔가를 할 수 있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // Java8 람다 사용. 루프돌림
                .filter(member -> member.getName().equals(name)) // 루프를 돌리며 매개변수로 넘어온 name과 같은지 확인
                .findAny(); // 같은 경우에만 필터링이 되고, 찾으면 반환을 함. findAny는 하나라도 찾는 것임.
        // Optional로 반환됨. 루프를 돌면서 하나라도 있으면 반환, 끝까지 루프를 돌았는데 없으면 Optional에 null이 포함되어 반환.
    }

    @Override
    public List<Member> findAll() {
        // 자바에서 실무할 때는 List를 많이 쓴다.
        return new ArrayList<>(store.values()); // store에 있는 value들이 List형식으로 반환된다.
    }

    public void clearStore() {
        store.clear();
    }
}
