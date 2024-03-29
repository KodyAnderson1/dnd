package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.DnDSessionDetails;
import com.dnd.dndcharactercreator.model.chat.ChatMessage;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.service.DnDSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DnDSessionServiceImpl implements DnDSessionService {

  // In Memory storage of sessions. Will reset on server restart
  private final ConcurrentHashMap<String, DnDSessionDetails> sessions;

  public DnDSessionServiceImpl() {
    sessions = new ConcurrentHashMap<>();

    // TODO: Load sessions from database

  }

  @Override
  public DnDSessionDetails createSession(String name, String description) {
    String sessionId = UUID.randomUUID().toString(); // Probably not the best way to generate session IDs
    DnDSessionDetails session = new DnDSessionDetails(sessionId, name, description, getUserId());

    // TODO: Save the session to database


    return sessions.computeIfAbsent(sessionId, newSession -> session); // Add session to in memory storage and return it
  }

  @Override
  public List<DnDSessionDetails> getAllSessions() {
    return List.copyOf(sessions.values());
  }

  @Override
  public DnDSessionDetails joinSession(String sessionId) {
    // This assumes that the session exists, will be null if it doesn't
    DnDSessionDetails session = sessions.get(sessionId);

    session.addParticipant(getUserId());

    return session;
  }

  @Override
  public void createManySessions(List<DnDSessionDetails> sessions) {
    sessions.forEach(session -> this.sessions.put(session.getSessionId(), session));
  }

  @Override
  public ChatMessage addChatMessage(String sessionId, ChatMessage message) {
    return sessions.get(sessionId).getChatSession().addMessage(message);
  }

  @Override
  public List<ChatMessage> getChatMessages(String sessionId) {
    return sessions.get(sessionId).getChatSession().getMessages();
  }

  private int getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    DnDUser userDetails = (DnDUser) authentication.getPrincipal();
    return Math.toIntExact(userDetails.getId());
  }
}
