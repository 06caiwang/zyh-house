package com.zyh.gateway.handler;

import com.zyh.commondomain.domain.ResultCode;
import com.zyh.commondomain.exception.ServiceException;
import com.zyh.commonsecurity.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;

class GatewayExceptionHandlerContextTest {
    private final ReactiveWebApplicationContextRunner runner = new ReactiveWebApplicationContextRunner()
            .withUserConfiguration(TestWebConfiguration.class);

    @Test
    void reactiveGatewayLoadsOnlyReactiveExceptionHandler() {
        runner.run(context -> {
            assertThat(context).doesNotHaveBean(GlobalExceptionHandler.class);
            assertThat(context).hasSingleBean(GatewayExceptionHandler.class);
        });
    }

    @Test
    void controllerBusinessExceptionReachesGatewayHandler() {
        runner.run(context -> WebTestClient.bindToApplicationContext(context).build()
                .get().uri("/test/business-error").exchange()
                .expectStatus().isEqualTo(503)
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.code").isEqualTo(503000)
                .jsonPath("$.msg").isEqualTo("chat unavailable"));
    }

    @Test
    void unavailableUpstreamReturnsExistingGatewayJsonError() {
        runner.run(context -> WebTestClient.bindToApplicationContext(context).build()
                .get().uri("/test/no-instance").exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.code").isEqualTo(ResultCode.ERROR.getCode())
                .jsonPath("$.msg").isEqualTo(ResultCode.ERROR.getMsg()));
    }

    @Configuration(proxyBeanMethods = false)
    @EnableWebFlux
    @Import({GlobalExceptionHandler.class, GatewayExceptionHandler.class, ErrorController.class})
    static class TestWebConfiguration {
    }

    @RestController
    static class ErrorController {
        @GetMapping("/test/business-error")
        public String businessError() {
            throw new ServiceException("chat unavailable", 503000);
        }

        @GetMapping("/test/no-instance")
        public String noInstance() {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Unable to find instance for zyh-chat");
        }
    }
}
