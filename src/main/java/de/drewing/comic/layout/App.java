package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class App{

  static String scriptFilename;
  static int pagecounter = 0;
  static List<String> scriptLines = new ArrayList<String>();
  static List<BufferedImage> pages;

  public static void main(String[] args) {
    scriptFilename = args[0];
    readScriptLines();
    createPages();
    savePagesAsImages();
  }

  static void readScriptLines() {
    try {
      FileReader f = new FileReader(scriptFilename);
      BufferedReader b = new BufferedReader(f);

      String line;
      while((line = b.readLine()) != null){
        scriptLines.add(line);
      }
    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  static void createPages() {
    String script = "";
    for (String s : scriptLines){
      script += s + "\n";
    }
    final Book b = new Book(script);
    pages = b.renderPages();
  }

  static void savePagesAsImages() {
    if( pages != null) {
     pages
        .stream()
        .forEach(App::saveRenderedPage);
    }
  }

  static void saveRenderedPage(final BufferedImage img) {
    File f = new File("page"+(App.pagecounter++)+".png");
    try {
      ImageIO.write(img, "png", f);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
