package de.drewing.comic.layout.model;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class PanelShotsFromJson {
  private String jsonString ="";
  private JsonShot[] jsonShots;

  private ObjectMapper mapper;
  private List<Shot> shots;

  PanelShotsFromJson (final String jsonString) {
    this.jsonString = jsonString;
    init();
  }

  private void init() {
    mapper = new ObjectMapper();
    shots = new ArrayList<Shot>();
    unmarshallJson();
    map();
  }

  private void unmarshallJson() {
    try{
      jsonShots = mapper.readValue(jsonString, JsonShot[].class);
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }

  private void map(){
    for (final JsonShot j : jsonShots){
      shots.add(new Shot(j.pattern, j.path, j.isRegex));
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
  boolean isRegex = false;
  Pattern pattern;
  String searchString;
  String path;

  Shot(final String pattern, final String path, final boolean isRegex) {
    this.pattern = Pattern.compile(pattern);
    this.searchString = pattern;
    this.path = path;
    this.isRegex = isRegex;
  }

  boolean matches(final String txt) {
    if (isRegex) {
      final Matcher m = pattern.matcher(txt);
      return m.find();
    }
    return txt.contains(searchString);
  }
}

class JsonShot {
  boolean isRegex;
  String pattern;
  String path;

  public void setPattern(final String pattern){
    this.pattern = pattern;
  }

  public String getPattern(){
    return pattern;
  }

  public void setPath(final String path){
    this.path= path;
  }

  public String getPath(){
    return path;
  }

  public void setIsRegex(final boolean isRegex){
    this.isRegex = isRegex;
  }

  public boolean getIsRegex(){
    return isRegex;
  }
}
