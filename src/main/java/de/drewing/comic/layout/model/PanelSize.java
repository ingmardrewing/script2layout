package de.drewing.comic.layout.model;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public enum PanelSize {

  TWO_THIRDS(4, "two thirds? size", "two thirds?"),
  ONE_THIRD(2, "one third size", "third size", "third"),
  HALF(3, "half size", "half"),
  DOUBLE_STRIP(12, "double strip size", "double size"),
  STRIP(6, "strip size", "strip"),
  PAGE(18, "page size"),
  UNDEFINED(0, "");

  private List<Pattern> sizePatterns;
  private int size;

  PanelSize(final int size, final String... patternParam) {
    sizePatterns = new ArrayList<Pattern>();
    final String ignoreCase = "(?i)";
    for (final String p : patternParam){
      sizePatterns.add(Pattern.compile(ignoreCase + p));
    }
    this.size = size;
  }

  public boolean findInText(final String text) {
    for (final Pattern p : sizePatterns) {
      final Matcher m = p.matcher(text);
      if(m.find()){
        return true;
      }
    }
    return false;
  }

  public int asInt() {
    return size;
  }
}
