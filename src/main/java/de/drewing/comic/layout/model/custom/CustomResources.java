package de.drewing.comic.layout.model.custom;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

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

  public static void addShot(final Shot shot) {
    if(psfj == null) {
        psfj = new PanelShotsFromJson();
    }
    psfj.addShot(shot);
  }

  public static void save() {
    final String json = psfj.getShotsAsJson();
    List<String> lines = Arrays.asList(json);
    Path file = Paths.get("customShots.json");
    try{
      Files.write(file, lines, Charset.forName("UTF-8"));
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

  public static void addCustomShot(final String pattern,
                                   final String path,
                                   final boolean isRegex){
    psfj.addCustomShot(pattern, path, isRegex);
  }

	public static List<Shot> getShots() {
		final String json = readShotsJson();
    final PanelShotsFromJson ps = new PanelShotsFromJson(json);
		return ps.getShots();
	}

  private static String readShotsJson() {
		String json = "";
    try {
      FileReader f = new FileReader("customShots.json");
      BufferedReader b = new BufferedReader(f);

			String line;
      while((line = b.readLine()) != null){
        json += line ;
      }
    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
		return json;
  }
}
