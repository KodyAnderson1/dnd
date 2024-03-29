package com.dnd.dndcharactercreator.repository;

import com.dnd.dndcharactercreator.model.entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
}
