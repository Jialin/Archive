package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class TaskF {
  static int DIGIT = 16;
  static int MAXC = 10;

  int[][] C = IntUtils.combination(MAXC);

  int[] cnt = new int[DIGIT];
  long[][] dp = new long[2][MAXC];
  char[] s = new char[MAXC];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int k = in.nextInt();
    int t = in.nextInt();
    calc(k, t);
    out.println(s);
  }

  int calc(int k, int t) {
    Arrays.fill(cnt, t);
    int len;
    for (len = 1; ; ++len) {
      long way = subCalc(len, false);
      if (k <= way) break;
      k -= way;
    }
    for (int i = 0; i < len; ++i) {
      for (int digit = i > 0 ? 0 : 1; digit < DIGIT; ++digit) if (cnt[digit] > 0) {
        s[i] = parse(digit);
        --cnt[digit];
        long way = subCalc(len - i - 1, true);
        if (k <= way) break;
        k -= way;
        ++cnt[digit];
      }
    }
    s[len] = 0;
    return len;
  }

  long subCalc(int n, boolean startZero) {
    int t = 0;
    Arrays.fill(dp[t], 0, n + 1, 0);
    dp[t][n] = 1;
    for (int digit = 0; digit < DIGIT; ++digit) {
      Arrays.fill(dp[t ^ 1], 0, n + 1, 0);
      for (int remLen = 0; remLen <= n; ++remLen) if (dp[t][remLen] > 0) {
        for (int len = Math.min(cnt[digit], remLen); len >= 0; --len) {
          if (digit > 0 || startZero) {
            dp[t ^ 1][remLen - len] += dp[t][remLen] * C[remLen][len];
          } else if (remLen > len) {
            dp[t ^ 1][remLen - len] += dp[t][remLen] * C[remLen - 1][len];
          }
        }
      }
      t ^= 1;
    }
    return dp[t][0];
  }

  char parse(int digit) {
    return (char) (digit < 10 ? '0' + digit : 'a' + digit - 10);
  }
}
