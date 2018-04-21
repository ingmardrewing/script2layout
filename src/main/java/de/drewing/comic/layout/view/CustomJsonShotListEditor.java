package de.drewing.comic.layout.view;

import de.drewing.comic.layout.model.custom.Shot;
import de.drewing.comic.layout.model.custom.CustomResources;

import java.util.List;
import java.util.ArrayList;
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
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.Scene;

public class CustomJsonShotListEditor  {

  private List<Shot> shotModels = new ArrayList<Shot>();
  private List<ShotView> shotViews = new ArrayList<ShotView>();
  private BorderPane borderPane = new BorderPane();
  private GridPane scrolledGrid = new GridPane();
	private Scene scene = new Scene(borderPane, 800, 600);
	private Stage window = new Stage();

  private int i = 2;

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
      save();
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
    for(final Shot s : shotModels){
      CustomResources.addShot(s);
      CustomResources.save();
    }
  }

  private void addShotModel() {
    final Shot sm = new Shot("", "", false);
    shotModels.add(sm);
  }

  private void updateList() {
    clearList();
    createList();
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

  private void createList () {
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
				final String imgPath = pickImage();
				sm.path = imgPath;
				save();
        updateList();
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

  public void delete (Shot sm, ShotView sv) {
    shotModels.removeIf(s -> s == sm);
    shotViews.removeIf(s -> s == sv);
  }

  public String pickImage() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File file = fileChooser.showOpenDialog(window);
    if( file != null){
      return file.getAbsolutePath();
    }
    return null;
  }

}

class ShotView {
  TextField pattern;
  TextField path;
  CheckBox isRegex;
}
