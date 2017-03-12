package main;

import template.array.LongArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

import static java.lang.Math.min;
import static java.util.Arrays.fill;
import static template.numbertheory.LongUtils.divisors;
import static template.numbertheory.LongUtils.factorize;

public class TaskE {

  private static final int MAX_PRIME_FACTOR = 30;
  private static final int MAX_EXP = 50;
  private static final int MAX_DIVISORS = 10000;

  int n;
  long k;

  int pCnt;
  long[] pk;
  int[] rk;

  int divisorCnt;
  long[] divisors;

  long[] a;
  int[][] aJump;

  int[][] minCnt;
  long[][] minSum;
  int[][] fromSum;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextLong();
    a = new long[n];
    in.nextLong(n, a);

    if (k == 1) {
      out.println('1');
      long minA = LongArrayUtils.min(a);
      for (int i = 0; i < n; ++i) if (minA == a[i]) {
        out.println(i + 1);
        return;
      }
    }

    pk = new long[MAX_PRIME_FACTOR];
    rk = new int[MAX_PRIME_FACTOR];
    pCnt = factorize(k, pk, rk);
    divisors = new long[MAX_DIVISORS];
    divisorCnt = divisors(k, divisors);

    calcJump();
    calc();
    if (minSum[n][0] == Long.MAX_VALUE) {
      out.println("-1");
    } else {
      IntArrayList lst = new IntArrayList(n);
      int j = 0;
      for (int i = n; i > 0; --i) {
        if (fromSum[i][j] != j) {
          j = fromSum[i][j];
          lst.add(i);
        }
      }
      out.println(lst.size());
      for (int i = 0; i < lst.size(); ++i) {
        out.print(lst.get(i));
        out.print(i + 1 == lst.size() ? '\n' : ' ');
      }
    }
  }

  void calcJump() {
    int[][] divisorJump = new int[divisorCnt][MAX_EXP];
    int[] expCnt = new int[divisorCnt];
    aJump = new int[n][divisorCnt];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < divisorCnt; ++j) {
        aJump[i][j] = j;
      }
    }
    for (int pIdx = 0; pIdx < pCnt; ++pIdx) {
      long p = pk[pIdx];
      for (int i = 0; i < divisorCnt; ++i) {
        expCnt[i] = 0;
        for (long d = divisors[i]; d % p == 0; ) {
          d /= p;
          divisorJump[i][expCnt[i]++] = Arrays.binarySearch(divisors, 0, divisorCnt, d);
        }
      }
      for (int i = 0; i < n; ++i) {
        int cnt = 0;
        long remA = a[i];
        for ( ; remA % p == 0; remA /= p, ++cnt) {}
        if (cnt == 0) continue;
        for (int j = 0; j < divisorCnt; ++j) if (expCnt[j] > 0) {
          aJump[i][j] = divisorJump[aJump[i][j]][min(expCnt[j], cnt) - 1];
        }
      }
    }
  }

  void calc() {
    minCnt = new int[n + 1][divisorCnt];
    minSum = new long[n + 1][divisorCnt];
    fromSum = new int[n + 1][divisorCnt];
    for (int i = 0; i <= n; ++i) {
      fill(minCnt[i], Integer.MAX_VALUE);
      fill(minSum[i], Long.MAX_VALUE);
      fill(fromSum[i], -1);
    }
    minCnt[0][divisorCnt - 1] = 0;
    minSum[0][divisorCnt - 1] = 0;
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < divisorCnt; ++j) {
        if (minCnt[i][j] == Integer.MAX_VALUE) continue;
        int nxtJ = aJump[i][j];
        update(i + 1, j, j, minCnt[i][j], minSum[i][j]);
        update(i + 1, nxtJ, j, minCnt[i][j] + 1, minSum[i][j] + a[i]);
      }
    }
  }

  void update(int newI, int newJ, int j, int cnt, long sum) {
    if (minCnt[newI][newJ] > cnt
        || (minCnt[newI][newJ] == cnt && minSum[newI][newJ] > sum)) {
      minCnt[newI][newJ] = cnt;
      minSum[newI][newJ] = sum;
      fromSum[newI][newJ] = j;
    }
  }
}
