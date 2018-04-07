package de.drewing.comic.layout.render;

import de.drewing.comic.layout.Page;
import de.drewing.comic.layout.Panel;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Renderer {

  private static final int PAGE_WIDTH = 2200;
  private static final int PAGE_HEIGHT = 3400;

  private static final int VERTICAL_BORDER = 50;
  private static final int HORIZONTAL_BORDER = 50;

  private static final int STRIP_HEIGHT = 400;

  private static final int GUTTER_WIDTH = 25 ;
  private static final int GUTTER_HEIGHT = 25 ;


  public void render(final Page p) {
     BufferedImage img = new BufferedImage(PAGE_WIDTH,PAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);

     Graphics2D g = img.createGraphics();

     g.setPaint(new Color(255, 255, 255));
     g.fillRect(0,0,PAGE_WIDTH,PAGE_HEIGHT);

     g.setStroke(new BasicStroke(2.0f));
     g.setPaint(new Color(0,0,0));
     g.drawRect(
         HORIZONTAL_BORDER,
          VERTICAL_BORDER,
          PAGE_WIDTH- 2 * HORIZONTAL_BORDER,
          PAGE_HEIGHT- 2 * VERTICAL_BORDER);

    File f = new File("image.png");
    try {
      ImageIO.write(img, "png", f);
    }
    catch(IOException e) {
      e.printStackTrace();
    }

  }
}
