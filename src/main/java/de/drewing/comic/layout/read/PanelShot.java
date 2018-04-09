package de.drewing.comic.layout.read;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public enum PanelShot {
  EXTREME_LONG_SHOT("extremelong.png", "extreme long shot"),
  LONG_SHOT("long.png", "long shot"),
  FULL_SHOT("full.png", "full shot"),
  AMERICAN_SHOT("american.png", "american shot"),
  MEDIUM_SHOT("medium.png", "medium shot"),
  CLOSE_UP("closeup.png", "close-up", "closeup"),
  ITALIAN_SHOT("italian.png", "italian shot"),

  AERIAL_SHOT("aerial.png", "aerial shot", "crane shot"),
  BIRDS_EYE_SHOT("birdseye.png", "bird's-eye shot", "birds-eye shot", "god"),
  LOW_ANGLE_SHOT("lowangle.png", "low angle shot"),

  POINT_OF_VIEW_SHOT("pointofview.png", "point of view shot" , "pov"),
  OVER_THE_SHOULDER_SHOT_FLIPPED("overtheshoulderflip.png", "over the shoulder shot flipped", "over the shoulder flipped", "ots flipped"),
  OVER_THE_SHOULDER_SHOT("overtheshoulder.png", "over the shoulder shot", "over the shoulder", "ots"),
  TWO_SHOT("two.png", "two shot"),

  DEFAULT("default.png");

  private List<Pattern> patterns;
  private String fileName;

  PanelShot(final String fileName, final String... patternsParam){
    patterns = new ArrayList<Pattern>();
    final String ignoreCase = "(?i)";
    for (final String p : patternsParam){
      patterns.add(Pattern.compile(ignoreCase + p));
    }
    this.fileName = fileName;
  }

  public boolean findInText(final String text) {
    for (final Pattern p : patterns) {
      final Matcher m = p.matcher(text);
      if(m.find()){
        return true;
      }
    }
    return false;
  }

  public String getPathAndName() {
    return "images/" + fileName;
  }
}
