package com.dnd.dndcharactercreator.controller;

import com.dnd.dndcharactercreator.model.chat.ChatMessage;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.service.DnDSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

  private final DnDSessionService sessionService; // final + @RequiredArgsConstructor is the same as @Autowired
  private final SimpMessagingTemplate messagingTemplate;

  // WebSocket communication for each session chat.
  @MessageMapping("/chat/{sessionId}/sendMessage")
  @SendTo("/topic/messages/{sessionId}")
  public ChatMessage sendPublicMessage(@DestinationVariable String sessionId, ChatMessage chatMessage) {
    log.info("Public Message Received");
    return sessionService.addChatMessage(sessionId, chatMessage);
  }

  @MessageMapping("/chat/{sessionId}/sendDirectMessage")
  public void sendPrivateMessage(@DestinationVariable String sessionId, ChatMessage chatMessage) {
    log.info("Private Message Received");
    sessionService.addChatMessage(sessionId, chatMessage);

    String destination = String.format("/topic/messages/%s/%s", sessionId, chatMessage.getRecipient());
    messagingTemplate.convertAndSend(destination, chatMessage);
  }

  @GetMapping("/chat/{sessionId}/activeUsers")
  public List<DnDUser> getActiveUsers(@PathVariable String sessionId) {
    return sessionService.getActiveUsers(sessionId);
  }

}
