package de.drewing.comic.layout.model.custom;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringJoiner;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class PanelShotsFromJson {
  private String jsonString ="";
  private JsonShot[] jsonShots;

  private ObjectMapper mapper;
  private List<Shot> shots;

  PanelShotsFromJson () {
    init();
  }

  PanelShotsFromJson (final String jsonString) {
    this.jsonString = jsonString;
    init();
    mapper = new ObjectMapper();
    unmarshallJson();
    map();
  }

  private void init() {
    shots = new ArrayList<Shot>();
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
      addCustomShot(j.pattern, j.path, j.isRegex);
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

  public Shot addCustomShot(final String pattern,
                                   final String path,
                                   final boolean isRegex){
    final Shot s = new Shot(pattern, path, isRegex);
    addShot(s);
    return s;
  }

  public void addShot(final Shot shot){
    shots.add(shot);
  }

  public List<Shot> getShots() {
    return shots;
  }

  public String getShotsAsJson() {
    final StringJoiner sb = new StringJoiner(",");
    for(final Shot s : shots){
      sb.add("{\"isRegex\":"+s.isRegex +",\"pattern\":\""+s.searchString+ "\",\"path\":\""+s.path + "\"}");
    }
    return "[" + sb.toString() + "]";
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
