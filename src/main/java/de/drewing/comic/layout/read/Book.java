package de.drewing.comic.layout.read;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Book {
  private List<String> pagesTexts;
  private String script;
  private List<Page> pages;

  public Book (final String script) {
    this.script = script;
  }

  public List<Page> generatePages() {
    prepareScript();
    return pagesTexts
      .stream()
      .map(Page::new)
      .collect(Collectors.toList());
  }

  private void prepareScript() {
    final String sep = "(?i)Page [0-9]+\\R";
    final List<String> txts = Arrays.asList(script.split(sep));
    pagesTexts = txts.subList(1, txts.size());
  }
}
