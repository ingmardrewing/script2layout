package de.drewing.comic.layout.view;

import de.drewing.comic.layout.control.SelectScriptFile;
import de.drewing.comic.layout.control.SelectOutputDir;
import de.drewing.comic.layout.control.GeneratePages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class View {
  private JButton selectScriptBtn;
  private JButton selectOutputDirBtn;
  private JButton generateBtn;

  private JFrame frame;

  private JPanel panel;

  private SelectScriptFile selectScriptAction;
  private SelectOutputDir selectOutputDirAction;
  private GeneratePages generatePagesAction;

  private CustomJsonShotListEditor cjsle;

  final List<ShotView> shotViews = new ArrayList<ShotView>();
  final GridPane grid = new GridPane();
  private int i = 1;

  public View (
      Stage stage,
      SelectScriptFile selectScriptAction,
      SelectOutputDir selectOutputDirAction,
      GeneratePages generatePagesAction) {
    this.selectScriptAction = selectScriptAction;
    this.selectOutputDirAction = selectOutputDirAction;
    this.generatePagesAction = generatePagesAction;


      Button btn = new Button("Add TextField");
      btn.setOnAction( e->{ addGroup(); } );

      grid.setHgap(10);
      grid.setVgap(10);
      grid.add(btn, 0, 0);

      ScrollPane root = new ScrollPane();
      root.setContent(grid);

      final Scene scene = new Scene(root, 800, 600);

      stage.setScene(scene);
      stage.show();

  }

  private void addGroup () {

    final Group g = new Group();

    final Label l = new Label("Keyword");
    g.getChildren().add(l);

    final TextField pattern = new TextField();
    pattern.setLayoutX(60);
    g.getChildren().add(pattern);

    final CheckBox isRegex = new CheckBox("regex");
    isRegex.setLayoutX(240);
    g.getChildren().add(isRegex);

    final TextField path = new TextField();
    path.setLayoutX(320);
    g.getChildren().add(path);

    final Button btn = new Button("Select image path");
    btn.setLayoutX(420);
    g.getChildren().add(btn);

    final ShotView sv = new ShotView();
    sv.pattern = pattern;
    sv.path = path;
    sv.isRegex = isRegex;

    shotViews.add(sv);

    grid.add(g, 5, i);
    i++;
  }

  public void init () {
    createButtons();
    createPanel();
    createFrame();
    createCustomJsonListEditor();
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

  private void createCustomJsonListEditor() {
    cjsle = new CustomJsonShotListEditor(new String[]{});
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

class ShotView {
  TextField pattern;
  TextField path;
  CheckBox isRegex;
}
