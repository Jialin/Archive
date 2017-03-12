package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  static char[] ACGT = new char[]{'A', 'C', 'G', 'T'};

  int n;
  char[] s;
  int[] cnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = new char[n];
    in.next(s);
    cnt = new int[4];
    for (int i = 0; i < n; ++i) if (s[i] == '?') {
      s[i] = getMin();
    }
    getMin();
    if ((n & 3) == 0 && IntArrayUtils.min(cnt) == (n >> 2)) {
      out.println(s);
    } else {
      out.println("===");
    }
  }

  char getMin() {
    Arrays.fill(cnt, 0);
    for (char c : s) if (c != '?') {
      ++cnt[getIndex(c)];
    }
    return ACGT[IntArrayUtils.find(cnt, IntArrayUtils.min(cnt))];
  }

  int getIndex(char c) {
    for (int i = 0; i < 4; ++i) if (c == ACGT[i]) {
      return i;
    }
    throw new IllegalArgumentException();
  }
}
