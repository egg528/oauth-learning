package org.example.withoutspringsecurity.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public record Member (
        String email
) { }
