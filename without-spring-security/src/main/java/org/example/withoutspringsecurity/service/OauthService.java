package org.example.withoutspringsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.data.OauthProvider;
import org.example.withoutspringsecurity.repository.InMemoryProviderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final InMemoryProviderRepository providerRepository;

    public String getLoginPageUrl(String providerName) {
        OauthProvider provider = providerRepository.findByProviderName(providerName);

        String url = combineLoginPageUrl(provider);

        return url;
    }

    private String combineLoginPageUrl(OauthProvider provider) {
        return String.format(
                provider.getAuthorizationUri(),
                provider.getClientId(),
                provider.getRedirectUri(),
                provider.getScope()
        );
    }
}
