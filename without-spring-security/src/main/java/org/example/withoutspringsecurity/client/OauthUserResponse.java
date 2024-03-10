package org.example.withoutspringsecurity.client;

import org.example.withoutspringsecurity.repository.Member;

public record OauthUserResponse(
        String email
) {
    public Member toMember() {
        return new Member(email);
    }
}
