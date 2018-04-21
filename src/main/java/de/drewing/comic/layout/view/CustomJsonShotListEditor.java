package de.drewing.comic.layout.view;

import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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


public class CustomJsonShotListEditor  {

  private List<ShotModel> shotModels = new ArrayList<ShotModel>();
  private List<ShotView> shotViews = new ArrayList<ShotView>();
  private BorderPane borderPane = new BorderPane();
  private GridPane scrolledGrid = new GridPane();
	private Scene scene = new Scene(borderPane, 800, 600);
	private Stage window = new Stage();

  private int i = 2;

	public CustomJsonShotListEditor() {
		System.out.println("editor created");
	}

	public void open() {
		window.setTitle("edit custom shots");

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
    save.setOnAction( e->{
      persistData();
    } );
    save.setLayoutX(100);
    top.add(save,2,1);

    final ScrollPane center = new ScrollPane();
    final GridPane scrooledGrid = new GridPane();
    center.setContent(scrolledGrid);
    borderPane.setCenter(center);

		window.setScene(scene);
    window.show();
  }

  private void persistData() {
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
