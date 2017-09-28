package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  static int MAXN = 100 + 1;

  int n;
  int[] cnt;
  int res1, res2;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    cnt = new int[MAXN];
    for (int i = 0; i < n; ++i) ++cnt[in.nextInt()];
    if (calc()) {
      out.println("YES");
      out.println(res1, res2);
    } else {
      out.println("NO");
    }
  }

  boolean calc() {
    for (int i = 1; i < MAXN; ++i) if (cnt[i] > 0) {
      for (int j = i + 1; j < MAXN; ++j) if (cnt[i] == cnt[j]) {
        res1 = i;
        res2 = j;
        return cnt[i] + cnt[j] == n;
      }
    }
    return false;
  }
}
