package org.example.withoutspringsecurity.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryMemberRepository {
    private final Map<String, Member> memberTable = new HashMap<>();

    public Optional<Member> findByEmail(String email) {
        return Optional.of(memberTable.get(email));
    }

    public Member upsert(Member member) {
        memberTable.put(member.email(), member);

        return member;
    }

    public Member save(Member member) {
        if (memberTable.containsKey(member)) {
            throw new IllegalArgumentException("이미 존재하는 Member입니다.");
        }

        memberTable.put(member.email(), member);

        return member;
    }
}
