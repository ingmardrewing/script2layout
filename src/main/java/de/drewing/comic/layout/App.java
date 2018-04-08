package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import de.drewing.comic.layout.read.Book;
import de.drewing.comic.layout.read.Page;
import de.drewing.comic.layout.render.Renderer;

public class App{
  static String scriptFilename;
  static String script = "";
  static int pagecounter = 0;
  static List<Page> pages;
  static List<BufferedImage> renderedPages;

  public static void main(String[] args) {
    scriptFilename = args[0];
    readScript();
    createPages();
    renderPages();
    savePagesAsImages();
  }

  static void readScript() {
    try {
      FileReader f = new FileReader(scriptFilename);
      BufferedReader b = new BufferedReader(f);

      String line;
      while((line = b.readLine()) != null){
        script += line + "\n";
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
    final Book b = new Book(script);
    pages = b.generatePages();
  }

  static void renderPages() {
    renderedPages = pages.stream()
      .filter(p -> p.ready())
      .map(page -> {
        final Renderer r = new Renderer(page);
        return r.renderPage();
      })
      .collect(Collectors.toList());
  }

  static void savePagesAsImages() {
    if( renderedPages != null) {
      renderedPages
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
