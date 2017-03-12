package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n, k;
  char[] s;

  int posG, posT, delta;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    s = new char[n];
    in.next(s);
    posG = calc('G');
    posT = calc('T');
    delta = posG < posT ? k : -k;
    out.println(yes() ? "YES" : "NO");
  }

  int calc(char c) {
    for (int i = 0; i < n; ++i) {
      if (s[i] == c) {
        return i;
      }
    }
    throw new IllegalArgumentException();
  }

  boolean yes() {
    for (posG += delta; 0 <= posG && posG < n; posG += delta) {
      if (s[posG] == '#') return false;
      if (posG == posT) return true;
    }
    return false;
  }
}
