package com.dnd.dndcharactercreator.model;

import com.dnd.dndcharactercreator.model.entities.SessionCharacter;
import com.dnd.dndcharactercreator.model.entities.SessionCharacterAttributes;

public record ExpandedDnDCharacter(SessionCharacter character, SessionCharacterAttributes attributes) { }
