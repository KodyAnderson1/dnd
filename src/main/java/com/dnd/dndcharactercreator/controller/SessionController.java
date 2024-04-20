package com.dnd.dndcharactercreator.controller;

import com.dnd.dndcharactercreator.exception.DnDException;
import com.dnd.dndcharactercreator.model.Error;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.DnDUserSession;
import com.dnd.dndcharactercreator.model.session.ActiveSession;
import com.dnd.dndcharactercreator.service.ActiveSessionManager;
import com.dnd.dndcharactercreator.service.SessionService;
import com.dnd.dndcharactercreator.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SessionController {

  private final ActiveSessionManager activeSessionManager;
  private final SessionService sessionService;
  private final UserService userService; // final + @RequiredArgsConstructor is the same as @Autowired

  @GetMapping("/sessions")
  public String allSession(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("error", error); // TODO: Probably shouldn't be a string directly from the request
    }

    // OLD WAY
    model.addAttribute("sessions", activeSessionManager.getAllSessions());

    // NEW WAY
    List<DnDUserSession> userSessions = sessionService.getSessionsByUserGuid(userService.getCurrentUser().getGuid());
    List<String> sessionIds = userSessions.stream().map(DnDUserSession::getSessionGuid).toList();

    model.addAttribute("allUserSessions", userSessions);
    model.addAttribute("activeSessions", activeSessionManager.getSessionsBySessionGuids(sessionIds));
    return "sessions";
  }

  @GetMapping("/sessions/{id}")
  public String singleSession(@PathVariable String id, Model model) {
    DnDUser currUser = userService.getCurrentUser();

    DnDUserSession userSession = sessionService.getUserSession(currUser.getGuid(), id);

    if (userSession == null) {
      log.info("User {} attempted to access session {} without being a member", currUser.getGuid(), id);
      return "redirect:/sessions?error=User is not a member of this group"; // TODO: Will this param work?
    }

    ActiveSession session = activeSessionManager.getSession(id);
    model.addAttribute("dndSession", session);
    model.addAttribute("user", currUser);

    try {

      model.addAttribute("participants", activeSessionManager.getActiveUsers(id));

    } catch (DnDException e) {
      log.warn("Exception occurred getting participants. {}", e.getMessage());

      model.addAttribute("participants", Collections.emptyList());
      model.addAttribute("error", new Error(e.getTitleMessage(), e.getDisplayMessage(), e.getCode()));
    }

    return "session-details";
  }
}
