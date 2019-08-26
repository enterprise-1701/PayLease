package com.paylease.app.qa.framework.utility.difftool;

import com.paylease.app.qa.framework.Logger;
import difflib.DiffUtils;
import difflib.Patch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class TextFileDiff {

  /**
   * Check if two text files are equal.
   *
   * @param filePath1 path of first file relative to PayLease directory
   * @param filePath2 path of second file relative to PayLease directory
   * @return True or False
   */
  public boolean isEqual(String filePath1, String filePath2) {
    List<String> original = fileToLines(filePath1);
    List<String> revised = fileToLines(filePath2);

    Patch patch = DiffUtils.diff(original, revised);

    if (patch.getDeltas().isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  /** Writes each line read from the given file to a List of strings. */
  private List<String> fileToLines(String filename) {
    List<String> lines = new LinkedList<>();
    String line = "";

    try {
      BufferedReader in = new BufferedReader(new FileReader(filename));
      while ((line = in.readLine()) != null) {
        lines.add(line);
      }
      in.close();
    } catch (Exception e) {
      Logger.trace(e.toString());
    }

    return lines;
  }
}
