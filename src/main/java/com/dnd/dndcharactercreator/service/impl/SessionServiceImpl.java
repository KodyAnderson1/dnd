package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.ExpandedDnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDSession;
import com.dnd.dndcharactercreator.model.entities.SessionCharacter;
import com.dnd.dndcharactercreator.model.entities.SessionCharacterAttributes;
import com.dnd.dndcharactercreator.repository.SessionCharacterAttributesRepository;
import com.dnd.dndcharactercreator.repository.SessionCharacterRepository;
import com.dnd.dndcharactercreator.repository.SessionRepository;
import com.dnd.dndcharactercreator.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

  private final SessionRepository sessionRepository;
  private final SessionCharacterAttributesRepository sessionCharacterAttributesRepository;
  private final SessionCharacterRepository sessionCharacterRepository;

  @Override
  public List<DnDSession> getAllSessions() {
    return sessionRepository.findAll();
  }

  @Override
  public int getTotalMembers(String sessionGuid) {
    return sessionCharacterRepository.countBySessionGuid(sessionGuid);
  }

  @Override
  public String getDMGuid(String sessionGuid) {
    return sessionRepository.findByGuid(sessionGuid).getDungeonMasterGuid();
  }

  @Override
  public boolean verifyInviteCode(String sessionGuid, String code) {
    DnDSession session = sessionRepository.findByGuid(sessionGuid);

    return session != null && session.getInviteCode().equals(code);
  }

  @Override
  public DnDSession getSession(String sessionGuid) {
    return sessionRepository.findByGuid(sessionGuid);
  }

  @Override
  public void saveSession(DnDSession session) {
    sessionRepository.save(session);
  }

  @Override
  public void deleteSession(String sessionGuid) {
    sessionRepository.deleteByGuid(sessionGuid);
  }

  @Override
  public ExpandedDnDCharacter getCharacter(String sessionGuid, String userGuid) {

    // TODO: Add Error Handling
    SessionCharacter character = sessionCharacterRepository.findBySessionGuidAndUserGuid(sessionGuid, userGuid);
    SessionCharacterAttributes attributes = sessionCharacterAttributesRepository.findByUserSessionCharacterFk(character.getId());

    return new ExpandedDnDCharacter(character, attributes);
  }

  @Override
  public void saveCharacter(String sessionGuid, ExpandedDnDCharacter character) {

    // TODO: Add Error Handling
    sessionCharacterRepository.save(character.character());
    sessionCharacterAttributesRepository.save(character.attributes());
  }

  @Override
  public void deleteCharacter(String sessionGuid, String userGuid) {

  }
}
