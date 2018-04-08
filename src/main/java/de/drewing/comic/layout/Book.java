package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import de.drewing.comic.layout.render.Renderer;

public class Book {
  private List<String> pagesTexts;
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
    final String sep = "(?i)Page [0-9]+\\R";
    final List<String> txts = Arrays.asList(script.split(sep));
    pagesTexts = txts.subList(1, txts.size());
  }

  private void generatePages() {
    pages = pagesTexts
      .stream()
      .map(Page::new)
      .collect(Collectors.toList());
  }

  List<BufferedImage> renderPages() {
    return pages.stream()
      .filter(p -> p.ready())
      .map(page -> {
        final Renderer r = new Renderer(page);
        return r.renderPage();
      })
      .collect(Collectors.toList());
  }
}
