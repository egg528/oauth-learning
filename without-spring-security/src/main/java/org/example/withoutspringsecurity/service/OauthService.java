package org.example.withoutspringsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.client.OauthTokenClient;
import org.example.withoutspringsecurity.client.OauthTokenResponse;
import org.example.withoutspringsecurity.data.OauthProvider;
import org.example.withoutspringsecurity.repository.InMemoryProviderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final InMemoryProviderRepository providerRepository;
    private final OauthTokenClient oauthTokenClient;

    public String getLoginPageUrl(String providerName) {
        OauthProvider provider = providerRepository.findByProviderName(providerName);

        return combineLoginPageUrl(provider);
    }

    public String login(String providerName, String code) {
        OauthProvider provider = providerRepository.findByProviderName(providerName);

        OauthTokenResponse token = oauthTokenClient.getToken(provider, code);


        return "";
    }

    private String combineLoginPageUrl(OauthProvider provider) {
        return String.format(
                provider.authorizationUri(),
                provider.clientId(),
                provider.redirectUri(),
                provider.scope()
        );
    }
}
