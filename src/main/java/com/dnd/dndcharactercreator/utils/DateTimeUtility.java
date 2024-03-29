package com.dnd.dndcharactercreator.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtility {
  private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
  private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");

  public static String formatDateString(String input) {
    // Parse the input string
    LocalDateTime dateTime = LocalDateTime.parse(input, inputFormatter);
    ZonedDateTime zonedNow = ZonedDateTime.now(ZoneId.of("UTC"));

    // If the date is the same, format just the time
    if (dateTime.toLocalDate().equals(zonedNow.toLocalDate())) {
      return dateTime.format(timeFormatter);
    } else { // Otherwise, format the full date
      return dateTime.format(dateFormatter);
    }
  }
}
