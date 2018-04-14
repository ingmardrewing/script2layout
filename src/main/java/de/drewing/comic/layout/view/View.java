package de.drewing.comic.layout.view;

import de.drewing.comic.layout.control.SelectScriptFile;
import de.drewing.comic.layout.control.SelectOutputDir;
import de.drewing.comic.layout.control.GeneratePages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View {
  private JButton selectScriptBtn;
  private JButton selectOutputDirBtn;
  private JButton generateBtn;

  private JFrame frame;
  private JPanel panel;

  private SelectScriptFile selectScriptAction;
  private SelectOutputDir selectOutputDirAction;
  private GeneratePages generatePagesAction;

  public View (
      SelectScriptFile selectScriptAction,
      SelectOutputDir selectOutputDirAction,
      GeneratePages generatePagesAction) {
    this.selectScriptAction = selectScriptAction;
    this.selectOutputDirAction = selectOutputDirAction;
    this.generatePagesAction = generatePagesAction;
  }

  public void init () {
    createButtons();
    createPanel();
    createFrame();
  }

  private void createButtons() {
    selectScriptBtn = new JButton("Select script file");
    selectScriptBtn.addActionListener(selectScriptAction);

    selectOutputDirBtn = new JButton("Select output directory");
    selectOutputDirBtn.addActionListener(selectOutputDirAction);

    generateBtn = new JButton("Generate pages");
    generateBtn.addActionListener(generatePagesAction);
  }

  private void createPanel(){
    panel = new JPanel();
    panel.add(selectScriptBtn);
    panel.add(selectOutputDirBtn);
    panel.add(generateBtn);
  }

  private void createFrame() {
    final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    final JFrame frame = new JFrame("script2layout");
    frame.setSize(200,150);
    frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(panel);
    frame.setVisible(true);
  }

  public void showNotification( final String msg) {
    final JFrame frame = new JFrame("");
    JOptionPane.showMessageDialog(frame, msg);
  }

  public String pickDir(final String title) {
		System.setProperty("apple.awt.fileDialogForDirectories", "true");
    final String outputDir = pick(title, null);
		System.setProperty("apple.awt.fileDialogForDirectories", "false");
		return outputDir;
  }

  public String pickFile(final String title) {
		return pick(title, "*.txt");
  }

  private String pick(final String title, final String filter) {
    final JFrame frame = new JFrame(title);
		FileDialog fd = new FileDialog(frame, "", FileDialog.LOAD);
		final String dir = System.getProperty("user.dir");
		fd.setDirectory(dir);
    if (filter != null){
      fd.setFile(filter);
    }
		fd.setVisible(true);
		return fd.getDirectory() + fd.getFile();
  }
}
