package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.SessionCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionCharacterRepository extends JpaRepository<SessionCharacter, Long> {

  SessionCharacter findBySessionGuidAndUserGuid(String characterGuid, String userGuid);

}
