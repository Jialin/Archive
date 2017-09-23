package main;

public class CheeseSlicing {
  int n;
  int[][][] dp;

  public int totalArea(int A, int B, int C, int S) {
    n = Math.max(Math.max(A, B), C);
    dp = new int[n + 1][n + 1][n + 1];
    for (int a = 1; a <= n; ++a) for (int b = 1; b <= n; ++b) for (int c = 1; c <= n; ++c) {
      int res = calc(a, b, c, S);
      for (int split = 1; split < a; ++split) {
        res = Math.max(res, dp[split][b][c] + dp[a - split][b][c]);
      }
      for (int split = 1; split < b; ++split) {
        res = Math.max(res, dp[a][split][c] + dp[a][b - split][c]);
      }
      for (int split = 1; split < c; ++split) {
        res = Math.max(res, dp[a][b][split] + dp[a][b][c - split]);
      }
      dp[a][b][c] = res;
    }
    return dp[A][B][C];
  }

  int calc(int a, int b, int c, int S) {
    if (a < S || b < S || c < S) return 0;
    if (a <= b && a <= c) return b * c;
    if (b <= a && b <= c) return a * c;
    if (c <= a && c <= b) return a * b;
    throw new IllegalArgumentException();
  }
}
