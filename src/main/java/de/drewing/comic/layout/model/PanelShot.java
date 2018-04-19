package de.drewing.comic.layout.model;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public enum PanelShot {
  EXTREME_LONG_SHOT("extremelong", "extreme long shot"),
  LONG_SHOT("long", "long shot"),
  FULL_SHOT("full", "full shot"),
  AMERICAN_SHOT("american", "american shot"),
  MEDIUM_SHOT("medium", "medium shot"),
  CLOSE_UP_FEMALE("closeupfemale", "close-up female", "closeup female"),
  CLOSE_UP("closeup", "close-up", "closeup"),
  ITALIAN_SHOT("italian", "italian shot"),

  AERIAL_SHOT("aerial", "aerial shot", "crane shot"),
  BIRDS_EYE_SHOT("birdseye", "bird.?s-?eye", "bird", "god"),
  LOW_ANGLE_SHOT("lowangle", "low angle shot", "low angle"),

  POINT_OF_VIEW_SHOT("pointofview", "point of view shot" , "pov"),
  OVER_THE_SHOULDER_SHOT_FLIPPED("overtheshoulderflip", "over the shoulder shot flipped", "over the shoulder flipped", "ots flipped"),
  OVER_THE_SHOULDER_SHOT("overtheshoulder", "over the shoulder shot", "over the shoulder", "ots"),
  TWO_SHOT("two", "two shot"),

  DEFAULT("default");

  private List<Pattern> patterns;
  private String dirName;

  PanelShot(final String dirName, final String... patternsParam){
    patterns = new ArrayList<Pattern>();
    final String ignoreCase = "(?i)";
    for (final String p : patternsParam){
      patterns.add(Pattern.compile(ignoreCase + p));
    }
    this.dirName = dirName;
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

  public String getDir() {
    return  dirName;
  }
}
