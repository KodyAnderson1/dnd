package com.dnd.dndcharactercreator.model.session;

import java.time.LocalDateTime;

public record LogItem(LocalDateTime occurred, SessionAction action) {
}
