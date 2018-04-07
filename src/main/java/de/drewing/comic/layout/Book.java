package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
    int counter = 0;
    for(final Page p : pages) {
      counter++;
      if (p.ready()) {
        final Renderer r = new Renderer(p);
        r.renderPage();
        final BufferedImage b = r.getImage();
        saveRenderedPage(b, counter);
      }
    }
  }

  private void saveRenderedPage(final BufferedImage img, final int i) {
   File f = new File("page"+i+".png");
    try {
      ImageIO.write(img, "png", f);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
