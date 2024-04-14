package com.dnd.dndcharactercreator.exception;

import com.dnd.dndcharactercreator.utils.ExceptionCodes;
import lombok.Getter;

@Getter
public class NotAuthorizedException extends DnDException {

  private final String titleMessage = "Unauthorized";

  public NotAuthorizedException(String message, ExceptionCodes exceptionCodes) {
    super(message, exceptionCodes.name(), exceptionCodes.getMessage());
  }

  public NotAuthorizedException(String message, String code, String displayMessage) {
    super(message, code, displayMessage);
  }
}
