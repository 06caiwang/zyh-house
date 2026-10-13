package com.zyh.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.util.ClassUtils;

import static org.assertj.core.api.Assertions.assertThat;

class GatewayNettyRuntimeTest {
    @Test
    void gatewayUsesNettyWithoutTransitiveTomcat() {
        new ReactiveWebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ReactiveWebServerFactoryAutoConfiguration.class))
                .run(context -> {
                    assertThat(context).hasSingleBean(NettyReactiveWebServerFactory.class);
                    assertThat(ClassUtils.isPresent("org.apache.catalina.startup.Tomcat", context.getClassLoader())).isFalse();
                    assertThat(ClassUtils.isPresent("org.springframework.web.servlet.DispatcherServlet", context.getClassLoader())).isFalse();
                });
    }
}
