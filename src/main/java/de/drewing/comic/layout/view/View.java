package de.drewing.comic.layout.view;

import de.drewing.comic.layout.control.GeneratePages;

import java.io.File;

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

  private GeneratePages generatePagesAction;

  private CustomJsonShotListEditor cjsle;
  private GridPane grid = new GridPane();

  private Config config;
  private Stage stage;

  public View (
      Config config,
      Stage stage,
      GeneratePages generatePagesAction) {
    this.stage = stage;
    this.config = config;
    this.generatePagesAction = generatePagesAction;

    cjsle = new CustomJsonShotListEditor();
    grid.setHgap(10);
    grid.setVgap(10);

    final Button openEditor = new Button("Edit custom shots");
    openEditor.setOnAction( e->{
      cjsle.open();
    } );
    grid.add(openEditor,1,1);

    final TextField scriptField = new TextField();
    grid.add(scriptField,1,2);

    final TextField outputField = new TextField();
    grid.add(outputField,1,3);

    final Button selectScript = new Button("Select script file");
    selectScript.setOnAction( e->{
      final String scriptFile = pickFile();
      config.scriptFilename = scriptFile;
      scriptField.setText(scriptFile);
    } );
    grid.add(selectScript,2,2);

    final Button selectDir = new Button("Select output directory");
    selectDir.setOnAction( e->{
      final String outputDir = pickDir();
      config.outputDir = outputDir;
      outputField.setText(outputDir);
    } );
    grid.add(selectDir,2,3);

    final Button generatePages = new Button("Generate pages");
    generatePages.setOnAction( e->{
      generatePagesAction.actionPerformed(null);
    } );
    grid.add(generatePages,2,1);

    Scene scene = new Scene(grid, 400, 200);
    stage.setScene(scene);

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
      return dir.getAbsolutePath();
    }
    return null;
  }

  public String pickFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File file = fileChooser.showOpenDialog(stage);
    if( file != null){
      return file.getAbsolutePath();
    }
    return null;
  }
}
