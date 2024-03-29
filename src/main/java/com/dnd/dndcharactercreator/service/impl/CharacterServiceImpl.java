package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDClass;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.Race;
import com.dnd.dndcharactercreator.repository.CharacterRepository;
import com.dnd.dndcharactercreator.repository.DnDClassRepository;
import com.dnd.dndcharactercreator.repository.RaceRepository;
import com.dnd.dndcharactercreator.service.CharacterService;
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
    dnDCharacter.setUserId(getUserId());
    return characterRepository.save(dnDCharacter);
  }

  public DnDCharacter getCharacterById(Long id) {
    return characterRepository.findById(id).orElseThrow(() -> new RuntimeException("Character not found"));
  }

  public List<DnDCharacter> getAllCharactersByUser() {
    return characterRepository.findAllByUserId(getUserId());
  }

  @Override
  public List<DnDCharacter> getAllCharacters() {
    return characterRepository.findAll();
  }

  public void updateCharacter(DnDCharacter dnDCharacter) {
    characterRepository.save(dnDCharacter);
  }

  @Override
  public void deleteCharacter(Long id) {

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

  private int getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    DnDUser userDetails = (DnDUser) authentication.getPrincipal();
    return Math.toIntExact(userDetails.getId());
  }

}
