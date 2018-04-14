package de.drewing.comic.layout.model;

import java.util.List;

public class PanelSizeInferer {
  private final List<Panel> panels;

  PanelSizeInferer(final List<Panel> panels) {
    this.panels = panels;
  }

  public List<Panel> getPanels() {
    return panels;
  }

  void checkPanelsAndGenerateSizesIfNotSet() {
    if (containsPanelsWithUndefinedSize()) {
      switch(panels.size()) {
        case 1:
          panels.get(0).setSize(PanelSize.PAGE);
          break;
        case 2:
          panels.get(0).setSize(PanelSize.DOUBLE_STRIP);
          panels.get(1).setSize(PanelSize.STRIP);
          break;
        case 3:
          panels.get(0).setSize(PanelSize.STRIP);
          panels.get(1).setSize(PanelSize.STRIP);
          panels.get(2).setSize(PanelSize.STRIP);
          break;
        case 4:
          panels.get(0).setSize(PanelSize.STRIP);
          panels.get(1).setSize(PanelSize.HALF);
          panels.get(2).setSize(PanelSize.HALF);
          panels.get(3).setSize(PanelSize.STRIP);
          break;
        case 5:
          panels.get(0).setSize(PanelSize.STRIP);
          panels.get(1).setSize(PanelSize.HALF);
          panels.get(2).setSize(PanelSize.HALF);
          panels.get(3).setSize(PanelSize.HALF);
          panels.get(4).setSize(PanelSize.HALF);
          break;
        case 6:
          panels.get(0).setSize(PanelSize.HALF);
          panels.get(1).setSize(PanelSize.HALF);
          panels.get(2).setSize(PanelSize.HALF);
          panels.get(3).setSize(PanelSize.HALF);
          panels.get(4).setSize(PanelSize.HALF);
          panels.get(5).setSize(PanelSize.HALF);
          break;
        case 7:
          panels.get(0).setSize(PanelSize.HALF);
          panels.get(1).setSize(PanelSize.HALF);
          panels.get(2).setSize(PanelSize.ONE_THIRD);
          panels.get(3).setSize(PanelSize.ONE_THIRD);
          panels.get(4).setSize(PanelSize.ONE_THIRD);
          panels.get(5).setSize(PanelSize.HALF);
          panels.get(6).setSize(PanelSize.HALF);
          break;
        case 8:
          panels.get(0).setSize(PanelSize.HALF);
          panels.get(1).setSize(PanelSize.HALF);
          panels.get(2).setSize(PanelSize.ONE_THIRD);
          panels.get(3).setSize(PanelSize.ONE_THIRD);
          panels.get(4).setSize(PanelSize.ONE_THIRD);
          panels.get(5).setSize(PanelSize.ONE_THIRD);
          panels.get(6).setSize(PanelSize.ONE_THIRD);
          panels.get(7).setSize(PanelSize.ONE_THIRD);
          break;
        case 9:
          panels.get(0).setSize(PanelSize.ONE_THIRD);
          panels.get(1).setSize(PanelSize.ONE_THIRD);
          panels.get(2).setSize(PanelSize.ONE_THIRD);
          panels.get(3).setSize(PanelSize.ONE_THIRD);
          panels.get(4).setSize(PanelSize.ONE_THIRD);
          panels.get(5).setSize(PanelSize.ONE_THIRD);
          panels.get(6).setSize(PanelSize.ONE_THIRD);
          panels.get(7).setSize(PanelSize.ONE_THIRD);
          panels.get(8).setSize(PanelSize.ONE_THIRD);
          break;
        default: break;
      }
    }
  }

  private boolean containsPanelsWithUndefinedSize() {
    for(final Panel p:panels){
      if(!p.hasSize()){
        return true;
      }
    }
    return false;
  }

}
