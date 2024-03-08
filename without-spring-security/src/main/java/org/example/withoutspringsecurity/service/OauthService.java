package org.example.withoutspringsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.utils.OauthServerUrlCreator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final OauthServerUrlCreator oauthServerUrlCreator;

    public String getOauthServerUrl(String providerName) {
        return oauthServerUrlCreator.create(providerName);
    }
}
