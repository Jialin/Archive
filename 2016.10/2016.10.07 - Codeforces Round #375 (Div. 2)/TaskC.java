package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n, m;
  int lower;
  boolean[] fixed;
  int[] a, cnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    a = in.nextInt(n);
    IntArrayUtils.update(a, -1);
    cnt = new int[m];
    lower = n / m;
    fixed = new boolean[n];
    for (int i = 0; i < n; ++i) if (a[i] < m && cnt[a[i]] < lower) {
      fixed[i] = true;
      ++cnt[a[i]];
    }
    int res = 0, pnt = 0;
    for (int i = 0; i < m; ++i) {
      for ( ; cnt[i] < lower; ++cnt[i]) {
        for ( ; fixed[pnt]; ++pnt) {}
        ++res;
        a[pnt++] = i;
      }
    }
    out.println(lower, res);
    IntArrayUtils.update(a, 1);
    out.println(a);
  }
}
