package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.exception.DnDException;
import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDClass;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.Race;
import com.dnd.dndcharactercreator.repository.CharacterRepository;
import com.dnd.dndcharactercreator.repository.DnDClassRepository;
import com.dnd.dndcharactercreator.repository.RaceRepository;
import com.dnd.dndcharactercreator.service.CharacterService;
import com.dnd.dndcharactercreator.utils.ExceptionCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

  private final CharacterRepository characterRepository;
  private final DnDClassRepository classRepository;
  private final RaceRepository raceRepository;

  @Override
  public List<DnDCharacter> saveAllCharacters(List<DnDCharacter> characters) {
    return characterRepository.saveAll(characters);
  }

  public DnDCharacter saveCharacter(DnDCharacter dnDCharacter) {
    dnDCharacter.setUserGuid(getUserGuid());
    return characterRepository.save(dnDCharacter);
  }

  public DnDCharacter getCharacterById(Long id) {
    String userGuid = getUserGuid();
    DnDCharacter character = characterRepository.findByIdAndUserGuid(id, userGuid);

    if (character == null) {
      throw new DnDException("No Character with Id " + id + " found for " + userGuid, ExceptionCodes.AE103);
    }

    return character;
  }

  public List<DnDCharacter> getAllCharactersByUser() {
    return characterRepository.findAllByUserGuid(getUserGuid());
  }

  public void updateCharacter(DnDCharacter dnDCharacter) {
    characterRepository.save(dnDCharacter);
  }

  @Override
  public void deleteCharacter(Long id, String userGuid) {
    characterRepository.removeByIdAndUserGuid(id, userGuid);
  }

  @Override
  public List<DnDClass> getAllClasses() {
    return classRepository.findAll();
  }

  @Override
  public List<Race> getAllRaces() {
    return raceRepository.findAll();
  }

  @Override
  public void saveAllRaces(List<Race> races) {
    raceRepository.saveAll(races);
  }

  @Override
  public void saveAllClasses(List<DnDClass> classes) {
    classRepository.saveAll(classes);
  }

  private String getUserGuid() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    DnDUser userDetails = (DnDUser) authentication.getPrincipal();
    return userDetails.getGuid();
  }

  private Long getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    DnDUser userDetails = (DnDUser) authentication.getPrincipal();
    return userDetails.getId();
  }

}
