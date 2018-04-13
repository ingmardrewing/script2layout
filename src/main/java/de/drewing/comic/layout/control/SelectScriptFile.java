package de.drewing.comic.layout.control;

import java.io.File;
import java.awt.FileDialog;

import de.drewing.comic.layout.Config;
import de.drewing.comic.layout.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class SelectScriptFile implements ActionListener {
  private final Config config;
  private View view;

  public SelectScriptFile(final Config config) {
    this.config = config;
  }

  public void setView (final View view) {
    this.view = view;
  }

  public void actionPerformed(ActionEvent e) {
    final String scriptFile = view.pickFile("Script selection");
    config.scriptFilename = scriptFile;
  }
}

