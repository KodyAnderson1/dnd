package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.exception.NotAuthorizedException;
import com.dnd.dndcharactercreator.exception.SessionNotFoundException;
import com.dnd.dndcharactercreator.model.ActiveSession;
import com.dnd.dndcharactercreator.model.ExpandedDnDCharacter;
import com.dnd.dndcharactercreator.model.SessionParticipant;
import com.dnd.dndcharactercreator.model.chat.ChatMessage;
import com.dnd.dndcharactercreator.model.entities.DnDSession;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.service.ActiveSessionManager;
import com.dnd.dndcharactercreator.service.SessionService;
import com.dnd.dndcharactercreator.utils.ExceptionCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Service
public class ActiveSessionManagerImpl implements ActiveSessionManager {

  // In Memory storage of sessions. Will reset on server restart
  private final ConcurrentHashMap<String, ActiveSession> sessions;
  private final SessionService sessionService;

  @Autowired
  public ActiveSessionManagerImpl(SessionService sessionService) {
    this.sessions = new ConcurrentHashMap<>();
    this.sessionService = sessionService;
  }

  @Override
  public ActiveSession startSession(String sessionGuid) {
    DnDSession session = sessionService.getSession(sessionGuid);

    if (session == null) {
      log.warn("User {} tried to start a non-existent session. Id {}", getUserGuid(), sessionGuid);
      throw new SessionNotFoundException("User {} tried to start a non-existent session. Id {}", ExceptionCodes.AE102);
    }

    if (!session.getDungeonMasterGuid().equalsIgnoreCase(getUserGuid())) {
      log.warn("User {} tried to start a session they are not the DM of. Id {}", getUserGuid(), sessionGuid);
      throw new NotAuthorizedException("User {} tried to start a session they are not the DM of. Id {}", ExceptionCodes.AE101);
    }

    ActiveSession activeSession = ActiveSession.builder()
            .sessionId(session.getGuid())
            .name(session.getName())
            .description(session.getDescription())
            .dungeonMasterGuid(session.getDungeonMasterGuid())
            .inviteCode(UUID.randomUUID().toString())
            .build();

    return sessions.putIfAbsent(activeSession.getSessionId(), activeSession);
  }

  @Override
  public List<ActiveSession> getAllSessions() {
    return List.copyOf(sessions.values());
  }

  @Override
  public ActiveSession joinSession(String sessionId, DnDUser user, String stompId) {
    ActiveSession session = sessions.get(sessionId);

    if (session == null) {
      log.warn("User {} tried to join a non-existent session. Id {}", user.getGuid(), sessionId);
      throw new SessionNotFoundException("User {} tried to join a non-existent session. Id {}", ExceptionCodes.AE102);
    }

    ExpandedDnDCharacter character = sessionService.getCharacter(sessionId, user.getGuid());

    log.info("Attempting to add participant " + user.getId() + " to session " + sessionId);
    session.addParticipant(user, stompId, character.character(), character.attributes());

    return session;
  }

  @Override
  public ActiveSession getSession(String sessionId) {
    return sessions.get(sessionId);
  }

  @Override
  public ChatMessage addChatMessage(String sessionId, ChatMessage message) {
    return sessions.get(sessionId).getChatSession().addMessage(message);
  }

  @Override
  public List<DnDUser> getActiveUsers(String sessionId) {
    return sessions.get(sessionId).getActiveParticipants().stream().map(SessionParticipant::getUser).toList();
  }

  private String getUserGuid() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    DnDUser userDetails = (DnDUser) authentication.getPrincipal();
    return userDetails.getGuid();
  }
}
