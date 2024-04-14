//package com.dnd.dndcharactercreator.service.impl;
//
//import com.dnd.dndcharactercreator.model.ExpandedDnDCharacter;
//import com.dnd.dndcharactercreator.model.entities.DnDCharacter;
//import com.dnd.dndcharactercreator.model.entities.SessionCharacterAttributes;
//import com.dnd.dndcharactercreator.model.entities.SessionCharacter;
//import com.dnd.dndcharactercreator.repository.SessionCharacterAttributesRepository;
//import com.dnd.dndcharactercreator.repository.SessionCharacterRepository;
//import com.dnd.dndcharactercreator.service.SessionCharacterService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class SessionCharacterServiceImpl implements SessionCharacterService {
//
//  private final SessionCharacterRepository sessionCharacterRepository;
//  private final SessionCharacterAttributesRepository attributesRepository;
//
//  @Override
//  public Map<String, List<ExpandedDnDCharacter>> getAllBySessionGuids(List<String> sessionGuids) {
//    List<SessionCharacter> sessionCharacters = sessionCharacterRepository.findAllBySessionGuidIn(sessionGuids);
//    if (sessionCharacters.isEmpty()) {
//      log.warn("No Characters found for the provided session GUIDs: {}", sessionGuids);
//      return Collections.emptyMap();
//    }
//
//    List<Long> characterIds = sessionCharacters.stream().map(SessionCharacter::getId).collect(Collectors.toList());
//    List<SessionCharacterAttributes> attributesList = attributesRepository.findAllByUserSessionCharacterFkIn(characterIds);
//
//    if (attributesList == null || attributesList.isEmpty()) {
//      log.warn("Characters exist for provided sessionGuids, but no attributes do. GUIDS: {}", sessionGuids);
//      throw new RuntimeException("Characters exist for provided sessionGuids, but no attributes do");
//    }
//
//    Map<Long, SessionCharacterAttributes> attributesMap = attributesList.stream()
//            .collect(Collectors.toMap(SessionCharacterAttributes::getUserSessionCharacterFk, Function.identity()));
//
//    return sessionCharacters.stream()
//            .collect(Collectors.groupingBy(SessionCharacter::getSessionGuid,
//                    Collectors.mapping(character -> new ExpandedDnDCharacter(character, attributesMap.get(character.getId())), Collectors.toList())));
//  }
//
//  @Override
//  public List<ExpandedDnDCharacter> getAllCharactersBySession(String sessionGuid) {
//    List<SessionCharacter> sessionCharacters = sessionCharacterRepository.findAllBySessionGuid(sessionGuid);
//
//    if (sessionCharacters == null || sessionCharacters.isEmpty()) {
//      log.info("No characters found for session GUID: {}", sessionGuid);
//      return Collections.emptyList(); // Early return since no characters exist
//    }
//
//    List<Long> ids = sessionCharacters.stream().map(SessionCharacter::getId).toList();
//    List<SessionCharacterAttributes> allSessionAttributes = attributesRepository.findAllByUserSessionCharacterFkIn(ids);
//    if (allSessionAttributes == null || allSessionAttributes.isEmpty()) {
//      log.warn("No characters found for session GUID: {}", sessionGuid);
//      throw new RuntimeException("Session has characters, but no corresponding attributes"); // Should never happen
//    }
//
//    Map<Long, SessionCharacterAttributes> attributesMap = allSessionAttributes.stream()
//            .collect(Collectors.toMap(SessionCharacterAttributes::getUserSessionCharacterFk, Function.identity()));
//
//    return sessionCharacters.stream()
//            .map(c -> new ExpandedDnDCharacter(c, attributesMap.get(c.getId())))
//            .toList();
//  }
//
//  @Override
//  public Optional<ExpandedDnDCharacter> getCharacterById(long id) {
//    return sessionCharacterRepository.findById(id)
//            .map(character -> {
//              SessionCharacterAttributes attributes = attributesRepository.findByUserSessionCharacterFk(character.getId());
//              return new ExpandedDnDCharacter(character, attributes);
//            });
//  }
//
//}
