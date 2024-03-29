package com.dnd.dndcharactercreator.controller;

import com.dnd.dndcharactercreator.model.DnDSessionDetails;
import com.dnd.dndcharactercreator.model.chat.ChatMessage;
import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.service.CharacterService;
import com.dnd.dndcharactercreator.service.DnDSessionService;
import com.dnd.dndcharactercreator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CharacterController {

  private final DnDSessionService sessionService;
  private final CharacterService characterService;
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
    DnDSessionDetails session = sessionService.joinSession(id);

    List<Long> userIds = session.getParticipants().stream()
            .map(item -> (long)(item)) // Convert Integer to Long. This is a workaround because DnDSessionDetails uses Integer for participants instead of Long and because I'm lazy I didn't want to refactor the whole thing
            .distinct() // Remove duplicates (if any)
            .toList();

    List<DnDUser> participants = userService.getAllUsersByIds(userIds);

    model.addAttribute("dndSession", session);
    model.addAttribute("participants", participants); // This is a list of DnDUser objects. could refactor session to use DnDUser objects instead of just IDs
    model.addAttribute("username", userService.getCurrentUser().getUsername()); // This is the current user's username (the one who joined the session)
    return "session-details";
  }

  @GetMapping("/characters/new")
  public String showCreateForm(Model model) {
    model.addAttribute("character", new DnDCharacter());
    model.addAttribute("classes", characterService.getAllClasses());
    model.addAttribute("races", characterService.getAllRaces());
    return "create-character";
  }

  @GetMapping("/characters/{id}")
  public String showCharacterDetails(@PathVariable("id") Long id, Model model) {
    DnDCharacter dnDCharacter = characterService.getCharacterById(id);

    model.addAttribute("character", dnDCharacter);
    return "character-details";
  }

  @GetMapping("/characters/{id}/edit")
  public String showUpdateForm(@PathVariable("id") Long id, Model model) {
    // Get the character by ID to Edit. Not super secure since the repository method doesn't check if the character belongs to the current user
    DnDCharacter dnDCharacter = characterService.getCharacterById(id);

    model.addAttribute("character", dnDCharacter);
    model.addAttribute("classes", characterService.getAllClasses());
    model.addAttribute("races", characterService.getAllRaces());
    return "update-character";
  }

  // WebSocket communication for each session chat. Look at fragments/chatMessages.html for the client side code
  @MessageMapping("/chat/{sessionId}/sendMessage")
  @SendTo("/topic/messages/{sessionId}")
  public ChatMessage sendMessage(@DestinationVariable String sessionId, ChatMessage chatMessage) {
    return sessionService.addChatMessage(sessionId, chatMessage);
  }

  @PostMapping("/signup")
  public String signUp(@ModelAttribute("user") DnDUser user) {
    userService.saveUser(user.getUsername(), user.getPassword());
    return "redirect:/";
  }

  @PostMapping("/characters")
  public String saveCharacter(@ModelAttribute("character") DnDCharacter dnDCharacter) {
    characterService.saveCharacter(dnDCharacter);
    return "redirect:/characters/" + dnDCharacter.getId();
  }

  @PostMapping("/characters/{id}")
  public String updateCharacter(@PathVariable("id") Long id, @ModelAttribute("character") DnDCharacter updatedDnDCharacter) {
    DnDCharacter dnDCharacter = characterService.getCharacterById(id);
    dnDCharacter.setName(updatedDnDCharacter.getName());
    dnDCharacter.setCharacterClass(updatedDnDCharacter.getCharacterClass());
    dnDCharacter.setLevel(updatedDnDCharacter.getLevel());

    characterService.updateCharacter(dnDCharacter);
    return "redirect:/characters/" + id;
  }

}
