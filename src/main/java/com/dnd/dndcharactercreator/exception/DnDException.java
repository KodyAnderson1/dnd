package com.dnd.dndcharactercreator.exception;

import com.dnd.dndcharactercreator.utils.ExceptionCodes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DnDException extends RuntimeException {

  private final String code;
  private final String displayMessage;
  private final String titleMessage = "Uh Oh!";

  @Builder
  public DnDException(String message, String code, String displayMessage) {
    super(message);

    this.code = code;
    this.displayMessage = displayMessage;
  }

  public DnDException(String message, ExceptionCodes exceptionCodes) {
    super(message);

    this.code = exceptionCodes.name();
    this.displayMessage = exceptionCodes.getMessage();
  }

}
