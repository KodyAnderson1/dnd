package com.dnd.dndcharactercreator.model.session;

import com.dnd.dndcharactercreator.model.chat.ChatSession;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.SessionCharacter;
import com.dnd.dndcharactercreator.model.entities.SessionCharacterAttributes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ActiveSession {

  private String sessionId;
  private String name;
  private String description;
  private List<SessionParticipant> activeParticipants;
  private LocalDateTime startTime;
  private ChatSession chatSession;
  private String dungeonMasterGuid;
  private String inviteCode;

  public ActiveSession(String sessionId, String name, String description) {
    this.sessionId = sessionId;
    this.name = name;
    this.description = description;
    this.activeParticipants = new ArrayList<>();
    this.startTime = LocalDateTime.now();
    this.chatSession = new ChatSession(sessionId);
    this.inviteCode = UUID.randomUUID().toString();
  }

  public ActiveSession(String sessionId, String name, String description, String dmGuid) {
    this.sessionId = sessionId;
    this.name = name;
    this.description = description;
    this.activeParticipants = new ArrayList<>();
    this.startTime = LocalDateTime.now();
    this.chatSession = new ChatSession(sessionId);
    this.dungeonMasterGuid = dmGuid;
    this.inviteCode = UUID.randomUUID().toString();
  }

  @Builder
  public ActiveSession(String sessionId, String name, String description, String dungeonMasterGuid, String inviteCode) {
    this.sessionId = sessionId;
    this.name = name;
    this.description = description;
    this.activeParticipants = new ArrayList<>();
    this.startTime = LocalDateTime.now();
    this.chatSession = new ChatSession(sessionId);
    this.dungeonMasterGuid = dungeonMasterGuid;
    this.inviteCode = inviteCode;
  }

  public int getParticipantCount() {
    return activeParticipants.size();
  }

  public void addParticipant(DnDUser participant, String stompId, SessionCharacter character, SessionCharacterAttributes attributes) {

    boolean alreadyActive = activeParticipants.stream().anyMatch(item -> Objects.equals(item.getUser().getId(), participant.getId()));
    if (!alreadyActive) {
      activeParticipants.add(new SessionParticipant(participant, stompId, character, attributes));
    }
  }

  public void removeParticipant(String userGuid) {
    activeParticipants.removeIf(participant -> participant.getUser().getGuid().equals(userGuid));
  }

  public List<String> getStompIds() {
    return this.activeParticipants.stream().map(SessionParticipant::getStompId).toList();
  }

}
