package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.ExpandedDnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDSession;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.DnDUserSession;
import com.dnd.dndcharactercreator.model.entities.SessionCharacter;
import com.dnd.dndcharactercreator.model.entities.SessionCharacterAttributes;
import com.dnd.dndcharactercreator.repository.SessionCharacterAttributesRepository;
import com.dnd.dndcharactercreator.repository.SessionCharacterRepository;
import com.dnd.dndcharactercreator.repository.SessionRepository;
import com.dnd.dndcharactercreator.repository.UserSessionsRepository;
import com.dnd.dndcharactercreator.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

  private final SessionRepository sessionRepository;
  private final UserSessionsRepository userSessionsRepository;
  private final SessionCharacterRepository sessionCharacterRepository;
  private final SessionCharacterAttributesRepository sessionCharacterAttributesRepository;

  @Override
  public List<DnDSession> getAllSessions() {
    return sessionRepository.findAll();
  }

  @Override
  public int getTotalMembers(String sessionGuid) {
    return userSessionsRepository.countBySessionGuid(sessionGuid);
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
  public void createSession(DnDSession session, Long characterId, Long attributesId) {
    DnDSession createdSession = sessionRepository.save(session);

    DnDUserSession userSession = DnDUserSession.builder()
            .sessionGuid(createdSession.getGuid())
            .characterId(characterId)
            .attributesId(attributesId)
            .userGuid(getCurrentUser().getGuid())
            .isDungeonMaster(true) // If the user is creating the session, they are the DM
            .build();

    userSessionsRepository.save(userSession);
  }

  @Override
  public void addUserToSession(String sessionGuid, ExpandedDnDCharacter character) {

    var savedCharacter = sessionCharacterRepository.save(character.character());
    var savedAttributes = sessionCharacterAttributesRepository.save(character.attributes());

    DnDUserSession userSession = DnDUserSession.builder()
            .sessionGuid(sessionGuid)
            .characterId(savedCharacter.getId())
            .attributesId(savedAttributes.getId())
            .userGuid(getCurrentUser().getGuid())
            .isDungeonMaster(false)
            .build();

    userSessionsRepository.save(userSession);
  }

  @Override
  public Optional<ExpandedDnDCharacter> getCharacter(String sessionGuid, String userGuid) {

    SessionCharacter character = sessionCharacterRepository.findBySessionGuidAndUserGuid(sessionGuid, userGuid);

    if (character == null || character.getId() == null) {
      return Optional.empty();
    }

    SessionCharacterAttributes attributes = sessionCharacterAttributesRepository.findByUserSessionCharacterFk(character.getId());

    return Optional.of(new ExpandedDnDCharacter(character, attributes));
  }

  @Override
  public List<DnDUserSession> getSessionsByUserGuid(String userGuid) {
    return userSessionsRepository.findAllByUserGuid(userGuid);
  }

  @Override
  public List<DnDUserSession> getUserSessionsBySessionGuid(String sessionGuid) {
    return userSessionsRepository.findAllBySessionGuid(sessionGuid);
  }

  @Override
  public DnDUserSession getUserSession(String userGuid, String sessionGuid) {
    return userSessionsRepository.findByUserGuidAndSessionGuid(userGuid, sessionGuid);
  }

  public DnDUser getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (DnDUser) authentication.getPrincipal();
  }
}
