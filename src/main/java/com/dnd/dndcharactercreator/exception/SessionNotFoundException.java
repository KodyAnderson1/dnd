package com.dnd.dndcharactercreator.exception;

import com.dnd.dndcharactercreator.utils.ExceptionCodes;
import lombok.Getter;

@Getter
public class SessionNotFoundException extends DnDException {


  private final String titleMessage = "No Session Found";

  public SessionNotFoundException(String message, ExceptionCodes exceptionCodes) {
    super(message, exceptionCodes.name(), exceptionCodes.getMessage());
  }

  public SessionNotFoundException(String message, String code, String displayMessage) {
    super(message, code, displayMessage);
  }

}
