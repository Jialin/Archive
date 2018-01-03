package main;

import template.collections.eertree.AbstractEertree;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskE {
  static int MAXL = 500000;

  char[] s, t;
  int[] values;
  FactorizationEertree eertree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = new char[MAXL];
    t = new char[MAXL];
    int n = in.next(s), n2 = n << 1;
    in.next(t);
    values = new int[n2];
    for (int i = 0, j = 0; i < n; ++i) {
      values[j++] = s[i] - 'a';
      values[j++] = t[i] - 'a';
    }
    eertree = new FactorizationEertree(26, n2, false);
    eertree.calc(n2, values);
    int res = eertree.dp[n2];
    if (res > n) {
      out.println(-1);
      return;
    }
    out.println(res);
    for (int i = n2; i > 0; ) {
      int j = eertree.dpFrom[i];
      if (i - j > 2) {
        out.println((j >> 1) + 1, i >> 1);
      }
      i = j;
    }
  }

  public class FactorizationEertree extends AbstractEertree {

    private static final int INF = Integer.MAX_VALUE;

    /** {@code diff[u] = length[u] - length[link[u]]} */
    private int[] diff;
    /** The longest suffix-palindrome of {@code u} having the difference unequal to {@code diff[u]}. */
    private int[] seriesLink;

    public int[] dp;
    public int[] dpFrom;

    /** Aggregates the value of {@code odd} and {@code even}. */
    private int[] seriesDpIdx;

    public FactorizationEertree(int letterCapacity, int charCapacity) {
      super(letterCapacity, charCapacity, false);
    }

    public FactorizationEertree(int letterCapacity, int charCapacity, boolean compress) {
      super(letterCapacity, charCapacity, compress, false);
    }

    @Override
    public void createSubclass(int letterCapacity, int nodeCapacity) {
      diff = new int[nodeCapacity];
      seriesLink = new int[nodeCapacity];
      dp = new int[nodeCapacity];
      dpFrom = new int[nodeCapacity];
      seriesDpIdx = new int[nodeCapacity];
    }

    public void calc(int n, int[] values) {
      init(26);
      dp[0] = 0;
      for (int i = 1; i <= n; ++i) {
        if (append(values[i - 1])) {
          diff[nodeIdx] = length[nodeIdx] - length[link[nodeIdx]];
          seriesLink[nodeIdx] = diff[nodeIdx] == diff[link[nodeIdx]] ? seriesLink[link[nodeIdx]] : link[nodeIdx];
        }
        dp[i] = INF;
        dpFrom[i] = -1;
        for (int idx = last; length[idx] > 0; idx = seriesLink[idx]) {
          seriesDpIdx[idx] = i - length[seriesLink[idx]] - diff[idx];
          if (seriesLink[idx] != link[idx] && dp[seriesDpIdx[idx]] > dp[seriesDpIdx[link[idx]]]) {
            seriesDpIdx[idx] = seriesDpIdx[link[idx]];
          }
          if (IntUtils.isEven(i) && IntUtils.isEven(seriesDpIdx[idx]) && dp[i] > dp[seriesDpIdx[idx]]) {
            dp[i] = dp[seriesDpIdx[idx]];
            dpFrom[i] = seriesDpIdx[idx];
          }
        }
        dp[i] = dp[i] == INF ? INF : dp[i] + 1;
        if (i >= 2 && IntUtils.isEven(i) && values[i - 2] == values[i - 1] && dp[i] > dp[i - 2]) {
          dp[i] = dp[i - 2];
          dpFrom[i] = i - 2;
        }
      }
    }
  }
}
