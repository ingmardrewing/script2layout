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
import de.drewing.comic.layout.ui.FilePicker;

public class App{
  static String scriptFilename;
  static String outputDir;
  static String script = "";
  static int pagecounter = 0;
  static List<Page> pages;

  public static void main(String[] args) {
		scriptFilename =System.getProperty("user.dir") + "/script.txt";
    System.out.println("Asking for path to script file");
    getPathToScriptFile();
    System.out.println("Asking for path to output dir");
    getPathToOutputDir();
    System.out.println("Reading script file");
    readScript();
    System.out.println("Creating pages from script");
    createPages();
    System.out.println("Rendering and saving pages");
    renderAndSavePages();
    System.out.println("Ending program");
    System.exit(0);
    return;
  }

  static void getPathToScriptFile() {
    final FilePicker f = new FilePicker();
		final String pathToScript = f.open();
		if ( pathToScript != null ) {
			scriptFilename = pathToScript;
		}
  }

  static void getPathToOutputDir() {
    final FilePicker f = new FilePicker();
		final String dir = f.openDir();
		if ( dir != null ) {
			outputDir = dir;
		}
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
    final String fileName = outputDir +"/page"+String.format("%03d", nr)+".png";
    File f = new File(fileName);
    try {
      ImageIO.write(img, "png", f);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
