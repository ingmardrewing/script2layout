package de.drewing.comic.layout.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.stream.Collectors;

public final class CustomResources {

  private static PanelShotsFromJson psfj;

  public static void init(final String pathString) {
      try {
        final Path fp = Paths.get(pathString);
        final String json = Files
          .lines(fp, StandardCharsets.UTF_8)
          .collect(Collectors.joining(""));
        psfj = new PanelShotsFromJson(json);
      }
      catch(IOException e){
        e.printStackTrace();
      }
  }

  public static String findInText(final String txt) {
    if(psfj != null)
      return psfj.findInText(txt);
    return null;
  }
}
