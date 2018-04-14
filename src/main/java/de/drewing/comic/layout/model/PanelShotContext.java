package de.drewing.comic.layout.model;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public enum PanelShotContext {
  /*
  WILDERNESS("wilderness", "wilderness", "country", "countryside"),
  CAR("car", "car", "vehicle", "auto"),
  INDOOR("indoor", "indoor"),
  */
  CITY("city", "city", "town" ),

  DEFAULT("default");

  private List<Pattern> patterns;
  private String dirName;

  PanelShotContext(final String dirName, final String... patternsParam){
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
    return dirName;
  }
}
