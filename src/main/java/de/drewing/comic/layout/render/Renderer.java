package de.drewing.comic.layout.render;

import de.drewing.comic.layout.Page;
import de.drewing.comic.layout.Panel;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public class Renderer {

  private static final float PAGE_WIDTH = 2200.0f;
  private static final float PAGE_HEIGHT = 3400.0f;

  private static final float VERTICAL_BORDER = 50.0f;
  private static final float HORIZONTAL_BORDER = 50.0f;

  private static final float STRIP_HEIGHT = 400.0f;
  private static final float STRIP_WIDTH = 2100.0f;

  private static final float GUTTER_WIDTH = 25.0f;
  private static final float GUTTER_HEIGHT = 25.0f;

  private Point panelPos = new Point(
      (int)HORIZONTAL_BORDER, (int)VERTICAL_BORDER);
  private Page page;
  private BufferedImage image;

  public Renderer(final Page p) {
    this.page = p;
    init();
  }

  private void init() {
   image = new BufferedImage(
         (int)PAGE_WIDTH,
         (int)PAGE_HEIGHT,
         BufferedImage.TYPE_INT_RGB);
  }

  public void renderPage() {
    Graphics2D g = image.createGraphics();
    g.setPaint(new Color(255, 255, 255));
    g.fillRect(0,0,(int)PAGE_WIDTH,(int)PAGE_HEIGHT);
    g.setStroke(new BasicStroke(2.0f));
    g.setPaint(new Color(0,0,0));
    for(final Panel pl : page.getPanels()) {
      renderPanel(pl, g);
    }
   }

  public BufferedImage getImage(){
    return image;
  }

  private void renderPanel(final Panel p, final Graphics2D g){
    final int w = (int)calcWidth(p.size);
    final int h = (int)calcHeight(p.size);
    g.drawRect( panelPos.x, panelPos.y, w, h);

    int newPosX = panelPos.x + w + (int)GUTTER_WIDTH;
    int newPosY = panelPos.y;
    if(newPosX >= (int)HORIZONTAL_BORDER + (int)STRIP_WIDTH){
     newPosX = (int)HORIZONTAL_BORDER;
     newPosY += h + GUTTER_HEIGHT;
    }
    panelPos = new Point(newPosX, newPosY);
  }

  public float calcWidth(final int size) {
    final float minWidth = STRIP_WIDTH - 2 * (GUTTER_WIDTH + (STRIP_WIDTH/3));
    switch(size) {
      case 2:
        return minWidth;
      case 3:
        return (STRIP_WIDTH - GUTTER_WIDTH) / 2;
      case 4:
        return STRIP_WIDTH - (GUTTER_WIDTH + minWidth);
      default:
        return STRIP_WIDTH;
    }
  }

  public float calcHeight(final int size) {
    switch(size) {
      case 12:
        return 2 * STRIP_HEIGHT + GUTTER_HEIGHT;
      case 18:
        return 3 * STRIP_HEIGHT + 2 * GUTTER_HEIGHT;
      default:
        return STRIP_HEIGHT;
    }
  }
}
