package com.dnd.dndcharactercreator.controller;

import com.dnd.dndcharactercreator.exception.DnDException;
import com.dnd.dndcharactercreator.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AdviceController {

  @ExceptionHandler(DnDException.class)
  public String handleDnDException(DnDException e, Model model) {
    model.addAttribute("error", new Error(e.getTitleMessage(), e.getCode(), e.getDisplayMessage()));

    log.error("Unhandled DnDException occurred, Message: {}", e.getMessage(), e);
    return "error";
  }

  @ExceptionHandler(RuntimeException.class)
  public String handleRuntimeException(RuntimeException e, Model model) {
    String titleMessage = "Uh oh!";
    String code = "AE002";
    String displayMessage = "Something bad happened and we couldn't recover. Try again later";

    model.addAttribute("error", new Error(titleMessage, code, displayMessage));

    log.error("Unexpected exception occurred, Message: {}", e.getMessage(), e);
    return "error";
  }

  @ExceptionHandler(Exception.class)
  public String handleException(Exception e, Model model) {
    String titleMessage = "Uh oh!";
    String code = "AE001";
    String displayMessage = "Something bad happened and we couldn't recover. Try again later";

    model.addAttribute("error", new Error(titleMessage, code, displayMessage));

    log.error("Unexpected exception occurred, Message: {}", e.getMessage(), e);
    return "error";
  }
}
