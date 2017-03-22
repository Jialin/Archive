package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskE {
  final int INF = Integer.MAX_VALUE >> 1;

  int n;
  char[] s;
  int vCnt, kCnt, oCnt;
  IntArrayList vLst, kLst, oLst;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = new char[n];
    in.next(s);
    vLst = new IntArrayList(n);
    kLst = new IntArrayList(n);
    oLst = new IntArrayList(n);
    for (int i = 0; i < n; ++i) {
      switch (s[i]) {
        case 'V':
          vLst.add(i);
          break;
        case 'K':
          kLst.add(i);
          break;
        default:
          oLst.add(i);
          break;
      }
    }
    vCnt = vLst.size;
    kCnt = kLst.size;
    oCnt = oLst.size;
    out.println(calc());
  }

  int[][][][] dp;

  int calc() {
    int t = 0;
    dp = new int[2][2][vCnt + 1][kCnt + 1];
    clear(t);
    dp[t][0][0][0] = 0;
    for (int i = 0; i < n; ++i) {
      clear(t ^ 1);
      for (int danger = 0; danger < 2; ++danger) for (int v = 0; v <= vCnt; ++v) for (int k = 0; k <= kCnt; ++k) {
        int value = dp[t][danger][v][k];
        if (value == INF) continue;
        int o = i - v - k;
        if (o < 0 || o > oCnt) continue;
        // v
        if (v < vCnt) {
          update(t ^ 1, 1, v + 1, k, value + calcCost(v, k, o, vLst.get(v)));
        }
        // k
        if (danger == 0 && k < kCnt) {
          update(t ^ 1, 0, v, k + 1, value + calcCost(v, k, o, kLst.get(k)));
        }
        // o
        if (o < oCnt) {
          update(t ^ 1, 0, v, k, value + calcCost(v, k, o, oLst.get(o)));
        }
      }
      t ^= 1;
    }
    return Math.min(dp[t][0][vCnt][kCnt], dp[t][1][vCnt][kCnt]);
  }

  void clear(int t) {
    for (int danger = 0; danger < 2; ++danger) for (int v = 0; v <= vCnt; ++v) {
      Arrays.fill(dp[t][danger][v], INF);
    }
  }

  void update(int t, int danger, int v, int k, int value) {
    dp[t][danger][v][k] = Math.min(dp[t][danger][v][k], value);
  }

  int calcCost(int v, int k, int o, int idx) {
    return Math.max(vLst.lowerBound(idx) - v, 0)
        + Math.max(kLst.lowerBound(idx) - k, 0)
        + Math.max(oLst.lowerBound(idx) - o, 0);
  }
}
