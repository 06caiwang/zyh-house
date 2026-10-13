package com.zyh.chatservice.service.websocket;

import com.zyh.chatservice.domain.dto.WebSocketDTO;
import com.zyh.commonsecurity.domain.dto.LoginUserDTO;
import com.zyh.commonsecurity.service.TokenService;
import jakarta.websocket.CloseReason;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WebSocketServerTest {
    @Test
    void expiredLoginClosesWithPolicyViolation() throws Exception {
        TokenService tokens = mock(TokenService.class);
        WebSocketServer endpoint = new WebSocketServer();
        endpoint.setTokenService(tokens);
        Session session = mock(Session.class);
        when(session.getUserProperties()).thenReturn(Map.of("Authorization", "Bearer expired"));
        endpoint.onOpen(session);
        verify(tokens).getLoginUser("expired");
        verify(session).close(argThat(reason -> reason.getCloseCode().getCode() == CloseReason.CloseCodes.VIOLATED_POLICY.getCode()));
        endpoint.onClose();
    }

    @Test
    void reconnectSurvivesOldConnectionCloseAndRepliesToHeartbeat() throws Exception {
        TokenService tokens = mock(TokenService.class);
        LoginUserDTO user = new LoginUserDTO(); user.setUserId(Long.MAX_VALUE);
        when(tokens.getLoginUser("test")).thenReturn(user);
        WebSocketServer old = new WebSocketServer(); old.setTokenService(tokens);
        WebSocketServer current = new WebSocketServer();
        Session oldSession = session(); Session currentSession = session();
        RemoteEndpoint.Basic remote = mock(RemoteEndpoint.Basic.class);
        when(currentSession.getBasicRemote()).thenReturn(remote);
        try {
            old.onOpen(oldSession); current.onOpen(currentSession); old.onClose();
            current.onMessage("{\"type\":\"heart_beat\",\"data\":\"ping\"}");
            verify(remote).sendObject(argThat(value -> value instanceof WebSocketDTO<?> dto
                    && "heart_beat".equals(dto.getType()) && "pong".equals(dto.getData())));
            reset(remote);
            WebSocketDTO<String> frame = new WebSocketDTO<>("text", "isolated test");
            WebSocketServer.sendMessage(Long.MAX_VALUE, frame);
            verify(remote).sendObject(frame);
            current.onClose(); reset(remote);
            WebSocketServer.sendMessage(Long.MAX_VALUE, frame);
            verifyNoInteractions(remote);
        } finally { old.onClose(); current.onClose(); }
    }

    private Session session() {
        Session session = mock(Session.class);
        when(session.getUserProperties()).thenReturn(Map.of("Authorization", "Bearer test"));
        return session;
    }
}
