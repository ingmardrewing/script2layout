package de.drewing.comic.layout.model;

public final class CustomResources {

  private static PanelShotsFromJson psfj;

  public static void init(final String json) {
      psfj = new PanelShotsFromJson(json);
  }

  public static String findInText(final String txt) {
    if(psfj != null)
      return psfj.findInText(txt);
    return null;
  }
}
