package de.drewing.comic.layout.model.custom;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringJoiner;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Shot {
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

