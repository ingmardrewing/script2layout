package de.drewing.comic.layout;

import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class Layout{

  public static void main(String[] args) {
    List<String> scriptLines = new ArrayList<String>();

    try {
      FileReader f = new FileReader(args[0]);
      BufferedReader b = new BufferedReader(f);

      String line;

      while((line = b.readLine()) != null){
        scriptLines.add(line);
      }
    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }

    if (scriptLines.size() > 0) {
      String script = "";
      for (String s : scriptLines){
        script += s + "\n";
      }
      final Book b = new Book(script);
    }
  }

}
