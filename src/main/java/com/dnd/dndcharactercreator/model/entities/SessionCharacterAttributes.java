package com.dnd.dndcharactercreator.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_session_character_attributes")
public class SessionCharacterAttributes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userSessionCharacterFk;
  private String userGuid;
  private String sessionGuid;

  private int experiencePoints;
  private double level;

  private double health;
  private double stamina;
  private double mana;

  private double strength;
  private double initiative;
  private double dexterity;
  private double constitution;
  private double intelligence;
  private double wisdom;
  private double charisma;
}
