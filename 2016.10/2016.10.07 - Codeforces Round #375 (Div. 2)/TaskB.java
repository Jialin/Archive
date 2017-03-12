package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  String[] s;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    in.nextInt();
    s = ("_" + in.next() + "_").split("\\(|\\)");
    out.printf("%d %d\n", calcLongestOutside(), calcNumberInside());
  }

  int calcLongestOutside() {
    int res = 0;
    for (int i = 0; i < s.length; i += 2) {
      for (String word : s[i].split("_")) {
        res = Math.max(res, word.length());
      }
    }
    return res;
  }

  int calcNumberInside() {
    int res = 0;
    for (int i = 1; i < s.length; i += 2) {
      for (String word : s[i].split("_")) if (word.length() > 0) {
        ++res;
      }
    }
    return res;
  }
}
