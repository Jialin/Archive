package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;
import java.util.Random;

public class beach_per {
  static int MAXR = 2000 + 1;
  static int MAXR2 = MAXR << 1;

  int n, m, m2r;
  int[] r;
  int[][] cnt;
  int[] product2;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
//    test();
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    m = in.nextInt() - 1;
    r = in.nextInt(n);
    out.printf("Case #%d: %d\n", taskIdx, calc());
  }

  int calc() {
    if (n == 1) {
      return m + 1;
    }
    m2r = m;
    for (int i = 0; i < n; ++i) {
      m2r -= r[i] << 1;
    }
    initProduct2();
    int res = IntUtils.mul(IntUtils.mul(product2[0], m2r + n - 1), m2r + n);
//System.out.printf("+(%d*%d*%d) res:%d\n", product2[0], m2r + n - 1, m2r + n, res);
    initCnt();
    for (int i = 0; i < MAXR; ++i) for (int j = 0; j < MAXR; ++j) if (i > 0 && j > 0 && cnt[i][j] > 0) {
//System.out.printf("i:%d j:%d +(%d*%d)\n", i, j, product2[i + j], cnt[i][j]);
      res = IntUtils.add(
          res,
          IntUtils.mul(product2[i + j], cnt[i][j]));
    }
    for (int i = 1; i < MAXR; ++i) {
      int curCnt = 0;
      for (int j = 0; j < n; ++j) if (i <= r[j]) {
        ++curCnt;
      }
      int curRes = IntUtils.mul(IntUtils.mul(product2[i], m2r + i + n - 1), curCnt);
      res = IntUtils.add(
          res,
          IntUtils.add(curRes, curRes));
    }
    return res;
  }

  void initCnt() {
    cnt = new int[MAXR][MAXR];
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (i != j) {
      ++cnt[r[i]][r[j]];
    }
    for (int i = MAXR - 1; i >= 0; --i) for (int j = MAXR - 1; j >= 0; --j) {
      if (i + 1 < MAXR) cnt[i][j] += cnt[i + 1][j];
      if (j + 1 < MAXR) cnt[i][j] += cnt[i][j + 1];
      if (i + 1 < MAXR && j + 1 < MAXR) cnt[i][j] -= cnt[i + 1][j + 1];
    }
  }

  void initProduct2() {
    product2 = new int[MAXR2];
    boolean first = true;
    for (int delta = 0; delta < MAXR2; ++delta) {
      if (m2r + delta < 0) {
        product2[delta] = 0;
      } else if (first) {
        first = false;
        product2[delta] = 1;
        for (int i = 1; i <= n - 2; ++i) {
          product2[delta] = IntUtils.mul(product2[delta], m2r + delta + i);
        }
      } else if (product2[delta - 1] > 0) {
//System.err.printf("product2:%d inverse(%d)\n", product2[delta - 1], m2r + delta);
        product2[delta] = IntUtils.mul(IntUtils.mul(
            product2[delta - 1],
            IntUtils.inverse(m2r + delta)),
            m2r + delta + n - 2);
      } else {
        product2[delta] = 0;
      }
    }
//System.out.printf("m2r:%d\n", m2r);
//for (int i = 0; i < 10; ++i) System.out.printf("%d ", product2[i]); System.out.println();
  }

  int resSlow;
  boolean[] used;
  int[] solSlow;
  int[] rSlow;

  int calcSlow() {
    used = new boolean[n];
    solSlow = new int[n];
    rSlow = new int[n];
    resSlow = 0;
    dfsSlow(0, 0);
    return resSlow;
  }

  void dfsSlow(int depth, int last) {
    if (depth == n) {
      ++resSlow;
//      System.out.printf("%d: pos:%s r:%s\n", resSlow, Arrays.toString(solSlow), Arrays.toString(rSlow));
      return;
    }
    for (int i = 0; i < n; ++i) if (!used[i]) {
      used[i] = true;
      rSlow[depth] = r[i];
      for (int j = depth > 0 ? last + r[i] : 0; j <= m; ++j) {
        solSlow[depth] = j;
        dfsSlow(depth + 1, j + r[i]);
      }
      used[i] = false;
    }
  }

  void test() {
    int MAXTASK = 1000;
    int MAXN = 6, MAXM = 20, MAXR = 10;
    Random RANDOM = new Random(1000000007);
    for (int task = 0; task < MAXTASK; ++task) {
System.out.printf("test task #%d\n", task);
      n = RANDOM.nextInt(MAXN) + 1;
      m = RANDOM.nextInt(MAXM) + 1;
      r = new int[n];
      for (int i = 0; i < n; ++i) {
        r[i] = RANDOM.nextInt(MAXR) + 1;
      }
      int expect = calcSlow();
      int actual = calc();
      if (expect != actual) {
        System.out.printf("expect:%d vs actual:%d\n", expect, actual);
        System.out.printf("n:%d m:%d r:%s\n", n, m, Arrays.toString(r));
        break;
      }
    }
  }
}
