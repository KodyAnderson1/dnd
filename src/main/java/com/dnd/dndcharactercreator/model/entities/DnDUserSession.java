package com.dnd.dndcharactercreator.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_sessions")
public class DnDUserSession {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userGuid;
  private String sessionGuid;
  private Long characterId;
  private Long attributesId;
  private boolean isDungeonMaster;
  private String created;

  @Builder
  public DnDUserSession(String userGuid, String sessionGuid, Long characterId, Long attributesId, boolean isDungeonMaster) {
    this.userGuid = userGuid;
    this.sessionGuid = sessionGuid;
    this.characterId = characterId;
    this.attributesId = attributesId;
    this.isDungeonMaster = isDungeonMaster;
  }

}
