package com.dnd.dndcharactercreator.model.chat;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChatSession {
  // This class represents a chat session. It contains a list of messages and a session ID
  // Probably don't need this, the DnDSessionDetails class could just have a list of messages,
  // but it's here for now in case we want to add more chat functionality later and/or save chat history to a db.

  private String sessionId; // This is the same as the session ID in DnDSessionDetails. Useful for linking the two
  private List<ChatMessage> messages; // All messages in the chat

  @Builder
  public ChatSession(String sessionId) {
    this.sessionId = sessionId;
    this.messages = new ArrayList<>();
  }

  public ChatMessage addMessage(ChatMessage message) {
    messages.add(message);
    return message;
  }


}
