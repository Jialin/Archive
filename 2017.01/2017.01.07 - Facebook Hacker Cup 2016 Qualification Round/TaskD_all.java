package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskD_all {
  static int MAXN = 300 + 1;
  static int INF = 1000000000;

  int n, m;
  String[] s = new String[MAXN];
  int[] common = new int[MAXN];
  int[][] dp = new int[MAXN][MAXN];

  public void solve(int unusedTaskIdx, QuickScanner in, QuickWriter out) {
    int taskCnt = in.nextInt();
    for (int taskIdx = 1; taskIdx <= taskCnt; ++taskIdx) {
      System.err.printf("Handling #%d\n", taskIdx);
      n = in.nextInt();
      m = in.nextInt();
      in.next(n, s);
      Arrays.sort(s, 0, n);
      for (int i = 1; i < n; ++i) {
        int cnt;
        for (cnt = 0; cnt < s[i - 1].length() && cnt < s[i].length() && s[i - 1].charAt(cnt) == s[i].charAt(cnt); ++cnt) {}
        common[i] = cnt;
      }
      for (int i = 0; i < n; ++i) {
        Arrays.fill(dp[i], 0, m + 1, INF);
        dp[i][1] = s[i].length();
        int allCommon = INF;
        for (int j = i - 1; j >= 0; --j) {
          allCommon = Math.min(allCommon, common[j + 1]);
          for (int k = 1; k < m; ++k) if (dp[j][k] != INF) {
            dp[i][k + 1] = Math.min(dp[i][k + 1], dp[j][k] + s[i].length() + s[j].length() - (allCommon << 1));
          }
        }
      }
      int res = Integer.MAX_VALUE;
      for (int i = m - 1; i < n; ++i) {
        res = Math.min(res, dp[i][m] + s[i].length());
      }
      out.printf("Case #%d: %d\n", taskIdx, res + m);
    }
  }
}
