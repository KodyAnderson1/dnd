package com.dnd.dndcharactercreator.config;

import com.dnd.dndcharactercreator.model.config.BaseStats;
import com.dnd.dndcharactercreator.model.config.LevelIncrease;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("stats")
public class StatsConfig {
  private BaseStats base;
  private LevelIncrease perLevelIncrease;
}
