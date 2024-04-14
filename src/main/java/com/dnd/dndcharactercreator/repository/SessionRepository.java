package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.DnDSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<DnDSession, Long> {

  DnDSession findByGuid(String sessionGuid);

  void deleteByGuid(String sessionGuid);
}
