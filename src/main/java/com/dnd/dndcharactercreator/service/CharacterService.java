package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDClass;
import com.dnd.dndcharactercreator.model.entities.Race;

import java.util.List;

public interface CharacterService {
  List<DnDCharacter> saveAllCharacters(List<DnDCharacter> characters);

  DnDCharacter saveCharacter(DnDCharacter dnDCharacter);

  DnDCharacter getCharacterById(Long id);

  List<DnDCharacter> getAllCharactersByUser();

  List<DnDCharacter> getAllCharacters();

  void updateCharacter(DnDCharacter dnDCharacter);

  void deleteCharacter(Long id);

  List<DnDClass> getAllClasses();

  List<Race> getAllRaces();

  void saveAllRaces(List<Race> races);

  void saveAllClasses(List<DnDClass> classes);

}
