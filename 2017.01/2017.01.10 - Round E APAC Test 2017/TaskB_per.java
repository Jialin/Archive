package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB_per {
  long n;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextLong();
    out.printf("Case #%d: %d\n", taskIdx, calc());
  }

  static int MAXN = 1000000 + 1;
  static long INF = 1000000000000000000L;

  static long[] value = new long[MAXN];

  long calc() {
    long res = n - 1;
    long res3 = possible3();
    if (res3 > 1) {
      res = res3;
    }
    for (int x = 2; x < MAXN; ++x) {
      value[x] = (x + 1L) * x + 1;
    }
    for (int step = 4; step < 100; ++step) {
      for (int x = 2; x < MAXN; ++x) {
        if (value[x] > INF / x) break;
        value[x] = (value[x] * x) + 1;
        if (value[x] == n) {
          res = x;
        }
      }
    }
    return res;
  }

  long possible3() {
    long n43 = (n << 2) - 3;
    long res = 1;
    long lower = 2, upper = n43;
    while (lower <= upper) {
      long medium = (lower + upper) >> 1;
      if (medium > n43 / medium || medium * medium > n43) {
        upper = medium - 1;
      } else {
        res = medium;
        lower = medium + 1;
      }
    }
    return res * res == n43 && ((res - 1) & 1) == 0 ? ((res - 1) >> 1) : -1;
  }

  long calcSlow() {
    int[] slowCnt = new int[1001];
    int[] slowRes = new int[1001];
    for (int base = 2; base <= 1000; ++base) if (base != 10) {
      int v = 1;
      for (int step = 2; ; ++step) {
        v = v * base + 1;
        if (v > 1000) break;
        if (slowCnt[v] < step) {
          slowCnt[v] = step;
          slowRes[v] = base;
        }
      }
    }
    return slowRes[(int) n];
  }
}
