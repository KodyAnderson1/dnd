package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.ExpandedDnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDSession;

import java.util.List;

public interface SessionService {

  List<DnDSession> getAllSessions();

  int getTotalMembers(String sessionGuid);

  String getDMGuid(String sessionGuid);

  boolean verifyInviteCode(String sessionGuid, String code);

  DnDSession getSession(String sessionGuid);

  void saveSession(DnDSession session);

  void deleteSession(String sessionGuid);

  ExpandedDnDCharacter getCharacter(String sessionGuid, String userGuid);

  void saveCharacter(String sessionGuid, ExpandedDnDCharacter character);

  void deleteCharacter(String sessionGuid, String userGuid);

}
