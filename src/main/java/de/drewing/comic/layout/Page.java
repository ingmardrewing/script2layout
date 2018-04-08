package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Page{
  private static int REQUIRED_SIZE = 18;

  private String script;
  private String[] panelTexts;
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
    final String panelSeparatorPattern = "(?i)Panel [0-9]+\\R";
    final String[] parts = script.split(panelSeparatorPattern);
    panelTexts = Arrays.copyOfRange(parts, 1, parts.length);
  }

  private void generatePanels() {
    panels = new ArrayList<Panel>();
    for (final String panelText : panelTexts) {
      panels.add(new Panel(panelText));
    }
  }

  boolean ready() {
    int i = 0;
    for(final Panel p : panels) {
      i += p.getSize().asInt();
    }
    return i == REQUIRED_SIZE;
  }

  public Panel[] getPanels() {
    return panels.stream().toArray(Panel[]::new);
  }
}
