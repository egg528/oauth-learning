package org.example.withoutspringsecurity.utils;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.props.OauthProps;
import org.example.withoutspringsecurity.props.OauthProps.User;
import org.example.withoutspringsecurity.props.OauthProps.Provider;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class OauthServerUrlCreator {

    private final OauthProps oauthProps;

    public String create(String providerName) {
        if (!oauthProps.contains(providerName)) {
            throw new NoSuchElementException("지원하지 않는 소셜 로그인입니다.");
        }

        User user = oauthProps.getUsers().get(providerName);
        Provider provider = oauthProps.getProviders().get(providerName);

        return String.format(
                provider.getAuthorizationUri(),
                user.getClientId(),
                user.getRedirectUri(),
                user.getScope()
        );
    }
}
