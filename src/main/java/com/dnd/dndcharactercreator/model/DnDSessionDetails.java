package com.dnd.dndcharactercreator.model;

import com.dnd.dndcharactercreator.model.chat.ChatMessage;
import com.dnd.dndcharactercreator.model.chat.ChatSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DnDSessionDetails {

  private String sessionId;
  private String name;
  private String description;
  private List<Integer> participants; // User IDs
  private LocalDateTime startTime;
  private ChatSession chatSession; // Chat messages
  private Long creatorId; // User ID of the session creator

  public DnDSessionDetails(String sessionId, String name, String description) {
    this.sessionId = sessionId;
    this.name = name;
    this.description = description;
    this.participants = new ArrayList<>();
    this.startTime = LocalDateTime.now();
    this.chatSession = new ChatSession(sessionId);
    this.creatorId = null;
  }

  @Builder
  public DnDSessionDetails(String sessionId, String name, String description, int creatorId) {
    this.sessionId = sessionId;
    this.name = name;
    this.description = description;
    this.participants = new ArrayList<>();
    this.startTime = LocalDateTime.now();
    this.chatSession = new ChatSession(sessionId);
    this.creatorId = (long) creatorId;
  }

  public int getParticipantCount() {
    return participants.size();
  }

  public void addParticipant(int participantId) {
    System.out.println("Adding participant " + participantId + " to session " + sessionId);

    // If user is not already in the session, add them
    if (!participants.contains(participantId)) {
      participants.add(participantId);
    }

  }

  public ChatMessage addMessage(String sender, String content) {
    return chatSession.addMessage(new ChatMessage(sender, content));
  }

}
