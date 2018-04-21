package de.drewing.comic.layout.model.custom;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class PanelShotsFromJsonTest {

    @Test
    public void panelShotFromJson_testStringContains() {
      final PanelShotsFromJson p = new PanelShotsFromJson(json());

      final String path = p.findInText("such a test");
      assertEquals("/path/to/image2", path);

      final String path2 = p.findInText("such a testtoast");
      assertEquals("/path/to/image", path2);
    }

    @Test
    public void panelShotFromJson_testRegex() {
      final PanelShotsFromJson p = new PanelShotsFromJson(json());

      final String path3 = p.findInText("such a teeeeesttoast");
      assertEquals("/path/to/image3", path3);
    }

    @Test
    public void panelShotFromJson_addCustomShot() {
      final PanelShotsFromJson p = new PanelShotsFromJson();
      p.addCustomShot("test.*", "/path/to/image", true);

      final String actual= p.getShotsAsJson();
      final String expected = "[{\"isRegex\":true,\"pattern\":\"test.*\",\"path\":\"/path/to/image\"}]";

      assertEquals(expected, actual);
    }


    private String json() {
      return "[{\"isRegex\":false,\"pattern\":\"testtoast\",\"path\":\"/path/to/image\"},{\"isRegex\":false,\"pattern\":\"test\",\"path\":\"/path/to/image2\"},{\"isRegex\":true,\"pattern\":\"tee.*st\",\"path\":\"/path/to/image3\"}]";
    }
}
