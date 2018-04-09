package de.drewing.comic.layout.read;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum PanelSize {

  ONE_THIRD("one third size", 2),
  HALF("half size", 3),
  TWO_THIRDS("two thirds? size", 4),
  DOUBLE_STRIP("double strip size", 12),
  STRIP("strip size", 6),
  PAGE("page size", 18);

  private int size;
  private Pattern pattern;

  PanelSize(final String pattern, final int size) {
    final String ignoreCase = "(?i)";
    this.pattern = Pattern.compile(ignoreCase+pattern);
    this.size = size;
  }

  public boolean findInText(final String text) {
    final Matcher m = pattern.matcher(text);
    return m.find();
  }

  public int asInt() {
    return size;
  }
}
