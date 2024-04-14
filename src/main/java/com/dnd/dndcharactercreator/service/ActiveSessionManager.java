package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.ActiveSession;
import com.dnd.dndcharactercreator.model.chat.ChatMessage;
import com.dnd.dndcharactercreator.model.entities.DnDUser;

import java.util.List;

public interface ActiveSessionManager {

  ActiveSession startSession(String sessionId);

  List<ActiveSession> getAllSessions();

  ActiveSession joinSession(String sessionId, DnDUser user, String stompId);

  ActiveSession getSession(String sessionId);

  ChatMessage addChatMessage(String sessionId, ChatMessage message);

  List<DnDUser> getActiveUsers(String sessionId);

}
