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

  void updateCharacter(DnDCharacter dnDCharacter);

  void deleteCharacter(Long id, String userId);


  //  TODO: Create Separate Service for below
  List<DnDClass> getAllClasses();

  List<Race> getAllRaces();

  void saveAllRaces(List<Race> races);

  void saveAllClasses(List<DnDClass> classes);

}
