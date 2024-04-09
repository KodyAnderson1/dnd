package com.dnd.dndcharactercreator.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtility {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static String printAsJson(Object object) {

    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    } catch (Exception e) {
      return "Failed to convert to JSON";
    }

  }
}
