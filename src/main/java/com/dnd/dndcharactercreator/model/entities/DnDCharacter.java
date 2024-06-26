package com.dnd.dndcharactercreator.model.entities;

import com.dnd.dndcharactercreator.model.form.CharacterForm;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dnd_characters")
public class DnDCharacter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String userGuid;
  private String name;
  private String characterClass;
  private String race;
  private String icon;

  private double strength;
  private double initiative;
  private double dexterity;
  private double constitution;
  private double intelligence;
  private double wisdom;
  private double charisma;

  public DnDCharacter(SessionCharacter character) {
    this.userGuid = character.getUserGuid();
    this.name = character.getName();
    this.characterClass = character.getCharacterClass();
    this.race = character.getRace();
    this.icon = character.getIcon();
  }

  public DnDCharacter(CharacterForm character) {
    this.id = character.getId();
    this.userGuid = character.getUserGuid();

    this.name = character.getName();
    this.characterClass = character.getCharacterClass();
    this.race = character.getRace();
    this.icon = character.getIcon();

    this.strength = character.getStrength();
    this.initiative = character.getInitiative();
    this.dexterity = character.getDexterity();
    this.constitution = character.getConstitution();
    this.intelligence = character.getIntelligence();
    this.wisdom = character.getWisdom();
    this.charisma = character.getCharisma();
  }
}
