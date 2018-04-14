package de.drewing.comic.layout.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Book {
  private List<String> pagesTexts;
  private String script;
  private List<Page> pages;

  public Book (final String script) {
    this.script = script;
  }

  public List<Page> generatePages() {
    prepareScript();
    int i = 0;
    pages = new ArrayList<Page>();
    for(final String txt : pagesTexts){
      pages.add(new Page(txt,i));
      i++;
    }
    return pages;
  }

  private void prepareScript() {
    final String sep = "(?i)Page [0-9]+\\R";
    final List<String> txts = Arrays.asList(script.split(sep));
    pagesTexts = txts.subList(1, txts.size());
  }
}
