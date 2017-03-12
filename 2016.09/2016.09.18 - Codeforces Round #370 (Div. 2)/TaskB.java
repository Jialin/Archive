package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.abs;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int x = 0, y = 0;
    for (char c : in.next().toCharArray()) {
      switch (c) {
        case 'L': --y; break;
        case 'R': ++y; break;
        case 'U': --x; break;
        case 'D': ++x; break;
      }
    }
    int diff = abs(x) + abs(y);
    out.println((diff & 1) > 0 ? -1 : diff >> 1);
  }
}
