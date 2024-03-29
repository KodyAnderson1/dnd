package com.dnd.dndcharactercreator.config;

import com.dnd.dndcharactercreator.model.DnDSessionDetails;
import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDClass;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.Race;
import com.dnd.dndcharactercreator.service.CharacterService;
import com.dnd.dndcharactercreator.service.DnDSessionService;
import com.dnd.dndcharactercreator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final DnDSessionService sessionService;
  private final CharacterService characterService;
  private final UserService userService;

  private static List<Race> getRaces() {
    return new ArrayList<>(
            List.of(
                    new Race(1L, "Human"),
                    new Race(2L, "Elf"),
                    new Race(3L, "Dwarf"),
                    new Race(4L, "Halfling"),
                    new Race(5L, "Gnome"),
                    new Race(6L, "Half-Orc"),
                    new Race(7L, "Half-Elf")
            )
    );
  }

  private static List<DnDClass> getClasses() {
    return new ArrayList<>(List.of(
            new DnDClass(1L, "Barbarian"),
            new DnDClass(2L, "Bard"),
            new DnDClass(3L, "Cleric"),
            new DnDClass(4L, "Druid"),
            new DnDClass(5L, "Fighter"),
            new DnDClass(6L, "Monk"),
            new DnDClass(7L, "Paladin"),
            new DnDClass(8L, "Ranger"),
            new DnDClass(9L, "Rogue"),
            new DnDClass(10L, "Sorcerer"),
            new DnDClass(11L, "Wizard")));
  }

  private static List<DnDCharacter> getCharacters() {
    return new ArrayList<>(List.of(
            new DnDCharacter(1L, 1, "Gandalf", "Wizard", 20, "Human"),
            new DnDCharacter(2L, 1, "Aragorn", "Fighter", 20, "Human"),
            new DnDCharacter(3L, 1, "Legolas", "Ranger", 20, "Elf"),
            new DnDCharacter(4L, 1, "Gimli", "Fighter", 20, "Dwarf"),
            new DnDCharacter(5L, 1, "Frodo", "Rogue", 20, "Halfling"),
            new DnDCharacter(6L, 1, "Sam", "Cleric", 20, "Halfling"),
            new DnDCharacter(7L, 1, "Merry", "Rogue", 20, "Halfling"),
            new DnDCharacter(8L, 1, "Pippin", "Bard", 20, "Halfling")
    ));
  }

  private static List<DnDUser> getUsers() {
    return new ArrayList<>(List.of(
            new DnDUser(1L, "KodyAnderson1", "re"),
            new DnDUser(2L, "KodyAnderson2", "re"),
            new DnDUser(3L, "KodyAnderson3", "re"),
            new DnDUser(4L, "KodyAnderson4", "re"),
            new DnDUser(5L, "KodyAnderson5", "re"),
            new DnDUser(6L, "KodyAnderson6", "re"),
            new DnDUser(7L, "KodyAnderson7", "re"),
            new DnDUser(8L, "KodyAnderson8", "re"),
            new DnDUser(9L, "KodyAnderson9", "re"),
            new DnDUser(10L, "KodyAnderson10", "re"),
            new DnDUser(11L, "KodyAnderson11", "re"),
            new DnDUser(12L, "KodyAnderson12", "re")
    ));
  }

  private static List<DnDSessionDetails> getSessions() {
    return new ArrayList<>(List.of(
            new DnDSessionDetails("75761824-995b-4f31-8428-f9f5e5751ae9", "The Dragon's Layer", "Description 1", 1),
            new DnDSessionDetails(UUID.randomUUID().toString(), "The Bard's Regret", "Description 2", 2),
            new DnDSessionDetails(UUID.randomUUID().toString(), "Super Kewl Session", "Description 3", 3),
            new DnDSessionDetails(UUID.randomUUID().toString(), "Uuuuuuuuuuuuuh", "Description 4"),
            new DnDSessionDetails(UUID.randomUUID().toString(), "Session 5", "Description 5")
    ));
  }

  @Override
  public void run(String... args) {
    characterService.saveAllRaces(getRaces());
    characterService.saveAllClasses(getClasses());
    characterService.saveAllCharacters(getCharacters());
    userService.saveManyUsers(getUsers());
    sessionService.createManySessions(getSessions());

  }
}