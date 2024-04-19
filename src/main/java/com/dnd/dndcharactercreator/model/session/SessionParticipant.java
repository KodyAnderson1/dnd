package com.dnd.dndcharactercreator.model.session;

import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.SessionCharacter;
import com.dnd.dndcharactercreator.model.entities.SessionCharacterAttributes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionParticipant {
  private DnDUser user;
  private String stompId;
  private SessionCharacter character;
  private SessionCharacterAttributes characterAttributes;
}
