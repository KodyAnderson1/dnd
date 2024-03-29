package com.dnd.dndcharactercreator.model.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessage {
  private String author; // The user who sent the message. Currently just the username but could be expanded to include more info (e.g. user ID, or the entire DnDUser object)
  private String content; // The message itself
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  private String time; // The time the message was sent

  public ChatMessage(String sender, String content) {
    this.author = sender;
    this.content = content;
    this.time = LocalDateTime.now().toString();
  }

  public ChatMessage(String sender, String content, String timestamp) {
    this.author = sender;
    this.content = content;
    this.time = timestamp;
  }
}
