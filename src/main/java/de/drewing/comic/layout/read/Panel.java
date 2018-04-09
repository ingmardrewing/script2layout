package de.drewing.comic.layout.read;

import java.util.Arrays;
import java.util.regex.Pattern;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Panel {
  private PanelShot shot;
  private PanelSize size;
  private String script;

  Panel (final String script) {
    this.script = script;
    init();
  }

  private void init() {
    findShot();
    findSize();
    final String result = String.format("Found %s with panelsize %d", shot.getPathAndName(), size.asInt());
    System.out.println(result);
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

  public boolean hasImage() {
    return shot != null;
  }

  public BufferedImage getImage() {
    if(hasImage()) {
      final String pathAndName = shot.getPathAndName();
      try {
        return ImageIO.read(getClass()
          .getClassLoader()
          .getResource(pathAndName));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  private void findShot() {
    shot = Arrays.asList(PanelShot.values())
      .stream()
      .filter(s -> s.findInText(script))
      .findFirst()
      .get();
  }

  private void findSize() {
    size = Arrays.asList(PanelSize.values())
      .stream()
      .filter(s -> s.findInText(script))
      .findFirst()
      .get();
  }
}

