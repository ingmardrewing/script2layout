package de.drewing.comic.layout.render;

import de.drewing.comic.layout.Page;
import de.drewing.comic.layout.Panel;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Renderer {

  private static final int PAGE_WIDTH = 2200;
  private static final int PAGE_HEIGHT = 3400;

  private static final int VERTICAL_BORDER = 50;
  private static final int HORIZONTAL_BORDER = 50;

  private static final int STRIP_HEIGHT = 400;

  private static final int GUTTER_WIDTH = 25 ;
  private static final int GUTTER_HEIGHT = 25 ;

  private Page page;
  private BufferedImage image;

  public Renderer(final Page p) {
    this.page = p;
    init();
  }

  private void init() {
   image = new BufferedImage(
         PAGE_WIDTH,
         PAGE_HEIGHT,
         BufferedImage.TYPE_INT_RGB);
  }

  public void renderPage() {
    Graphics2D g = image.createGraphics();
    for(final Panel pl : page.getPanels()) {
      renderPanel(pl, g);
    }
   }

  public BufferedImage getImage(){
    return image;
  }

  public void renderPanel(final Panel p, final Graphics2D g){
     g.setPaint(new Color(255, 255, 255));
     g.fillRect(0,0,PAGE_WIDTH,PAGE_HEIGHT);
     g.setStroke(new BasicStroke(2.0f));
     g.setPaint(new Color(0,0,0));
     g.drawRect(
         HORIZONTAL_BORDER,
          VERTICAL_BORDER,
          PAGE_WIDTH- 2 * HORIZONTAL_BORDER,
          PAGE_HEIGHT- 2 * VERTICAL_BORDER);
  }
}
