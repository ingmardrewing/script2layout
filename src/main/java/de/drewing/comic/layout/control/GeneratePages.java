package de.drewing.comic.layout.control;

import java.io.File;
import java.awt.FileDialog;

import de.drewing.comic.layout.Config;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

import de.drewing.comic.layout.model.Book;
import de.drewing.comic.layout.model.Page;
import de.drewing.comic.layout.view.View;

@SuppressWarnings("serial")
public class GeneratePages implements ActionListener {
  private final Config config;
  private List<Page> pages;
  private View view;

  private static boolean generating = false;

  public GeneratePages(final Config config) {
    this.config = config;
  }

  public void actionPerformed(ActionEvent e) {
    if (generating){
      return;
    }
    if (config.scriptFilename == null) {
      view.showNotification("Missing a script file.\nPlease, select a fitting text file.");
    }
    if (config.outputDir == null) {
      view.showNotification("Missing an output directory.\nPlease select an output directory.");
    }
    generating = true;
    readScript();
    createPages();
    renderAndSavePages();
    view.showNotification("Layouts have been generated at: "+ config.outputDir);
    System.exit(0);
  }

  public void setView (final View view) {
    this.view = view;
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
    pages = b.generatePages();
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

