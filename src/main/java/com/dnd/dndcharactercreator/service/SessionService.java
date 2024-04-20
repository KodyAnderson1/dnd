package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.ExpandedDnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDSession;
import com.dnd.dndcharactercreator.model.entities.DnDUserSession;

import java.util.List;
import java.util.Optional;

public interface SessionService {

  List<DnDSession> getAllSessions();

  int getTotalMembers(String sessionGuid);

  String getDMGuid(String sessionGuid);

  boolean verifyInviteCode(String sessionGuid, String code);

  DnDSession getSession(String sessionGuid);

  void saveSession(DnDSession session);

  void createSession(DnDSession session, Long characterId, Long attributesId);

  void addUserToSession(String sessionGuid, ExpandedDnDCharacter character);

  Optional<ExpandedDnDCharacter> getCharacter(String sessionGuid, String userGuid);

  List<DnDUserSession> getSessionsByUserGuid(String userGuid);

  List<DnDUserSession> getUserSessionsBySessionGuid(String sessionGuid);

  DnDUserSession getUserSession(String userGuid, String sessionGuid);

}
