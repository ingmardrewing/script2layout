package de.drewing.comic.layout.model;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class PanelShotsFromJsonTest {

    @Test
    public void panelShotFromJson_unmarshallsJson() {
      final PanelShotsFromJson p = new PanelShotsFromJson(json());

      final String path = p.findInText("such a test");
      assertEquals("/path/to/image2", path);

      final String path2 = p.findInText("such a testtoast");
      assertEquals("/path/to/image", path2);

      final String path3 = p.findInText("such a teeeeesttoast");
      assertEquals("/path/to/image3", path3);
    }

    private String json() {
      return "[{\"pattern\":\"testtoast\",\"path\":\"/path/to/image\"},{\"pattern\":\"test\",\"path\":\"/path/to/image2\"},{\"pattern\":\"tee.*st\",\"path\":\"/path/to/image3\"}]";
    }
}
