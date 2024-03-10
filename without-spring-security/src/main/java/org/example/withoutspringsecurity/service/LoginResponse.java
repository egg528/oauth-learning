package org.example.withoutspringsecurity.service;

public record LoginResponse (
        String email,
        String accessToken,
        String refreshToken
) { }
