package de.drewing.comic.layout.control;

import java.io.File;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.awt.FileDialog;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

import de.drewing.comic.layout.model.Config;
import de.drewing.comic.layout.model.Book;
import de.drewing.comic.layout.model.Page;
import de.drewing.comic.layout.model.PanelSequence;
import de.drewing.comic.layout.view.View;

@SuppressWarnings("serial")
public class GeneratePages implements ActionListener {
  private final Config config;
  private List<Page> pages;
  private PanelSequence panelSequence;
  private View view;

  private static boolean generating = false;

  public GeneratePages(final Config config) {
    this.config = config;
  }

  public void actionPerformed(ActionEvent e) {
    if (!isExecutable()) {
      return;
    }
    generating = true;
    view.showNotification("Generating pages now.\nThis might take some time.");

    readScript();
    createPages();
    checkPanelSequence();
    renderAndSavePages();
    finish();
  }

  private boolean isExecutable() {
    List<String> msgs = new ArrayList<String>();
    if (generating){
      msgs.add("Already creating layout.");
    }
    if (config.scriptFilename == null) {
      msgs.add("Missing a script file.");
      msgs.add("Please, select a fitting text file.");
    }
    if (config.outputDir == null) {
      msgs.add("Missing an output directory.");
      msgs.add("Please select an output directory.");
    }
    if(msgs.size() == 0) {
      return true;
    }
    final String m = String.join("\n", msgs.toArray(new String[0]));
    view.showNotification(m);
    return false;
  }

  private void finish () {
    view.showNotification("Layouts have been generated at: "+ config.outputDir);
    try {
      Desktop.getDesktop().open(new File(config.outputDir));
    }
    catch(IOException e){
      // do nothing, user has already seen a notification
      // about where the generated files are.
    }
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

