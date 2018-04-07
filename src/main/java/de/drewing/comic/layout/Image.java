package de.drewing.comic.layout;

public class Image {

  private String path;
  private String filename;

  Image (final String path, final String filename) { 
    this.path = path;
    this.filename = filename;
  }

  public void hello (){
    System.out.println("hello panel");
  }
}
