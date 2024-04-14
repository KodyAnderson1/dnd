package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.entities.DnDClass;
import com.dnd.dndcharactercreator.model.entities.Race;
import com.dnd.dndcharactercreator.service.CharacterService;
import com.dnd.dndcharactercreator.service.PoorManCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoorManCacheImpl implements PoorManCache {

  private final CharacterService characterService;

  private List<DnDClass> dndClasses;
  private List<Race> dndRaces;

  @PostConstruct
  private void init() {
    fetchClasses();
    fetchRaces();
  }

  @Override
  public List<DnDClass> getAllClasses() {
    if (dndClasses == null || dndClasses.isEmpty()) {
      fetchClasses();
    }

    return dndClasses;
  }

  @Override
  public List<Race> getAllRaces() {
    if (dndRaces == null || dndRaces.isEmpty()) {
      fetchRaces();
    }

    return dndRaces;
  }

  @Override
  public void invalidateDnDClasses() {
    dndClasses = null;
  }

  @Override
  public void invalidateDnDRaces() {
    dndRaces = null;
  }

  private void fetchClasses() {
    dndClasses = characterService.getAllClasses();
  }

  private void fetchRaces() {
    dndRaces = characterService.getAllRaces();
  }

}
