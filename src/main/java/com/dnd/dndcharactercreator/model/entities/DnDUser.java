package com.dnd.dndcharactercreator.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users")
public class DnDUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String username;
  private String initials;
  private String password;

  public DnDUser(String username, String password) {
    this.username = username;
    this.password = password;
    this.initials = getUserInitials(username);
  }

  public DnDUser(Long userId, String username, String password) {
    this.id = userId;
    this.username = username;
    this.password = password;
    this.initials = getUserInitials(username);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  private static String getUserInitials(String username) {
    if (username == null || username.isEmpty()) {
      return "";
    }

    if (username.length() == 1) {
      return username.toUpperCase() + " ";
    }

    // return first character and last character of string
    return String.valueOf(username.toUpperCase().charAt(0)) + username.toUpperCase().charAt(username.length() - 1);
  }
}
