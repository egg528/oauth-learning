package org.example.withoutspringsecurity.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthTokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        long expireIn,
        String scope,
        @JsonProperty("token_type")
        String tokenType
) {}
