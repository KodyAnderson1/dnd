package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.DnDSessionDetails;
import com.dnd.dndcharactercreator.model.chat.ChatMessage;
import com.dnd.dndcharactercreator.model.entities.DnDUser;

import java.util.List;

public interface DnDSessionService {

  DnDSessionDetails createSession(String name, String description);

  List<DnDSessionDetails> getAllSessions();

  DnDSessionDetails joinSession(String sessionId, DnDUser user);

  DnDSessionDetails getSession(String sessionId);

  void createManySessions(List<DnDSessionDetails> sessions);

  ChatMessage addChatMessage(String sessionId, ChatMessage message);

//  List<ChatMessage> getChatMessages(String sessionId);

  List<DnDUser> getActiveUsers(String sessionId);

}
