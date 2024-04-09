package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.entities.DnDUser;

import java.util.List;

public interface UserService {
  void saveUser(String username, String password);

  void saveManyUsers(List<DnDUser> users);

  DnDUser getById(Long id);

  List<DnDUser> getAllUsersByIds(List<Long> ids);

  DnDUser getCurrentUser();
}
