package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;
import template.numbertheory.number.LongUtils;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: pprime
*/
public class pprime_main {
  QuickWriter out;
  int lower;
  int upper;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.out = out;
    lower = in.nextInt();
    upper = in.nextInt();
    for (int length = 1; length <= 8; ++length) {
      dfs(length, 0, 0);
    }
  }

  void dfs(int length, int depth, int value) {
    int other = length - 1 - depth;
    if (depth > other) {
      if (lower <= value && value <= upper && LongUtils.isPrime(value)) {
        out.println(value);
      }
      return;
    }
    int delta = depth == other ? IntUtils.POW10[depth] : IntUtils.POW10[depth] + IntUtils.POW10[other];
    for (int i = 0; i < 10; ++i) {
      dfs(length, depth + 1, value);
      value += delta;
    }
  }
}
