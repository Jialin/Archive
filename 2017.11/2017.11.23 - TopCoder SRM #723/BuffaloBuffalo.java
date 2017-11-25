package main;

import java.util.regex.Pattern;

public class BuffaloBuffalo {
  static String YES = "Good";
  static String NO = "Not good";

  public String check(String s) {
    return Pattern.matches("buffalo( buffalo)*", s) ? YES : NO;
  }
}
