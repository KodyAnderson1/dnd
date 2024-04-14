package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.SessionCharacterAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionCharacterAttributesRepository extends JpaRepository<SessionCharacterAttributes, Long> {

  List<SessionCharacterAttributes> findAllByUserSessionCharacterFkIn(List<Long> ids);

  List<SessionCharacterAttributes> findAllBySessionGuidIn(List<String> sessionGuids);

  SessionCharacterAttributes findByUserSessionCharacterFk(Long id);
}
