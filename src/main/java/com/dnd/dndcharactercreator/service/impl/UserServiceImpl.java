package com.dnd.dndcharactercreator.service.impl;

import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.repository.DnDUserRepository;
import com.dnd.dndcharactercreator.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final DnDUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void saveUser(String username, String password) {
    userRepository.save(new DnDUser(username, passwordEncoder.encode(password)));
  }

  @Override
  public void saveUser(DnDUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public DnDUser getById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public List<DnDUser> getAllUsersByIds(List<Long> ids) {
    return userRepository.findAllById(ids);
  }

  @Override
  public DnDUser getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (DnDUser) authentication.getPrincipal();
  }

}
