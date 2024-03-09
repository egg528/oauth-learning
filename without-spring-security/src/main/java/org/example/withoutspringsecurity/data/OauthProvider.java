package org.example.withoutspringsecurity.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.props.OauthProps.*;

@Getter
@RequiredArgsConstructor
public class OauthProvider {

    // user field
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String scope;

    // provider field
    private final String tokenUri;
    private final String userInfoUri;
    private final String authorizationUri;

    public static OauthProvider from(User user, Provider provider) {
        return new OauthProvider(
                user.getClientId(),
                user.getClientSecret(),
                user.getRedirectUri(),
                user.getScope(),
                provider.getTokenUri(),
                provider.getUserInfoUri(),
                provider.getAuthorizationUri()
        );
    }
}
