package com.dnd.dndcharactercreator.controller;

import com.dnd.dndcharactercreator.exception.DnDException;
import com.dnd.dndcharactercreator.model.ActiveSession;
import com.dnd.dndcharactercreator.model.Error;
import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.form.CharacterForm;
import com.dnd.dndcharactercreator.service.CharacterService;
import com.dnd.dndcharactercreator.service.ActiveSessionManager;
import com.dnd.dndcharactercreator.service.PoorManCache;
import com.dnd.dndcharactercreator.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CharacterController {

  private final ActiveSessionManager sessionService; // final + @RequiredArgsConstructor is the same as @Autowired
  private final CharacterService characterService;
  private final PoorManCache cacheService;
  private final UserService userService;

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("characters", characterService.getAllCharactersByUser());
    return "home";
  }

  @GetMapping("/login")
  public String logIn(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("loginError", true);
    }

    model.addAttribute("loginForm", new DnDUser());
    return "log-in";
  }

  @GetMapping("/signup")
  public String signUp(Model model) {
    model.addAttribute("loginForm", new DnDUser());
    return "sign-up";
  }

  @GetMapping("/sessions")
  public String session(Model model) {
    model.addAttribute("sessions", sessionService.getAllSessions());
    return "sessions";
  }

  @GetMapping("/sessions/{id}")
  public String session(@PathVariable String id, Model model) {
    DnDUser currUser = userService.getCurrentUser();

    // TODO: Currently no check to make sure user is authorized to actually join
    ActiveSession session = sessionService.getSession(id);
    model.addAttribute("dndSession", session);
    model.addAttribute("user", currUser);

    try {

      model.addAttribute("participants", sessionService.getActiveUsers(id));

    } catch (DnDException e) {
      log.warn("Exception occurred getting participants. {}", e.getMessage());

      model.addAttribute("participants", Collections.emptyList());
      model.addAttribute("error", new Error(e.getTitleMessage(), e.getDisplayMessage(), e.getCode()));

    }

    return "session-details";
  }

  @GetMapping("/characters/new")
  public String showCreateForm(Model model) {
    model.addAttribute("character", new CharacterForm());
    model.addAttribute("classes", cacheService.getAllClasses());
    model.addAttribute("races", cacheService.getAllRaces());
    return "create-character";
  }

  @GetMapping("/characters/{id}")
  public String showCharacterDetails(@PathVariable("id") Long id, Model model) {
    DnDCharacter character = characterService.getCharacterById(id);
    CharacterForm characterForm = new CharacterForm(character);

    model.addAttribute("character", characterForm);
    return "character-details";
  }

  @GetMapping("/characters/{id}/edit")
  public String showUpdateForm(@PathVariable("id") Long id, Model model) {

    DnDCharacter dnDCharacter = characterService.getCharacterById(id);
    CharacterForm characterForm = new CharacterForm(dnDCharacter);

    model.addAttribute("character", characterForm);
    model.addAttribute("classes", characterService.getAllClasses());
    model.addAttribute("races", characterService.getAllRaces());
    return "update-character";
  }

  @PostMapping("/signup")
  public String signUp(@ModelAttribute("user") DnDUser user) {
    userService.saveUser(user.getUsername(), user.getPassword());
    return "redirect:/";
  }

  @PostMapping("/characters")
  public String saveCharacter(@ModelAttribute("character") CharacterForm character) {
    DnDCharacter savedCharacter = characterService.saveCharacter(new DnDCharacter(character));
    return "redirect:/characters/" + savedCharacter.getId();
  }

  @PostMapping("/characters/{id}")
  public String updateCharacter(@PathVariable("id") Long id, @ModelAttribute("character") CharacterForm updatedDnDCharacter) {
    updatedDnDCharacter.setId(id);
    updatedDnDCharacter.setUserGuid(userService.getCurrentUser().getGuid());

    characterService.updateCharacter(new DnDCharacter(updatedDnDCharacter));
    return "redirect:/characters/" + id;
  }


}
