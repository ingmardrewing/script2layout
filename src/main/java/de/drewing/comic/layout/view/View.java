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
import javafx.scene.layout.BorderPane;
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

  private List<ShotModel> shotModels = new ArrayList<ShotModel>();
  private List<ShotView> shotViews = new ArrayList<ShotView>();
  private BorderPane borderPane = new BorderPane();
  private GridPane scrolledGrid = new GridPane();

  private int i = 2;

  public View (
      Stage stage,
      SelectScriptFile selectScriptAction,
      SelectOutputDir selectOutputDirAction,
      GeneratePages generatePagesAction) {
    this.selectScriptAction = selectScriptAction;
    this.selectOutputDirAction = selectOutputDirAction;
    this.generatePagesAction = generatePagesAction;

    scrolledGrid.setHgap(10);
    scrolledGrid.setVgap(10);

    final GridPane top = new GridPane();
    top.setHgap(10);
    top.setVgap(10);
    borderPane.setTop(top);

    final Button addImage = new Button("Add Shot");
    addImage.setOnAction( e->{
      save();
      addShotModel();
      updateList();
    } );
    top.add(addImage,1,1);

    final Button save = new Button("save");
    save.setOnAction( e->{ save(); } );
    save.setLayoutX(100);
    top.add(save,2,1);

    final Button clearV = new Button("clear");
    clearV.setOnAction( e->{ clearList(); } );
    clearV.setLayoutX(150);
    top.add(clearV,3,1 );

    final Button deleteV = new Button("delete");
    deleteV.setOnAction( e->{ shotModels.clear(); } );
    deleteV.setLayoutX(280);
    top.add(deleteV, 4, 1);

    final Button createV = new Button("create");
    createV.setOnAction( e->{ createList(); } );
    createV.setLayoutX(200);
    top.add(createV, 5, 1);

    final ScrollPane center = new ScrollPane();
    final GridPane scrooledGrid = new GridPane();
    center.setContent(scrolledGrid);
    borderPane.setCenter(center);

    final Scene scene = new Scene(borderPane, 800, 600);

    stage.setScene(scene);
    stage.show();
  }

  private void addShotModel() {
    System.out.println("addShotModel");
    final ShotModel sm = new ShotModel();
    shotModels.add(sm);
  }

  private void updateList() {
    clearList();
    createList();
  }

  private void save () {
    System.out.println("save: models");
    System.out.println(shotModels.size());
    System.out.println("save: views");
    System.out.println(shotViews.size());
    shotModels.clear();
    for(ShotView s : shotViews) {
      final ShotModel sm = new ShotModel();
      sm.pattern = s.pattern.getText();
      sm.path = s.path.getText();
      sm.isRegex = s.isRegex.isSelected();
      shotModels.add(sm);
    }
    System.out.println("save: - post loop:");
    System.out.println(shotModels.size());
  }

  private void createList () {
    System.out.println("createList:");
    System.out.println(shotModels.size());
    shotViews.clear();
    for (ShotModel sm : shotModels) {

      final Label l = new Label("Keyword");

      final TextField pattern = new TextField();
      pattern.setText(sm.pattern);
      pattern.setLayoutX(60);

      final CheckBox isRegex = new CheckBox("regex");
      isRegex.setSelected(sm.isRegex);
      isRegex.setLayoutX(240);

      final TextField path = new TextField();
      path.setText(sm.path);
      path.setLayoutX(320);

      final Button select = new Button("Select image");
      select.setLayoutX(420);

      final Button delete = new Button("delete");
      delete.setLayoutX(540);

      final Group g = new Group();
      g.getChildren().add(l);
      g.getChildren().add(pattern);
      g.getChildren().add(isRegex);
      g.getChildren().add(path);
      g.getChildren().add(select);
      g.getChildren().add(delete);

      final ShotView sv = new ShotView();
      sv.pattern = pattern;
      sv.path = path;
      sv.isRegex = isRegex;
      shotViews.add(sv);

      delete.setOnAction( e -> {
        delete(sm, sv);
        updateList();
      } );

      scrolledGrid.add(g, 1, i);
      i++;
    }
  }

  private void clearList() {
    i = 2;
    scrolledGrid.getChildren().clear();
  }

  public void delete (ShotModel sm, ShotView sv) {
    System.out.println("deleting shot");
    shotModels.removeIf(s -> s == sm);
    shotViews.removeIf(s -> s == sv);
    updateList();
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

class ShotModel {
  String pattern;
  String path;
  boolean isRegex;
}

class ShotView {
  TextField pattern;
  TextField path;
  CheckBox isRegex;
}
