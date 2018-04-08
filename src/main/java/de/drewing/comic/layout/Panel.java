package de.drewing.comic.layout;

import java.util.regex.Pattern;

public class Panel {
  public String shot;
  public PanelSize size;
  private String script;

  Panel (final String script) {
    this.script = script;
    init();
  }

  private void init() {
    findShot();
    findSize();
    final String result = String.format("Found %s with panelsize %d", shot, size.getSize());
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
        size = s;
      }
    }
  }

}

