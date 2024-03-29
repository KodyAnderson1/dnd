package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.DnDUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnDUserRepository extends JpaRepository<DnDUser, Long> {
  DnDUser findByUsername(String username);
}
