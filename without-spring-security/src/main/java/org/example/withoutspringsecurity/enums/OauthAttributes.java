package org.example.withoutspringsecurity.enums;

import org.example.withoutspringsecurity.client.OauthUserResponse;

import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {
    GOOGLE("google") {
        @Override
        public OauthUserResponse of(Map<String, Object> attributes) {
            return new OauthUserResponse((String) attributes.get("email"));
        }
    };

    private final String providerName;

    OauthAttributes(String name) {
        this.providerName = name;
    }

    public static OauthUserResponse extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s는 존재하지 않는 Provider입니다.", providerName)))
                .of(attributes);
    }

    public abstract OauthUserResponse of(Map<String, Object> attributes);
}
