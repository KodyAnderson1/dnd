package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.DnDSessionDetails;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.service.DnDSessionService;
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
  private final DnDSessionService dnDSessionService;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("=== Connected Event Occurred");
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String sessionId = getSessionId(accessor);
    DnDUser user = getUser(event);

    if (sessionId == null) {
      log.info("SessionId is null in the SessionConnectedEvent");
      return;
    }

    DnDSessionDetails activeSession = dnDSessionService.getSession(sessionId);

    if (activeSession == null) {
      log.info("Active session not found for sessionId: " + sessionId);
      return;
    }

    if (user == null) {
      log.info("User Is null for sessionId: " + sessionId);
      return;
    }

    log.info("Added participant to session");
    activeSession.addParticipant(user);

    if (activeSession.getStompId() == null || activeSession.getStompId().isBlank()) {
      activeSession.setStompId(accessor.getSessionId());
    }

    messagingTemplate.convertAndSend("/topic/activeUsers/" + sessionId, activeSession.getParticipants());
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

    DnDUser user = getUser(event);

    if (user == null) {
      log.info("User not found for the disconnected session.");
      return;
    }

    DnDSessionDetails currSession = dnDSessionService.getAllSessions().stream()
            .filter(session -> stompId.equals(session.getStompId()))
            .findFirst()
            .orElse(null);

    if (currSession == null) {
      log.warn("Could not find session with stompId {}", stompId);
      return;
    }

    List<DnDUser> participants = currSession.getParticipants();
    participants.removeIf(participant -> participant.getId().equals(user.getId()));

    log.info("Removed participant from session");
    messagingTemplate.convertAndSend("/topic/activeUsers/" + currSession.getSessionId(), participants);
  }

  private DnDUser getUser(SessionConnectedEvent event) {
    Principal principal = event.getUser();
    if (principal instanceof Authentication authentication) {
      Object principalDetails = authentication.getPrincipal();
      if (principalDetails instanceof DnDUser) {
        return (DnDUser) principalDetails;
      }
    }
    return null;
  }

  private DnDUser getUser(SessionDisconnectEvent event) {
    Principal principal = event.getUser();
    if (principal instanceof Authentication authentication) {
      Object principalDetails = authentication.getPrincipal();
      if (principalDetails instanceof DnDUser) {
        return (DnDUser) principalDetails;
      }
    }
    return null;
  }

  private String getSessionId(StompHeaderAccessor accessor) {
    GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
    if (nonNull(generic)) {
      SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
      List<String> userIdValue = nativeAccessor.getNativeHeader("sessionId");

      return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
    }

    return null;
  }
}
