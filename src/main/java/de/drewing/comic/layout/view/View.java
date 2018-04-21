package de.drewing.comic.layout.view;

import de.drewing.comic.layout.model.custom.Shot;
import de.drewing.comic.layout.model.custom.CustomResources;
import de.drewing.comic.layout.control.GeneratePages;

import java.io.File;

import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.Scene;

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
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import de.drewing.comic.layout.model.Config;

public class View {

  private List<Shot> shotModels = new ArrayList<Shot>();
  private List<ShotView> shotViews = new ArrayList<ShotView>();

  private GeneratePages generatePagesAction;

  private GridPane grid = new GridPane();
  private GridPane scrolledGrid = new GridPane();

  private BorderPane borderPane = new BorderPane();
  private Config config;
	private Scene scene;
  private Stage stage;
  private int i = 2;

  public View (
      Config config,
      Stage stage,
      GeneratePages generatePagesAction) {
    this.stage = stage;
    this.config = config;
    this.generatePagesAction = generatePagesAction;

    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(10, 10, 10, 10));

    final TextField scriptField = new TextField();
    grid.add(scriptField,1,1);

    final TextField outputField = new TextField();
    grid.add(outputField,1,2);

    final Button selectScript = new Button("Select script file");
    selectScript.setOnAction( e->{
      final String scriptFile = pickFile();
      config.scriptFilename = scriptFile;
      scriptField.setText(scriptFile);
    } );
    grid.add(selectScript,2,1);

    final Button selectDir = new Button("Select output directory");
    selectDir.setOnAction( e->{
      final String outputDir = pickDir();
      config.outputDir = outputDir;
      outputField.setText(outputDir);
    } );
    grid.add(selectDir,2,2);

    final Button generatePages = new Button("Generate pages");
    generatePages.setOnAction( e->{
      generatePagesAction.actionPerformed(null);
    } );
    grid.add(generatePages,3,2);

		borderPane.setTop(grid);
    scene = new Scene(borderPane, 800, 600);
    stage.setScene(scene);

    open();

    stage.show();
  }

  public void showNotification( final String msg) {
    System.out.println(msg);
  }

  public String pickDir() {
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setTitle("Open Resource File");
    File dir = dirChooser.showDialog(stage);
    if( dir != null){
      final String p = dir.getAbsolutePath();
      System.out.println(p);
      return p;
    }
    System.out.println("no dir selected");
    return null;
  }

  static String lastPicked = "";

  public String pickFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File file = fileChooser.showOpenDialog(stage);
    if( file != null){
      final String p = file.getAbsolutePath();
      System.out.println(p);
      return p;
    }
    System.out.println("no file selected");
    return null;
  }

	public void open() {
    scrolledGrid.setHgap(10);
    scrolledGrid.setVgap(10);

    final GridPane left= new GridPane();
    left.setHgap(10);
    left.setVgap(10);
    left.setPadding(new Insets(10, 10, 10, 10));

    borderPane.setLeft(left);

    final Button addImage = new Button("Add Shot");
    addImage.setOnAction( e->{
      save();
      addShotModel();
      updateDisplayedList();
    } );
    left.add(addImage,1,1);

    final Button save = new Button("save");
    save.setOnAction( e->{
      save();
      persistData();
    } );
    save.setLayoutX(100);
    left.add(save,1,2);

    final ScrollPane center = new ScrollPane();
    final GridPane scrooledGrid = new GridPane();
    center.setContent(scrolledGrid);
    center.setPadding(new Insets(10, 10, 10, 10));
    borderPane.setCenter(center);

		shotModels = CustomResources.getShots();
		updateDisplayedList();

    // TODO: Fix this - data needs to be read to be directly
    // relevant for the rendering:
    persistData();
  }

  private void persistData() {
    for(final Shot s : shotModels){
      CustomResources.addShot(s);
      CustomResources.save();
    }
  }

  private void addShotModel() {
    final Shot sm = new Shot("", "", false);
    shotModels.add(sm);
  }

  private void updateDisplayedList() {
    clearDisplayedList();
    createDisplayedList();
  }

  private void save () {
    shotModels.clear();
    for(ShotView s : shotViews) {
      final Shot sm = new Shot("", "", false);
      sm.searchString = s.pattern.getText();
      sm.path = s.path.getText();
      sm.isRegex = s.isRegex.isSelected();
      shotModels.add(sm);
    }
  }

  private void createDisplayedList () {
    shotViews.clear();
    for (final Shot sm : shotModels) {

      final Label l = new Label("Keyword");
      l.setLayoutY(4);

      final TextField pattern = new TextField();
      pattern.setText(sm.searchString);
      pattern.setLayoutX(60);

      final CheckBox isRegex = new CheckBox("regex");
      isRegex.setSelected(sm.isRegex);
      isRegex.setLayoutX(240);
      isRegex.setLayoutY(4);

      final TextField path = new TextField();
      path.setText(sm.path);
      path.setLayoutX(320);

      final Button select = new Button("Select image");
      select.setLayoutX(490);
      select.setOnAction( e -> {
				final String imgPath = pickFile();
				sm.path = imgPath;
        updateDisplayedList();
      } );

      final Button delete = new Button("delete");
      delete.setLayoutX(600);

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
        save();
        updateDisplayedList();
      } );

      scrolledGrid.add(g, 1, i);
      i++;
    }
  }

  private void clearDisplayedList() {
    i = 2;
    scrolledGrid.getChildren().clear();
  }

  public void delete (Shot sm, ShotView sv) {
    shotModels.removeIf(s -> s == sm);
    shotViews.removeIf(s -> s == sv);
  }
}

class ShotView {
  TextField pattern;
  TextField path;
  CheckBox isRegex;
}
