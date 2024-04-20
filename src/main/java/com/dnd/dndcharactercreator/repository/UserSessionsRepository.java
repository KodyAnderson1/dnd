package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.DnDUserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSessionsRepository extends JpaRepository<DnDUserSession, Long> {

  DnDUserSession findByUserGuidAndSessionGuid(String userGuid, String sessionGuid);

  List<DnDUserSession> findAllByUserGuid(String userGuid);

  List<DnDUserSession> findAllBySessionGuid(String sessionGuid);

  int countBySessionGuid(String sessionGuid);

}
