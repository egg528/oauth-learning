package org.example.withoutspringsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.client.OauthTokenClient;
import org.example.withoutspringsecurity.client.OauthTokenResponse;
import org.example.withoutspringsecurity.client.OauthUserClient;
import org.example.withoutspringsecurity.client.OauthUserResponse;
import org.example.withoutspringsecurity.repository.InMemoryMemberRepository;
import org.example.withoutspringsecurity.repository.Member;
import org.example.withoutspringsecurity.repository.OauthProvider;
import org.example.withoutspringsecurity.repository.InMemoryProviderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final InMemoryProviderRepository providerRepository;
    private final InMemoryMemberRepository memberRepository;
    private final OauthTokenClient oauthTokenClient;
    private final OauthUserClient oauthUserClient;
    private final JwtProvider jwtProvider;

    public String getLoginPageUrl(String providerName) {
        OauthProvider provider = providerRepository.findByProviderName(providerName);

        return combineLoginPageUrl(provider);
    }

    public LoginResponse login(String providerName, String code) {
        OauthProvider provider = providerRepository.findByProviderName(providerName);

        OauthTokenResponse token = oauthTokenClient.getToken(provider, code);

        OauthUserResponse userInfo = oauthUserClient.getUserInfo(providerName, provider, token.accessToken());

        Member member = memberRepository.upsert(userInfo.toMember());

        String accessToken = jwtProvider.createAccessToken(member.email());
        String refreshToken = jwtProvider.createRefreshToken();

        return new LoginResponse(member.email(), accessToken, refreshToken);
    }

    private String combineLoginPageUrl(OauthProvider provider) {
        return String.format(
                provider.authorizationUri(),
                provider.clientId(),
                provider.redirectUri(),
                provider.scope()
        );
    }
}
