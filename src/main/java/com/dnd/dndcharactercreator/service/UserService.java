package com.dnd.dndcharactercreator.service;

import com.dnd.dndcharactercreator.model.entities.DnDUser;

import java.util.List;

public interface UserService {
  void saveUser(String username, String password);

  void saveManyUsers(List<DnDUser> users);

  boolean userExists(String username);

  boolean validateUser(String username, String password);

  List<DnDUser> getAllUsers();

  List<DnDUser> getAllUsersByIds(List<Long> ids);

  DnDUser getCurrentUser();
}
