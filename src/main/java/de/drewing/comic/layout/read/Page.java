package de.drewing.comic.layout.read;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Page{
  private static int MAX_SIZE = 18;

  private String script;
  private List<String> panelTexts;
  private List<Panel> panels;
  private int number;

  Page (final String script, final int number) {
    this.script = script;
    this.number = number;
    init();
  }

  private void init () {
    System.out.println("Reading page:" + number);
    prepareScript();
    generatePanels();
    checkSizes();
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

  private void checkSizes() {
    final PanelSizeInferer psi = new PanelSizeInferer(panels);
    psi.checkPanelsAndGenerateSizesIfNotSet();
    panels = psi.getPanels();
  }

  public int number() {
    return number;
  }

  public boolean ready() {
    int i = panels
      .stream()
      .mapToInt(p -> p.getSize().asInt())
      .sum();
    return i <= MAX_SIZE;
  }

  public List<Panel> getPanels() {
    return panels;
  }
}
