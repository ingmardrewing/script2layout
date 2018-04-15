package de.drewing.comic.layout.control;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

import de.drewing.comic.layout.model.Book;
import de.drewing.comic.layout.model.Page;
import de.drewing.comic.layout.model.PanelSequence;
import de.drewing.comic.layout.model.Config;

public class Generator {

  private List<Page> pages;
  private PanelSequence panelSequence;
  private final Config config;

  public Generator(final Config config){
    this.config = config;
  }

  public void generate() {
    readScript();
    createPages();
    checkPanelSequence();
    renderAndSavePages();
  }

  private void readScript() {
    try {
      FileReader f = new FileReader(config.scriptFilename);
      BufferedReader b = new BufferedReader(f);

      String line;
      while((line = b.readLine()) != null){
        config.script += line + "\n";
      }
    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  private void createPages() {
    final Book b = new Book(config.script);
    b.generatePages();
    panelSequence = b.getPanelSequence();
    pages = b.getPages();
  }

  private void checkPanelSequence() {
    panelSequence.checkDoubles();
  }

  private void renderAndSavePages() {
    pages.stream()
      .forEach(page ->{
        if(page.ready()){
          final Renderer r = new Renderer(page);
          saveRenderedPage(r.renderPage(),page.number());
        }
        else {
          System.out.println("Page "+page.number()+" can't be rendered,"
              +" due to wrong panel sizes.");
        }
      });
  }

  private void saveRenderedPage(final BufferedImage img, final int nr) {
    final String fileName = config.outputDir +"/page"+String.format("%03d", nr)+".png";
    File f = new File(fileName);
    try {
      ImageIO.write(img, "png", f);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

}
