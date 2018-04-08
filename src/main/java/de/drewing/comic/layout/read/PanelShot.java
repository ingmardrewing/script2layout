package de.drewing.comic.layout.read;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum PanelShot {
  EXTREME_LONG_SHOT("extreme long shot", "extremelong.png"),
  LONG_SHOT("long shot", "long.png"),
  FULL_SHOT("full shot", "full.png"),
  AMERICAN_SHOT("american shot", "american.png"),
  MEDIUM_SHOT("medium shot", "medium.png"),
  CLOSE_UP("close-up", "closeup.png"),
  ITALIAN_SHOT("italian shot", "italian.png"),

  AERIAL_SHOT("italian shot", "aerial.png"),
  BIRDS_EYE_SHOT("bird's-eye shot", "birdseye.png"),
  LOW_ANGLE_SHOT("low angle shot", "lowangle.png"),

  POINT_OF_VIEW_SHOT("point of view shot", "pointofview.png"),
  OVER_THE_SHOULDER_SHOT("over the shoulder shot", "overtheshoulder.png"),
  TWO_SHOT("two shot", "two.png");

  private Pattern pattern;
  private String fileName;

  PanelShot(final String pattern, final String fileName){
    final String ignoreCase = "(?i)";
    this.pattern = Pattern.compile(ignoreCase + pattern);
    this.fileName = fileName;
  }

  public boolean findInText(final String text) {
    final Matcher m = pattern.matcher(text);
    return m.find();
  }

  public String getFileName() {
    return fileName;
  }
}
