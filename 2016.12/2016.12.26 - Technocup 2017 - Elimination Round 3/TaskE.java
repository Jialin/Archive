package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  static int INF = 10000000;

  int n, k;
  int[] a;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    a = in.nextInt(n);
    int lower = 1;
    int upper = INF;
    int res = -1;
    while (lower <= upper) {
      int medium = (lower + upper) >> 1;
      if (calc(medium) >= k) {
        res = medium;
        lower = medium + 1;
      } else {
        upper = medium - 1;
      }
    }
    out.println(res);
  }

  long calc(int x) {
    long res = 0;
    for (int i = 0; i < n; ++i) {
      res += calcCnt(a[i], x);
    }
    return res;
  }

  int calcCnt(int n, int cost) {
    if (n < cost) return 0;
    int m = n / cost;
    int lv = Integer.highestOneBit(m), uv = lv << 1;
    return Math.max(lv, uv - (uv * cost - n));
  }
}
