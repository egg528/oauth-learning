package org.example.withoutspringsecurity.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
@ConfigurationProperties("oauth2")
public class OauthProps {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Provider> providers = new HashMap<>();

    public boolean contains(String providerName) {
        return providers.containsKey(providerName);
    }

    @Getter
    @Setter
    public static class User {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String scope;
    }

    @Getter
    @Setter
    public static class Provider {
        private String tokenUri;
        private String userInfoUri;
        private String userNameAttribute;
        private String authorizationUri;
    }
}
