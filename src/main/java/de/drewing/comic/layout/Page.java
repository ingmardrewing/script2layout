package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Page{
  private static int REQUIRED_SIZE = 18;

  private String script;
  private List<String> panelTexts;
  private List<Panel> panels;

  Page (final String script ) {
    this.script = script;
    init();
  }

  private void init () {
    System.out.println("Creating page:");
    prepareScript();
    generatePanels();
    System.out.println();
  }

  private void prepareScript() {
    final String sep = "(?i)Panel [0-9]+\\R";
    final List<String> txts = Arrays.asList(script.split(sep));
    panelTexts = txts.subList(1, txts.size());
  }

  private void generatePanels() {
    panels = panelTexts
      .stream()
      .map(Panel::new)
      .collect(Collectors.toList());
  }

  boolean ready() {
    int i = panels
      .stream()
      .mapToInt(p -> p.getSize().asInt())
      .sum();
    return i == REQUIRED_SIZE;
  }

  public List<Panel> getPanels() {
    return panels;
  }
}
