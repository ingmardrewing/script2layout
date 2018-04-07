package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import de.drewing.comic.layout.render.Renderer;

public class Book {
  private String[] pagesTexts;
  private String script;
  private List<Page> pages;

  Book (final String script) {
    this.script = script;
    init();
  }

  private void init ()  {
    prepareScript();
    generatePages();
    renderPages();
  }

  private void prepareScript() {
    final String ignoreCase = "(?i)";
    final String pagesSeparatorPattern = "Page [0-9]+\\R";
    final String[] parts = script.split(ignoreCase + pagesSeparatorPattern);
    pagesTexts = Arrays.copyOfRange(parts, 1, parts.length);
  }

  private void generatePages() {
    pages = new ArrayList<Page>();
    for (final String pageText : pagesTexts) {
      pages.add(new Page(pageText));
    }
  }

  private void renderPages() {
    final Renderer r = new Renderer();
    for(final Page p : pages) {
        r.render(p);
    }
  }
}
