package de.drewing.comic.layout.model;

import java.util.ArrayList;
import java.util.List;

public class PanelSequence {
  private List<Panel> panels;

  PanelSequence(){
    init();
  }

  private void init() {
    panels = new ArrayList<Panel>();
  }

  void add(Panel panel){
    panels.add(panel);
  }

  Panel getPanelBefore(Panel panel) {
    final int i = panels.indexOf(panel);
    if(i > 0){
      return panels.get(i-1);
    }
    return null;
  }

  Panel getPanelAfter(Panel panel) {
    final int i = panels.indexOf(panel);
    if(i > -1 && i < panels.size() -1){
      return panels.get(i);
    }
    return null;
  }

  public void checkDoubles() {
    Panel prev = null;
    for(final Panel p : panels) {
      if (prev == null) {
        prev = p;
        continue;
      }
      if(prev.getShot() == p.getShot()) {
          System.out.println(p.getScript());
      }
      prev = p;
    }
  }

}
