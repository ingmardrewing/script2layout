package de.drewing.comic.layout.read;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

import java.io.IOException;
import java.io.File;
import java.io.FilenameFilter;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.net.URISyntaxException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

public class Panel {
  private PanelShot shot;
  private PanelShotContext context;
  private PanelSize size;
  private String script;

  private String imagePath;
  private int sizeAsInt;

  Panel (final String script) {
    this.script = script;
    init();
  }

  private void init() {
    shot = findShot();
    size = findSize();
    context = findContext();

    imagePath = getImagePath();
    sizeAsInt = size.asInt();
    final String result = String.format("Found %s with panelsize %d", imagePath , sizeAsInt);
    System.out.println(result);
  }

  public String getScript() {
    return script;
  }

  public List<String> getScriptWithLineLength(final int size) {
    List<String> ret = new ArrayList<String>((script.length() + size - 1) / size);
    for (int start = 0; start < script.length(); start += size) {
        ret.add(script.substring(start, Math.min(script.length(), start + size)));
    }
    return ret;
  }

  void setSize(PanelSize size) {
    this.size = size;
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

  public boolean hasSize() {
    return size != PanelSize.UNDEFINED;
  }

  private String getImagePath() {
    final String imageDir = "images";
    final String contextDir = context.getDir();
    final String shotDir = shot.getDir();
    final String name = "image000.png";

    final Path path = Paths.get(imageDir, contextDir, shotDir);
    return Paths.get(path.toString(), name ).toString();
  }

  public BufferedImage getImage() {
    if(hasImage()) {
     try {
        return ImageIO.read(getClass().getClassLoader().getResource(imagePath));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  private PanelShot findShot() {
    for (final PanelShot s : PanelShot.values()){
      if(s.findInText(script)) {
        return s;
      }
    }
    return PanelShot.DEFAULT;
  }

  private PanelSize findSize() {
    for (final PanelSize s : PanelSize.values()) {
      if(s.findInText(script)) {
        return s;
      }
    }
    return PanelSize.ONE_THIRD;
  }

  private PanelShotContext findContext() {
    for (final PanelShotContext s : PanelShotContext.values()) {
      if(s.findInText(script)) {
        return s;
      }
    }
    return PanelShotContext.DEFAULT;
  }
}

