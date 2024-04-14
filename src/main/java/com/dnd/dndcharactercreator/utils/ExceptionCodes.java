package com.dnd.dndcharactercreator.utils;

import lombok.Getter;

@Getter
public enum ExceptionCodes {
  AE101("User Not Authorized"),
  AE102("Session Not Found"),
  AE103("No Character Found");

  private final String message;

  ExceptionCodes(String message) {
    this.message = message;
  }
}
