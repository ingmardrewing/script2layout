package de.drewing.comic.layout;

import java.util.regex.Pattern;

public class Panel {
  public String shot;
  public int size;
  private String script;

  Panel (final String script) {
    this.script = script;
    init();
  }

  private void init() {
    findShot();
    findSize();
    final String result = String.format("Found %s with panelsize %d", shot, size);
    System.out.println(result);
  }

  private void findShot() {
    for(final PanelShot s : PanelShot.values()) {
      if(s.findInText(script)) {
        shot = s.getName();
      }
    }
  }

  private void findSize() {
    for(final PanelSize s : PanelSize.values()) {
      if(s.findInText(script)) {
        size = s.getSize();
      }
    }
  }

  public int calcWidth(final int stripwidth, final int gutterwidth) {
    final int minWidth = stripwidth - 2 * (gutterwidth + (stripwidth/3));
    switch(size) {
      case 2:
        return minWidth;
      case 3:
        return (stripwidth - gutterwidth) / 2;
      case 4:
        return stripwidth - (gutterwidth + minWidth);
      default:
        return stripwidth;
    }
  }

  public int calcHeight(final int stripHeight, final int gutterheight) {
    switch(size) {
      case 12:
        return 2 * stripHeight + gutterheight;
      case 18:
        return 3 * stripHeight + 2 * gutterheight;
      default:
        return stripHeight;
    }
  }
}

