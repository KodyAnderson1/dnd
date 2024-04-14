package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<DnDCharacter, Long> {
  List<DnDCharacter> findAllByUserGuid(String userGuid);

  DnDCharacter findByIdAndUserGuid(Long id, String userGuid);

  void removeByIdAndUserGuid(Long id, String userGuid);
}
