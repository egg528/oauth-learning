package org.example.withoutspringsecurity.data;

import org.example.withoutspringsecurity.props.OauthProps.*;

public record OauthProvider (
        // user field
        String clientId,
        String clientSecret,
        String redirectUri,
        String scope,
        // provider field
        String tokenUri,
        String userInfoUri,
        String authorizationUri
) {
    public static OauthProvider from(User user, Provider provider) {
        return new OauthProvider(
                user.clientId(),
                user.clientSecret(),
                user.redirectUri(),
                user.scope(),
                provider.tokenUri(),
                provider.userInfoUri(),
                provider.authorizationUri()
        );
    }
}
