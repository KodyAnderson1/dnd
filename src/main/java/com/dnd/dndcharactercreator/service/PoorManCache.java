package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.entities.DnDClass;
import com.dnd.dndcharactercreator.model.entities.Race;

import java.util.List;

public interface PoorManCache {

  List<DnDClass> getAllClasses();

  List<Race> getAllRaces();

  void invalidateDnDClasses();

  void invalidateDnDRaces();

}
