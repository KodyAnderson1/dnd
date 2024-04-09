package com.dnd.dndcharactercreator.config;

import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.repository.DnDUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final DnDUserRepository userRepository; // final + @RequiredArgsConstructor is the same as @Autowired

  @Override
  public DnDUser loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("=== ### === @@@ === Load User By Username: " + username);
    DnDUser user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return user;
  }
}

