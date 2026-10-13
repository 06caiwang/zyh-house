package com.zyh.chatservice.config;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class WebSocketConfigTest {
    @Test
    void authorizationHeaderIsCaseInsensitive() {
        for (String name : List.of("Authorization", "authorization", "AUTHORIZATION")) {
            HandshakeRequest request = mock(HandshakeRequest.class);
            ServerEndpointConfig endpoint = mock(ServerEndpointConfig.class);
            Map<String, Object> properties = new HashMap<>();
            when(request.getHeaders()).thenReturn(Map.of(name, List.of("Bearer test-token")));
            when(endpoint.getUserProperties()).thenReturn(properties);
            new WebSocketConfig().modifyHandshake(endpoint, request, mock(HandshakeResponse.class));
            assertThat(properties).containsEntry("Authorization", "Bearer test-token");
        }
    }

    @Test
    void missingOrBlankHeaderCannotReusePreviousCredential() {
        for (Map<String, List<String>> headers : List.of(Map.<String, List<String>>of(), Map.of("authorization", List.of(" ")))) {
            HandshakeRequest request = mock(HandshakeRequest.class);
            ServerEndpointConfig endpoint = mock(ServerEndpointConfig.class);
            Map<String, Object> properties = new HashMap<>(Map.of("Authorization", "previous-token"));
            when(request.getHeaders()).thenReturn(headers);
            when(endpoint.getUserProperties()).thenReturn(properties);
            assertThatThrownBy(() -> new WebSocketConfig().modifyHandshake(endpoint, request, mock(HandshakeResponse.class)))
                    .isInstanceOf(RuntimeException.class);
            assertThat(properties).doesNotContainKey("Authorization");
        }
    }
}
