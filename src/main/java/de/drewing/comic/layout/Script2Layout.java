package de.drewing.comic.layout;

import de.drewing.comic.layout.model.Config;
import de.drewing.comic.layout.model.custom.CustomResources;
import de.drewing.comic.layout.view.View;
import de.drewing.comic.layout.control.GeneratePages;
import de.drewing.comic.layout.control.Generator;

import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

public class Script2Layout extends Application {

  private static Config config;

  public static void main(String[] args) {
    config = new Config();
    if (args.length == 3 ) {
      runHeadless(args);
    }
    else {
      launch(args);
    }
  }

  private static void runHeadless(final String[] args){
    config.scriptFilename = args[0];
    config.outputDir = args[1];
    config.customResourcePath = args[2];
    CustomResources.init(config.customResourcePath);
    final Generator g = new Generator(config);
    g.generate();
  }

    @Override
    public void start(Stage stage) {
      final GeneratePages generateAction = new GeneratePages(config);
      final View view = new View( config, stage, generateAction);
      generateAction.setView(view);
    }
}

