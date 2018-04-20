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

public class CustomJsonShotListEditor extends Application {
  CustomJsonShotListEditor(String[] args) {
      launch(args);
  }

    final ScrollPane sp = new ScrollPane();
    final Image[] images = new Image[5];
    final ImageView[] pics = new ImageView[5];
    final VBox vb = new VBox();
    final Label fileName = new Label();

    final List<Group> groups = new ArrayList<Group>();
    final GridPane grid = new GridPane();
    private int i = 1;

    @Override
    public void start(Stage stage) {
      grid.setHgap(10);
      grid.setVgap(10);

      ScrollPane root = new ScrollPane();
      root.setContent(grid);

      Button btn = new Button("Add TextField");
      grid.add(btn, 0, 0);
      btn.setOnAction( e->{ addGroup(); } );

      final Scene scene = new Scene(root, 500, 400);
      stage.setScene(scene);
      stage.show();
    }

    private void addGroup () {
      final Group g = new Group();
      groups.add(g);
      grid.add(g, 5, i);
      i++;
    }
}
