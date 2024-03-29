package org.example.withoutspringsecurity.repository;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class InMemoryProviderRepository {
    private final Map<String, OauthProvider> providers;

    public OauthProvider findByProviderName(String name) {
        if (!providers.containsKey(name)) {
            throw new NoSuchElementException(String.format("%s는 존재하지 않는 Provider입니다.", name));
        }

        return providers.get(name);
    }
}
