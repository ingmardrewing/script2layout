package de.drewing.comic.layout;

import java.util.regex.Pattern;

public class Panel {
  private PanelShot shot;
  private PanelSize size;
  private String script;

  Panel (final String script) {
    this.script = script;
    init();
  }

  public String getScript() {
    return script;
  }

  public PanelSize getSize() {
    return size;
  }

  public PanelShot getShot() {
    return shot;
  }

  private void init() {
    findShot();
    findSize();
    final String result = String.format("Found %s with panelsize %d", shot.getName(), size.asInt());
    System.out.println(result);
  }

  private void findShot() {
    for(final PanelShot s : PanelShot.values()) {
      if(s.findInText(script)) {
        shot = s;
        break;
      }
    }
  }

  private void findSize() {
    for(final PanelSize s : PanelSize.values()) {
      if(s.findInText(script)) {
        size = s;
        break;
      }
    }
  }
}

