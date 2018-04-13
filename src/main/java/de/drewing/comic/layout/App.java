package de.drewing.comic.layout;

import de.drewing.comic.layout.view.View;

import de.drewing.comic.layout.control.SelectScriptFile;
import de.drewing.comic.layout.control.SelectOutputDir;
import de.drewing.comic.layout.control.GeneratePages;

public class App{
  private static Config config;

  public static void main(String[] args) {
    final Config config = new Config();

    final SelectScriptFile scriptAction = new SelectScriptFile(config);
    final SelectOutputDir dirAction = new SelectOutputDir(config);
    final GeneratePages generateAction = new GeneratePages(config);

    final View view = new View(scriptAction, dirAction, generateAction);

    scriptAction.setView(view);
    dirAction.setView(view);
    generateAction.setView(view);

    view.init();
  }
}
