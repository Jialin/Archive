package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskG {
  static int MAXBIT = 50 + 1;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    dp = new long[2][2][MAXBIT << 1];
    out.println(calc(in.nextLong()));
  }

  long calc(long s) {
    long res = 0;
    for (int leftLen = 0; leftLen < MAXBIT; ++leftLen) for (int rightLen = 0; rightLen < MAXBIT; ++rightLen) {
      long v = s / ((1L << (leftLen + 1)) + (1L << (rightLen + 1)) - 3);
      if (v < 1) continue;
      res += calc(s, leftLen, rightLen, v);
    }
    return res;
  }

  long calc(long s, int leftLen, int rightLen, long v) {
    if (leftLen == 0 && rightLen == 0) {
      return s == v ? 1 : 0;
    }
    long res = 0;
    for (int one = 0; one <= leftLen + rightLen; ++one) {
      if (leftLen == 0 || rightLen == 0) {
        long sum = s + v + one;
        if ((sum & 1) > 0) continue;
        sum = (sum >> 1) - (1L << (leftLen + rightLen)) * v;
        if (leftLen == 0) {
          sum -= 1L << (rightLen - 1);
          if (0 <= sum && sum < (1L << (rightLen - 1)) && Long.bitCount(sum) + 1 == one) {
            ++res;
          }
        } else {
          if (sum < (1L << (leftLen - 1)) && Long.bitCount(sum) == one) {
            ++res;
          }
        }
        continue;
      }
      if (one < 1) continue;
      long sum = s + v * 3 + one;
      if ((sum & 1) > 0) continue;
      sum = (sum >> 1) - ((1L << leftLen) + (1L << rightLen)) * v - (1L << (rightLen - 1));
      if (sum >= 0) {
        res += calcDP(sum, leftLen - 1, rightLen - 1, one - 1);
      }
    }
    return res;
  }

  long[][][] dp;

  long calcDP(long sum, int leftLen, int rightLen, int one) {
    int t = 0;
    clear(dp[t], one);
    dp[t][0][one] = 1;
    int len = Math.max(leftLen, rightLen);
    if ((sum >> len) > 1) return 0;
    for (int i = 0; i < len; ++i) {
      clear(dp[t ^ 1], one);
      for (int carry = 0; carry < 2; ++carry) for (int remOne = 0; remOne <= one; ++remOne) if (dp[t][carry][remOne] > 0) {
        int leftEnd = i < leftLen ? 1 : 0;
        int rightEnd = i < rightLen ? 1 : 0;
        for (int leftBit = 0; leftBit <= leftEnd; ++leftBit) for (int rightBit = 0; rightBit <= rightEnd; ++rightBit) {
          int nxtRemOne = remOne - leftBit - rightBit;
          if (nxtRemOne < 0) continue;
          int sumBit = leftBit + rightBit + carry;
          int curBit = sumBit & 1;
          if (curBit != ((sum >> i) & 1)) continue;
          int nxtBit = sumBit >> 1;
          dp[t ^ 1][nxtBit][nxtRemOne] += dp[t][carry][remOne];
        }
      }
      t ^= 1;
    }
    return dp[t][(int) (sum >> len)][0];
  }

  void clear(long[][] dp, int one) {
    for (int carry = 0; carry < 2; ++carry) for (int remOne = 0; remOne <= one; ++remOne) {
      dp[carry][remOne] = 0;
    }
  }
}
