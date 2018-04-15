package de.drewing.comic.layout.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Book {
  private List<String> pagesTexts;
  private String script;
  private PanelSequence panelSequence;
  private List<Page> pages;

  public Book (final String script) {
    this.script = script;
    init();
  }

  private void init() {
    pages = new ArrayList<Page>();
    panelSequence = new PanelSequence();
    prepareScript();
  }

  public void generatePages() {
    int i = 0;
    for(final String txt : pagesTexts){
      final Page page = new Page(txt,i);
      for( Panel p : page.getPanels()) {
        panelSequence.add(p);
      }
      pages.add(page);
      i++;
    }
  }

  public PanelSequence getPanelSequence() {
    return panelSequence;
  }

  public List<Page> getPages() {
    return pages;
  }

  private void prepareScript() {
    final String sep = "(?i)Page [0-9]+\\R";
    final List<String> txts = Arrays.asList(script.split(sep));
    pagesTexts = txts.subList(1, txts.size());
  }
}
