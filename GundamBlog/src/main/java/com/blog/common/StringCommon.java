package com.blog.common;

import java.text.Normalizer;

public class StringCommon {
  public static String trimAllSpace(String inputString) {
    return inputString.replaceAll("\\s+", "");
  }

  public static String trimSpace(String inputString) {
    return inputString.replaceAll("\\s+", " ").trim();
  }

  public static String createPath(String inputString) {
    // Delete Punctuation
    String normalizedInput = Normalizer.normalize(inputString, Normalizer.Form.NFD);
    normalizedInput = normalizedInput.replaceAll("[^\\p{ASCII}]", "");
    //Replace all characters that are not letters or numbers with a "-"
    String path = normalizedInput.replaceAll("[^a-zA-Z0-9-]", "-").toLowerCase();
    // Remove consecutive "-"
    path = path.replaceAll("-+", "-");
    // Remove "-" at the beginning and end of the string
    path = path.replaceAll("^-|-$", "");
    return path.trim();
  }
}
