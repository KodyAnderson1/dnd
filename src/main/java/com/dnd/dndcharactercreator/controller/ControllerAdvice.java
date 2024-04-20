package com.dnd.dndcharactercreator.controller;

import com.dnd.dndcharactercreator.exception.DnDException;
import com.dnd.dndcharactercreator.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.function.Predicate;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  private static final Predicate<String> NULL_BLANK = s -> s == null || s.isBlank();

  @ExceptionHandler(DnDException.class)
  public String handleDnDException(DnDException e, Model model) {
    String titleMessage = NULL_BLANK.test(e.getTitleMessage()) ? "Uh oh!" : e.getTitleMessage();
    String code = NULL_BLANK.test(e.getCode()) ? "AE003" : e.getCode();
    String displayMessage = NULL_BLANK.test(e.getDisplayMessage()) ? "Something bad happened and we couldn't recover. Try again later" : e.getDisplayMessage();

    model.addAttribute("error", new Error(titleMessage, code, displayMessage));

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
