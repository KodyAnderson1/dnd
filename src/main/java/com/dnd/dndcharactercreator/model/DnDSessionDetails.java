package com.dnd.dndcharactercreator.model;

import com.dnd.dndcharactercreator.model.chat.ChatSession;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class DnDSessionDetails {

  private String sessionId;
  private String StompId;
  private String name;
  private String description;
  private List<DnDUser> participants; // User IDs
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

  public void addParticipant(DnDUser participant) {
    log.info("Adding participant " + participant.getId() + " to session " + sessionId);

    boolean containsId = participants.stream().anyMatch(item -> Objects.equals(item.getId(), participant.getId()));

    if (!containsId) {
      participants.add(participant);
    }

  }

}
