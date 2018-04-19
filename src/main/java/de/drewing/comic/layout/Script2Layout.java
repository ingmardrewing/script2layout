package de.drewing.comic.layout;

import de.drewing.comic.layout.model.Config;
import de.drewing.comic.layout.model.CustomResources;
import de.drewing.comic.layout.view.View;
import de.drewing.comic.layout.control.SelectScriptFile;
import de.drewing.comic.layout.control.SelectOutputDir;
import de.drewing.comic.layout.control.GeneratePages;
import de.drewing.comic.layout.control.Generator;

public class Script2Layout{

  public static void main(String[] args) {
    final Config config = new Config();
    if (args.length == 3 ) {
      config.scriptFilename = args[0];
      config.outputDir = args[1];
      config.customResourcePath = args[2];
      runHeadless(config);
    }
    else {
      runWithGui(config);
    }
  }

  private static void runHeadless(final Config config){
    CustomResources.init(config.customResourcePath);
    final Generator g = new Generator(config);
    g.generate();
  }

  private static void runWithGui(final Config config) {
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
