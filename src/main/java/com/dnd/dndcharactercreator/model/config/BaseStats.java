package com.dnd.dndcharactercreator.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseStats {
  private double mana;
  private double stamina;
  private double health;
}
