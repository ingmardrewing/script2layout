package de.drewing.comic.layout.control;

import de.drewing.comic.layout.model.Page;
import de.drewing.comic.layout.model.Panel;
import de.drewing.comic.layout.model.PanelSize;

import java.lang.Math;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Font;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.geom.AffineTransform;

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
    final int w = (int)calcWidth(p.getSize());
    final int h = (int)calcHeight(p.getSize());

		renderDummyImage(g,p,w,h);
		renderText(g, p);
    g.drawRect(panelPos.x, panelPos.y, w, h);
    updatePanelPos(w,h);
  }

	private void renderDummyImage(final Graphics2D g, final Panel p, final int panelWidth, final int panelHeight){
    if(p.hasImage()){
      final BufferedImage bim = p.getImage();
      final float prop_bim = (float) bim.getWidth() / (float) bim.getHeight() ;
      final float prop_pan = (float) panelWidth / (float) panelHeight;

      float w, h ;
      if (prop_bim > prop_pan) {
        // -> width of panel is max
        w = (float) panelWidth;
        h = w * (float) bim.getHeight() / (float) bim.getWidth();
      }
      else {
        // -> height of panel is max
        h = (float) panelHeight;
        w =  h * (float) bim.getWidth() / (float) bim.getHeight();
      }

      final BufferedImage img = Renderer.scale(bim, w, h);
      final int dx = (int) (panelWidth/2 -  w/2);
      final int dy = (int) (panelHeight/2 - h/2);
      g.drawImage(img,panelPos.x + dx, panelPos.y +dy, null);
    }
	}

	private void renderText(final Graphics2D g, final Panel p){
    g.setPaint(new Color(153, 153, 153));
    g.setFont(new Font("Arial", Font.PLAIN, 26));
    int ymove = 0;
    for ( String s : p.getScriptWithLineLength(20)){
      g.drawString(s, panelPos.x + 15, panelPos.y + 40 + ymove);
      ymove += 32;
    }
    g.setPaint(new Color(0, 0, 0));
	}

  private float calcWidth(final PanelSize size) {
    final float minWidth = STRIP_WIDTH/3 - GUTTER_WIDTH*2/3 ;
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

  private static BufferedImage scale(BufferedImage source, float dWidth, float dHeight) {
      if(source != null) {
        BufferedImage dest
          = new BufferedImage((int) dWidth, (int) dHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        final float scaleX = dWidth / (float) source.getWidth();
        final float scaleY =  dHeight/ (float) source.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        g.drawRenderedImage(source, at);
        return dest;
      }
      return null;
  }
}
