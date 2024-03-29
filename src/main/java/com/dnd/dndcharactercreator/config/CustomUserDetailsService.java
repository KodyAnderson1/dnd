package com.dnd.dndcharactercreator.config;

import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.repository.DnDUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private DnDUserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public DnDUser loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("=== ### === @@@ === Load User By Username: " + username);
    DnDUser user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return user;
  }
}

