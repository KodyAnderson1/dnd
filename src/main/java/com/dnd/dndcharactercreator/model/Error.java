package com.dnd.dndcharactercreator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {
  private String title;
  private String subTitle;
  private String code;
}
