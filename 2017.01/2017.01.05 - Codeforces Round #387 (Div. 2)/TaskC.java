package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n;
  int[] freeTime;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    freeTime = new int[n];
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      int t = in.nextInt();
      int k = in.nextInt();
      int d = in.nextInt();
      int freeCnt = 0;
      for (int i = 0; i < n; ++i) if (freeTime[i] <= t) {
        ++freeCnt;
      }
      if (freeCnt < k) {
        out.println(-1);
        continue;
      }
      int res = 0;
      for (int i = 0; i < n && k > 0; ++i) if (freeTime[i] <= t) {
        --k;
        res += i + 1;
        freeTime[i] = t + d;
      }
      out.println(res);
    }
  }
}
