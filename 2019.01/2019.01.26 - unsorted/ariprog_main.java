package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: ariprog
*/
public class ariprog_main {
  final int MAXM = 250;
  final int MAX_RANGE = MAXM * MAXM << 1;
  final int MAX_ANSWER = 10000;

  boolean[] valid;
  IntArrayList values;
  int[] a, b;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    valid = new boolean[MAX_RANGE + 1];
    a = new int[MAX_ANSWER];
    b = new int[MAX_ANSWER];
    int n = in.nextInt();
    int m = in.nextInt();
    for (int p = 0; p <= m; ++p) {
      int pp = p * p;
      for (int q = 0; q <= m; ++q) {
        valid[pp + q * q] = true;
      }
    }
    int cnt = 0;
    for (int i = 0; i <= MAX_RANGE; ++i) if (valid[i]) {
      ++cnt;
    }
    values = new IntArrayList(cnt);
    for (int i = 0; i <= MAX_RANGE; ++i) if (valid[i]) {
      values.add(i);
    }
    cnt = 0;
    for (int i = 0; i < values.size; ++i) for (int j = i + 1; j < values.size; ++j) {
      int prev = (values.get(i) << 1) - values.get(j);
      if (prev >= 0 && valid[prev]) {
        continue;
      }
      int a = values.get(i);
      int b = values.get(j) - values.get(i);
      int step = 2;
      int v = a + (b << 1);
      for ( ; v < MAX_RANGE && valid[v]; v += b, ++step) {}
      if (step < n) {
        continue;
      }
      v = a;
      for (int remStep = n; remStep <= step; ++remStep, v += b) {
        this.a[cnt] = v;
        this.b[cnt++] = b;
      }
    }
    IntArrayUtils.sort(b, 0, cnt, a);
    for (int i = 0; i < cnt; ++i) {
      out.printf("%d %d\n", a[i], b[i]);
    }
    if (cnt == 0) {
      out.println("NONE");
    }
  }
}
