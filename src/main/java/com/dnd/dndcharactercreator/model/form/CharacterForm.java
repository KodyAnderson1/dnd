package com.dnd.dndcharactercreator.model.form;

import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterForm {
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

  public CharacterForm(DnDCharacter character) {
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
