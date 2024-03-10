package org.example.withoutspringsecurity.client;

import org.example.withoutspringsecurity.config.WebClientConfig;
import org.example.withoutspringsecurity.repository.OauthProvider;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Collections;


@Component
public class OauthTokenClient {

    public OauthTokenResponse getToken(OauthProvider provider, String code) {

        return WebClientConfig.baseWebClient.mutate().build()
                .post()
                .uri(provider.tokenUri())
                .headers(header -> {
                    header.setBasicAuth(provider.clientId(), provider.clientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(provider, code))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(OauthProvider provider, String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", provider.clientId());
        formData.add("client_secret", provider.clientSecret());
        formData.add("redirect_uri", provider.redirectUri());
        formData.add("grant_type", "authorization_code");
        return formData;
    }
}
