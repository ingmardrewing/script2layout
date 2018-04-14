package de.drewing.comic.layout.control;

import java.io.File;
import java.awt.FileDialog;

import de.drewing.comic.layout.model.Config;
import de.drewing.comic.layout.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class SelectOutputDir implements ActionListener {
  private final Config config;
  private View view;

  public SelectOutputDir(final Config config) {
    this.config = config;
  }

  public void actionPerformed(ActionEvent e) {
    final String outputDir = view.pickDir("Output dir selection");
    config.outputDir = outputDir;
  }

  public void setView (final View view) {
    this.view = view;
  }
}

