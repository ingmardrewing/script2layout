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

  public static void main(String[] args) {
    scriptFilename = args[0];
    readScript();
    createPages();
    renderAndSavePages();
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

  static void renderAndSavePages() {
    pages.stream()
      .forEach(page ->{
        if(page.ready()){
          final Renderer r = new Renderer(page);
          App.saveRenderedPage(r.renderPage(),page.number());
        }
        else {
          System.out.println("Page "+page.number()+" can't be rendered,"
              +" due to wrong panel sizes.");
        }
      });
  }

  static void saveRenderedPage(final BufferedImage img, final int nr) {
    final String fileName = "page"+String.format("%03d", nr)+".png";
    File f = new File(fileName);
    try {
      ImageIO.write(img, "png", f);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
