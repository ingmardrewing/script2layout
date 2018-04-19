package de.drewing.comic.layout.model;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class PanelShotsFromJson {
  private ObjectMapper mapper;
  private String jsonString;
  private JsonShot[] jsonShots;
  private List<Shot> shots;

  PanelShotsFromJson (final String jsonString) {
    this.jsonString = jsonString;
    mapper = new ObjectMapper();
  }

  void map() {
    try{
      jsonShots = mapper.readValue(jsonString, JsonShot[].class);
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }

  void createShots(){
    shots = new ArrayList<Shot>();
    for (final JsonShot j : jsonShots){
      shots.add(new Shot(Pattern.compile(j.pattern), j.path));
    }
  }

  String findInText(final String script) {
    for (final Shot s : shots) {
      if(s.matches(script)){
        return s.path;
      }
    }
    return null;
  }
}

class Shot {
  Pattern pattern;
  String path;

  Shot(final Pattern pattern, final String path) {
    this.pattern = pattern;
    this.path = path;
  }

  boolean matches(final String txt) {
    final Matcher m = pattern.matcher(txt);
    return m.find();
  }
}

class JsonShot {
  String pattern;
  String path;
}
