package de.drewing.comic.layout.read;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum PanelShot {
  EXTREME_LONG_SHOT("extreme long shot", "long shot"),
  LONG_SHOT("long shot", "long shot"),
  FULL_SHOT("full shot", "full shot"),
  AMERICAN_SHOT("american shot", "american shot"),
  MEDIUM_SHOT("medium shot", "medium shot"),
  CLOSE_UP("close-up", "close-up"),
  ITALIAN_SHOT("italian shot", "italian shot"),

  AERIAL_SHOT("italian shot", "italian shot"),
  BIRDS_EYE_SHOT("bird's-eye shot", "bird's-eye shot"),
  LOW_ANGLE_SHOT("low angle shot", "low angle shot"),

  POINT_OF_VIEW_SHOT("point of view shot", "point of view shot"),
  OVER_THE_SHOULDER_SHOT("over the shoulder shot", "over the shoulder shot"),
  TWO_SHOT("two shot", "two shot");

  private Pattern pattern;
  private String name;

  PanelShot(final String pattern, final String name){
    final String ignoreCase = "(?i)";
    this.pattern = Pattern.compile(ignoreCase + pattern);
    this.name = name;
  }

  public boolean findInText(final String text) {
    final Matcher m = pattern.matcher(text);
    return m.find();
  }

  public String getName() {
    return name;
  }
}
