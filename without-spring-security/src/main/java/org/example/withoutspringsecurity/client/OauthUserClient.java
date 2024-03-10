package org.example.withoutspringsecurity.client;

import org.example.withoutspringsecurity.config.WebClientConfig;
import org.example.withoutspringsecurity.enums.OauthAttributes;
import org.example.withoutspringsecurity.repository.OauthProvider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OauthUserClient {
    public OauthUserResponse getUserInfo(String providerName, OauthProvider provider, String accessToken) {
        Map<String, Object> userAttributes = WebClientConfig.baseWebClient.mutate().build()
                .get()
                .uri(provider.userInfoUri())
                .headers(header -> header.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return OauthAttributes.extract(providerName, userAttributes);
    }
}
