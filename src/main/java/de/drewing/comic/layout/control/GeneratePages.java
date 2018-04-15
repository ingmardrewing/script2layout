package de.drewing.comic.layout.control;

import java.io.File;

import javax.swing.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.awt.FileDialog;
import java.awt.*;
import java.awt.event.*;
import java.awt.Desktop;

import de.drewing.comic.layout.model.Config;
import de.drewing.comic.layout.view.View;

@SuppressWarnings("serial")
public class GeneratePages implements ActionListener {
  private final Config config;

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

    final Generator g = new Generator(config);
    g.generate();

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
}

