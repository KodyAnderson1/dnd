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
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dnd_sessions")
public class DnDSession {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String description;
  private String guid;
  private String dungeonMasterGuid;
  private String created;
  private String inviteCode;

  @Builder
  public DnDSession(String name, String description, String guid, String dmGuid, String inviteCode) {
    this.name = name;
    this.description = description;
    this.guid = guid;
    this.dungeonMasterGuid = dmGuid;
    this.inviteCode = inviteCode;
  }
}
