package com.dnd.dndcharactercreator.config;

import com.dnd.dndcharactercreator.model.ActiveSession;
import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
import com.dnd.dndcharactercreator.model.entities.DnDClass;
import com.dnd.dndcharactercreator.model.entities.DnDSession;
import com.dnd.dndcharactercreator.model.entities.DnDUser;
import com.dnd.dndcharactercreator.model.entities.Race;
import com.dnd.dndcharactercreator.service.CharacterService;
import com.dnd.dndcharactercreator.service.SessionService;
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

  private final SessionService sessionService;
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
    DnDCharacter character1 = DnDCharacter.builder()
            .id(1L).userGuid("38dda5d2-ffbb-41b4-b53f-0046d64f67b3")
            .name("Gandalf").characterClass("Wizard").race("Human").icon("")
            .initiative(10).strength(10).dexterity(10).constitution(10).intelligence(10).wisdom(10).charisma(10)
            .build();

    DnDCharacter character2 = DnDCharacter.builder()
            .id(2L).userGuid("38dda5d2-ffbb-41b4-b53f-0046d64f67b3")
            .name("Aragorn").characterClass("Fighter").race("Human").icon("")
            .initiative(10).strength(10).dexterity(10).constitution(10).intelligence(10).wisdom(10).charisma(10)
            .build();

    return new ArrayList<>(List.of(character1, character2));
  }

  private static List<DnDUser> getUsers() {
    return new ArrayList<>(List.of(
            new DnDUser(1L, "KodyAnderson1", "re", "38dda5d2-ffbb-41b4-b53f-0046d64f67b3"),
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

  private static List<ActiveSession> getSessions() {
    return new ArrayList<>(List.of(
            new ActiveSession("75761824-995b-4f31-8428-f9f5e5751ae9", "The Dragon's Layer", "Description 1", "38dda5d2-ffbb-41b4-b53f-0046d64f67b3"),
            new ActiveSession(UUID.randomUUID().toString(), "The Bard's Regret", "Description 2", "38dda5d2-ffbb-41b4-b53f-0046d64f67b3"),
            new ActiveSession(UUID.randomUUID().toString(), "Super Kewl Session", "Description 3", "38dda5d2-ffbb-41b4-b53f-0046d64f67b3")
    ));
  }

  @Override
  public void run(String... args) {
    characterService.saveAllRaces(getRaces());
    characterService.saveAllClasses(getClasses());
    characterService.saveAllCharacters(getCharacters());
    userService.saveManyUsers(getUsers());

    getSessions().forEach(session -> {

      DnDSession local = DnDSession.builder()
              .guid(session.getSessionId())
              .name(session.getName())
              .description(session.getDescription())
              .dmGuid(session.getDungeonMasterGuid())
              .build();

      sessionService.saveSession(local);
    });
  }
}