package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.DnDClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnDClassRepository extends JpaRepository<DnDClass, Long> {
}
