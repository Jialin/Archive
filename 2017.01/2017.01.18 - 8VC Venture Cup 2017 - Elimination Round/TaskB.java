package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Set;
import java.util.TreeSet;

public class TaskB {
  int[] n;
  Set<String> wordSet;
  int commonCnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt(2);
    wordSet = new TreeSet<>();
    for (int i = 0; i < n[0]; ++i) {
      wordSet.add(in.next());
    }
    for (int i = 0; i < n[1]; ++i) {
      String word = in.next();
      if (wordSet.contains(word)) {
        ++commonCnt;
      }
    }
    IntArrayUtils.update(n, -commonCnt);
    out.println(calc() ? "YES" : "NO");
  }

  boolean calc() {
    for (int t = 0; ; t ^= 1) {
      if (n[t] + commonCnt == 0) {
        return t > 0;
      }
      if (commonCnt > 0) {
        --commonCnt;
      } else {
        --n[t];
      }
    }
  }
}
