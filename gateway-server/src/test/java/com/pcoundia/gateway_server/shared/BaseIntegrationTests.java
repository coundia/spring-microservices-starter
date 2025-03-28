package com.pcoundia.gateway_server.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.config.name=application-test"
)
@Profile("test")

@Slf4j
public class BaseIntegrationTests {

    @LocalServerPort
    public int port;

    @Value("${server.address}")
    public String host;

    @Autowired
    public WebTestClient webTestClient;

    public String getBaseUrl() {
        String uri = "http://"+this.host + ":" + this.port + "/api/";

        log.info("***************** URI ********************");
        log.info(uri);

        return uri;
    }

}
