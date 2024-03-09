package org.example.withoutspringsecurity.props;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
@ConfigurationProperties("oauth2")
public class OauthProps {
    private final Map<String, User> user = new HashMap<>();
    private final Map<String, Provider> provider = new HashMap<>();

    public List<String> keys() {
        var userKeys = user.keySet();
        var providerKeys = provider.keySet();

        return  userKeys.stream()
                .filter(providerKeys::contains)
                .toList();
    }

    public record User (
            String clientId,
            String clientSecret,
            String redirectUri,
            String scope
    ) {}

    public record Provider (
            String tokenUri,
            String userInfoUri,
            String authorizationUri
    ) {}
}
