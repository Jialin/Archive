package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.util.Arrays.fill;

public class TaskB {
  private static final int MAXN = 5000 + 10;
  static final long MAX_VALUE = Long.MAX_VALUE;

  int n, s, e;
  int[] x, a, b, c, d;
  long[][][] dp;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = in.nextInt() - 1;
    e = in.nextInt() - 1;
    x = in.nextInt(n);
    a = in.nextInt(n);
    b = in.nextInt(n);
    c = in.nextInt(n);
    d = in.nextInt(n);
    out.println(calc());
  }

  long calc() {
    dp = new long[2][2][MAXN];
    int t = 0;
    clear(t);
    if (s == 0) {
      dp[t][0][1] = d[0];
    } else if (e == 0) {
      dp[t][1][1] = b[0];
    } else {
      dp[t][0][2] = b[0] + d[0];
    }
    for (int i = 1; i < n; ++i) {
      clear(t ^ 1);
      for (int dir = 0; dir < 2; ++dir) {
        long delta = x[i] - x[i - 1];
        long sumDelta = delta;
        for (int cnt = 1; cnt + 2 < MAXN; ++cnt, sumDelta += delta) if (dp[t][dir][cnt] != MAX_VALUE) {
          if (i == s) {
            if ((cnt & 1) == 0 || dir == 1) {
              update(t ^ 1, 0, cnt - 1, dp[t][dir][cnt] + sumDelta + c[i]);
              update(t ^ 1, 0, cnt + 1, dp[t][dir][cnt] + sumDelta + d[i]);
            }
          } else if (i == e) {
            if ((cnt & 1) == 0 || dir == 0) {
              update(t ^ 1, 1, cnt - 1, dp[t][dir][cnt] + sumDelta + a[i]);
              update(t ^ 1, 1, cnt + 1, dp[t][dir][cnt] + sumDelta + b[i]);
            }
          } else {
            if (cnt > 1) update(t ^ 1, dir, cnt - 2, dp[t][dir][cnt] + sumDelta + a[i] + c[i]);
            update(t ^ 1, dir, cnt + 2, dp[t][dir][cnt] + sumDelta + b[i] + d[i]);
            if (cnt > 1 || dir == 0) update(t ^ 1, dir, cnt, dp[t][dir][cnt] + sumDelta + a[i] + d[i]);
            if (cnt > 1 || dir == 1) update(t ^ 1, dir, cnt, dp[t][dir][cnt] + sumDelta + b[i] + c[i]);
          }
        }
      }
      t ^= 1;
    }
    return dp[t][0][0];
  }

  void clear(int t) {
    fill(dp[t][0], MAX_VALUE);
    fill(dp[t][1], MAX_VALUE);
  }

  void update(int t, int dir, int cnt, long value) {
    if ((cnt & 1) == 0) {
      dir = 0;
    }
    if (dp[t][dir][cnt] > value) {
      dp[t][dir][cnt] = value;
    }
  }
}
