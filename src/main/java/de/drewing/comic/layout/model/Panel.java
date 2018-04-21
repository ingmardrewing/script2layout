package de.drewing.comic.layout.model;

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
import java.util.regex.*;

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

import de.drewing.comic.layout.model.custom.CustomResources;

public class Panel {
  private PanelShot shot;
  private PanelShotContext context;
  private PanelSize size;
  private String script;
  private boolean isCustomImage;

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

  public List<String> getScriptWithLineLength(final int size) {
    List<String> shortenedLines = new ArrayList<String>();
    for(final String l : getScriptWithSeparatedDialog()) {
        final Pattern p = Pattern.compile("(.{0,"+size+"}\\S*\\s?)");
        final Matcher m = p.matcher(l);
        while(m.find()){
          shortenedLines.add(m.group());
        }
    }
    return shortenedLines;
  }

  List<String> getScriptWithSeparatedDialog() {
    final String[] lines = script.split("\\R");
    final List<String> s = new ArrayList<String>();

    String current = "";
    for(final String line : lines) {
      if(line.matches("(^[A-Z ]+$)")){
        s.add(current);
        s.add(line);
        current = "";
      }
      else {
        current += line + "\n";
      }
    }
    if(current.length() > 0) {
      s.add(current);
    }
    return s;
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

  public String getScript() {
    return script;
  }

  public boolean hasImage() {
    return shot != null;
  }

  public boolean hasSize() {
    return size != PanelSize.UNDEFINED;
  }

  private String getImagePath() {
    final String customPath = CustomResources.findInText(script) ;
    if(customPath != null) {
      isCustomImage = true;
      return customPath;
    }

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
        if(isCustomImage) {
          return ImageIO.read(new File(imagePath));
        }
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

