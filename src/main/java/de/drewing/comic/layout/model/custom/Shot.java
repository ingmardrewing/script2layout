package de.drewing.comic.layout.model.custom;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringJoiner;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Shot {
  public boolean isRegex = false;
  public Pattern pattern;
  public String searchString;
  public String path;

  public Shot(final String pattern, final String path, final boolean isRegex) {
    this.searchString = pattern;
    this.path = path;
    this.isRegex = isRegex;
    createPattern();
  }

  public void createPattern() {
    if(searchString != null) {
      this.pattern = Pattern.compile(searchString);
    }
  }

  boolean matches(final String txt) {
    if (isRegex) {
      final Matcher m = pattern.matcher(txt);
      return m.find();
    }
    return txt.contains(searchString);
  }
}

