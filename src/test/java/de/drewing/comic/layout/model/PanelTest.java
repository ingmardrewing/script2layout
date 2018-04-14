package de.drewing.comic.layout.model;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class PanelTest {

    @Test
    public void getScriptWithSeparatedDialog_extracts_panel_description() {
      final Panel p = new Panel(givenScript());
      final List<String> s = p.getScriptWithSeparatedDialog();
      assertEquals(s.get(0), "Extreme long shot, strip size, city Modified Vehicle (back part has been replaced with parts from the ad agency) seen from above.\n");
    }

    @Test
    public void getScriptWithSeparatedDialog_identifies_character_names() {
      final Panel p = new Panel(givenScript());
      final List<String> s = p.getScriptWithSeparatedDialog();
      assertEquals(s.get(2),"ADA");
      assertEquals(s.get(5),"BRAM");
    }

    @Test
    public void getScriptWithSeparatedDialog_extracts_dialogue() {
      final Panel p = new Panel(givenScript());
      final List<String> s = p.getScriptWithSeparatedDialog();
      assertEquals("I still don‘t get it - why are they doing this?\n", s.get(3));
      assertEquals("Inside the reactor of the ad agency they get fifteen minutes of flame. Maybe that's why.\n", s.get(6));
    }

    @Test
    public void getScriptWithLineLength_returns_separated_lines_with_max_50_characters() {
      final Panel p = new Panel(givenScript());
      final List<String> s = p.getScriptWithLineLength(50);
      assertEquals("Extreme long shot, strip size, city Modified Vehicle ", s.get(0));
      assertEquals("agency) seen from above.\n", s.get(2));
      assertEquals("ADA", s.get(4));
      assertEquals("I still don‘t get it - why are they doing this?\n", s.get(5));
    }

    private String givenScript() {
      return "Extreme long shot, strip size, city Modified Vehicle (back part has been replaced with parts from the ad agency) seen from above.\nADA\nI still don‘t get it - why are they doing this?\nBRAM\nInside the reactor of the ad agency they get fifteen minutes of flame. Maybe that's why.";
    }
}
