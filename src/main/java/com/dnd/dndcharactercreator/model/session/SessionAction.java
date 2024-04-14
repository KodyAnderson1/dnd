package com.dnd.dndcharactercreator.model.session;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SessionAction {
  private String triggeredByGuid;
  private String affectedUserGuid;
  private String action;
  private Map<String, String> details;
}
