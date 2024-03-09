package org.example.withoutspringsecurity.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

public class WebClientConfig {

    // timeout 관련
    private static final HttpClient httpClient =
            HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) Duration.ofSeconds(2).toMillis());
    public static final WebClient baseWebClient =
            WebClient.builder()
                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) // Max Data Size
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();

    protected ClientHttpConnector getClientHttpConnector(int timeoutSeconds) {
        // ConnectionProvider 생성 및 설정
        var connectionProvider = ConnectionProvider.builder("default-connection-provider")
                .maxConnections(500) // ConnectionProvider 을 지정하지 않은 경우, 커넥션풀 기본값
                .maxIdleTime(Duration.ofSeconds(3)) // 사용하지 않는 상태(idle)의 connection 이 유지되는시간.
                .maxLifeTime(Duration.ofSeconds(25)) // 태어난 지 25초가 지난 컨넥션은 죽이고, 새로 생성하도록 하자.
                .lifo() // 마지막에 사용된 커넥션 재사용 (fifo : 처음 사용된 커넥션 재사용 (default))
                .build();

        // HTTPS 인증서를 검증하지 않고 바로 접속하는 설정과, TCP 연결 시 ConnectionTimeOut , ReadTimeOut , WriteTimeOut 을
        // 적용하는 설정을 추가
        return new ReactorClientHttpConnector(
                HttpClient.create(connectionProvider)
                        .secure(sslContextSpec -> sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))
                        // connection time 설정
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) Duration.ofSeconds(timeoutSeconds).toMillis())
                        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(timeoutSeconds)).addHandlerLast(new WriteTimeoutHandler(timeoutSeconds))));
    }
}
