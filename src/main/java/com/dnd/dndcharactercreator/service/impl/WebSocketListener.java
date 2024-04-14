package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.ActiveSession;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.service.ActiveSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketListener {

  private final SimpMessagingTemplate messagingTemplate;
  private final ActiveSessionManager activeSessionManager;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("=== Connected Event Occurred");
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String dndSessionId = getSessionId(accessor);

    if (dndSessionId == null) {
      log.info("SessionId is null in the SessionConnectedEvent");
      return;
    }

    DnDUser user = getUser(event.getUser());

    if (user == null) {
      log.info("User not found to connect to session {}", dndSessionId);
      return;
    }

    ActiveSession activeSession = activeSessionManager.getSession(dndSessionId);

    if (activeSession == null) {
      log.info("Active session not found for sessionId: " + dndSessionId);
      return;
    }

    log.info("Added participant to session");
    activeSessionManager.joinSession(dndSessionId, user, accessor.getSessionId());

    messagingTemplate.convertAndSend("/topic/activeUsers/" + dndSessionId, activeSession.getActiveParticipants());
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    log.info("=== Disconnected Event Occurred");
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String stompId = accessor.getSessionId();

    if (stompId == null) {
      log.info("stompId not found or session does not exist.");
      return;
    }

    DnDUser user = getUser(event.getUser());

    if (user == null) {
      log.info("User not found for the disconnected session.");
      return;
    }

    ActiveSession currSession = activeSessionManager.getAllSessions().stream()
            .filter(session -> session.getStompIds().contains(stompId))
            .findFirst()
            .orElse(null);

    if (currSession == null) {
      log.warn("Could not find session with stompId {}", stompId);
      return;
    }

    currSession.removeParticipant(user.getGuid());

    log.info("Removed participant from session");
    messagingTemplate.convertAndSend("/topic/activeUsers/" + currSession.getSessionId(), currSession.getActiveParticipants());
  }

  private DnDUser getUser(Principal principal) {
    if (principal instanceof Authentication authentication) {
      Object principalDetails = authentication.getPrincipal();
      if (principalDetails instanceof DnDUser) {
        return (DnDUser) principalDetails;
      }
    }

    log.info("Principal != InstanceOf Authentication, returning...");
    return null;
  }

  private String getSessionId(StompHeaderAccessor accessor) {
    GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
    if (nonNull(generic)) {
      SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
      List<String> userIdValue = nativeAccessor.getNativeHeader("sessionId");

      return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
    }

    log.info("Generic is null, returning...");
    return null;
  }
}
