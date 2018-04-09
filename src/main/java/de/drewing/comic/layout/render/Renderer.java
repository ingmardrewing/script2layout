package de.drewing.comic.layout.render;

import de.drewing.comic.layout.read.Page;
import de.drewing.comic.layout.read.Panel;
import de.drewing.comic.layout.read.PanelSize;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Renderer {

  private static final float PAGE_WIDTH = 2200.0f;
  private static final float PAGE_HEIGHT = 3400.0f;

  private static final float VERTICAL_BORDER = 200.0f;
  private static final float HORIZONTAL_BORDER = 100.0f;

  private static final float STRIP_HEIGHT = 1000.0f;
  private static final float CENTER_STRIP_HEIGHT = 900.0f;
  private static final float STRIP_WIDTH = PAGE_WIDTH - 2* HORIZONTAL_BORDER;

  private static final float GUTTER_WIDTH = 50.0f;
  private static final float GUTTER_HEIGHT = 50.0f;

  private Point panelPos = new Point(
      (int)HORIZONTAL_BORDER, (int)VERTICAL_BORDER);
  private Page page;

  public Renderer(final Page p) {
    this.page = p;
  }

  public BufferedImage renderPage() {
    final BufferedImage image = getImage();
    final Graphics2D g = getGraphics(image);
    page.getPanels()
      .stream()
      .forEach(p -> renderPanel(p, g));
    return image;
  }

  private Graphics2D getGraphics(final BufferedImage image) {
    final Graphics2D g = image.createGraphics();
    g.setPaint(new Color(255, 255, 255));
    g.fillRect(0,0,(int)PAGE_WIDTH,(int)PAGE_HEIGHT);
    g.setStroke(new BasicStroke(2.0f));
    g.setPaint(new Color(0,0,0));
    return g;
  }

  private BufferedImage getImage() {
   return new BufferedImage(
         (int)PAGE_WIDTH,
         (int)PAGE_HEIGHT,
         BufferedImage.TYPE_INT_RGB);
  }

  private void renderPanel(final Panel p, final Graphics2D g){
    if(p.hasImage()){
      final BufferedImage img = p.getImage();
    }
    final int w = (int)calcWidth(p.getSize());
    final int h = (int)calcHeight(p.getSize());
    g.drawRect(panelPos.x, panelPos.y, w, h);
    updatePanelPos(w,h);
  }

  private float calcWidth(final PanelSize size) {
    final float minWidth = STRIP_WIDTH - 2 * (GUTTER_WIDTH + (STRIP_WIDTH/3));
    switch(size) {
      case ONE_THIRD:
        return minWidth;
      case HALF:
        return (STRIP_WIDTH - GUTTER_WIDTH) / 2;
      case TWO_THIRDS:
        return STRIP_WIDTH - (GUTTER_WIDTH + minWidth);
      default:
        return STRIP_WIDTH;
    }
  }

  private float calcHeight(final PanelSize size) {
    switch(size) {
      case DOUBLE_STRIP:
        return 2 * STRIP_HEIGHT - GUTTER_HEIGHT;
      case PAGE:
        return 3 * STRIP_HEIGHT - 2 * GUTTER_HEIGHT;
      default:
        return atMiddleStrip()
          ? STRIP_HEIGHT - 2 * GUTTER_HEIGHT
          : STRIP_HEIGHT;
    }
  }

  private void updatePanelPos(final int w, final int h) {
    int newPosX = panelPos.x + w + (int)GUTTER_WIDTH;
    int newPosY = panelPos.y;
    if(newPosX >= (int)HORIZONTAL_BORDER + (int)STRIP_WIDTH){
      newPosX = (int)HORIZONTAL_BORDER;
      newPosY += h + GUTTER_HEIGHT;
    }
    panelPos = new Point(newPosX, newPosY);
  }

  private boolean atMiddleStrip () {
    final int middleStripPosY = (int) (
       VERTICAL_BORDER + STRIP_HEIGHT + GUTTER_HEIGHT
      );
    return panelPos.y == middleStripPosY;
  }
}
