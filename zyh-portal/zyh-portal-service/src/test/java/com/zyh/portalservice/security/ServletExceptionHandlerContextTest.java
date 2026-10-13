package com.zyh.portalservice.security;

import com.zyh.commonsecurity.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class ServletExceptionHandlerContextTest {
    @Test
    void servletApplicationKeepsItsExceptionHandler() {
        new WebApplicationContextRunner().withUserConfiguration(GlobalExceptionHandler.class)
                .run(context -> assertThat(context).hasSingleBean(GlobalExceptionHandler.class));
    }

    @Test
    void nonWebApplicationDoesNotLoadServletAdvice() {
        new ApplicationContextRunner().withUserConfiguration(GlobalExceptionHandler.class)
                .run(context -> assertThat(context).doesNotHaveBean(GlobalExceptionHandler.class));
    }
}
