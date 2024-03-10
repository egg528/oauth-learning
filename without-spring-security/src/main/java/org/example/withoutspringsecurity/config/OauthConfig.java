package org.example.withoutspringsecurity.config;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.repository.OauthProvider;
import org.example.withoutspringsecurity.props.OauthProps;
import org.example.withoutspringsecurity.repository.InMemoryProviderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class OauthConfig {

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository(OauthProps oauthProps) {
        List<String> providerKeys = oauthProps.keys();

        Map<String, OauthProvider> providers = providerKeys.stream()
                .collect(Collectors.toMap(
                        key -> key,
                        key -> OauthProvider.from(
                                oauthProps.getUser().get(key),
                                oauthProps.getProvider().get(key)
                        )
                ));

        return new InMemoryProviderRepository(providers);
    }
}
