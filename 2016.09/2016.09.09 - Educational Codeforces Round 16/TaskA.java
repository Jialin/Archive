package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    out.println(calc(in.next()));
  }

  int calc(String cd) {
    int c = cd.charAt(0) - 'a';
    int d = cd.charAt(1) - '1';
    if ((c == 0 || c == 7) && (d == 0 || d == 7)) return 3;
    if ((c == 0 || c == 7) || (d == 0 || d == 7)) return 5;
    return 8;
  }
}
