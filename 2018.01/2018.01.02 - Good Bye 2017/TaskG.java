package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.combination.BinomialModular;
import template.numbertheory.number.IntModular;

import java.util.Arrays;

public class TaskG {
  static int MAXL = 700 + 1;
  static IntModular MOD = new IntModular();
  static BinomialModular C = new BinomialModular(MAXL - 1, MOD);

  int n;
  char[] digits;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    digits = new char[MAXL];
    n = in.next(digits);
    initPow();
    int res = MOD.mul(calc(9), 9);
    for (int digit = 0; digit < 9; ++digit) {
      res = MOD.sub(res, calc(digit));
    }
    out.println(res);
  }

  int[] pow10, ones;

  void initPow() {
    pow10 = new int[n + 1];
    ones = new int[n + 1];
    pow10[0] = 1;
    for (int i = 1; i <= n; ++i) {
      pow10[i] = MOD.mul(pow10[i - 1], 10);
      ones[i] = MOD.add(MOD.mul(ones[i - 1], 10), 1);
    }
  }

  int[][][] way;

  int calc(int targetDigit) {
    if (way == null) {
      way = new int[2][2][n + 1];
    }
    int t = 0;
    clearCnt(t);
    way[t][0][0] = 1;
    for (int i = 0; i < n; ++i) {
      int digit = digits[i] - '0';
      clearCnt(t ^ 1);
      // not free
      for (int cnt = 0; cnt <= n; ++cnt) if (way[t][0][cnt] > 0) {
        // not free
        int newCnt = targetDigit < digit ? cnt + 1 : cnt;
        way[t ^ 1][0][newCnt] = MOD.add(
            way[t ^ 1][0][newCnt],
            way[t][0][cnt]);
        // free
        way[t ^ 1][1][cnt] = MOD.add(
            way[t ^ 1][1][cnt],
            MOD.mul(
                Math.min(targetDigit + 1, digit),
                way[t][0][cnt]));
        way[t ^ 1][1][cnt + 1] = MOD.add(
            way[t ^ 1][1][cnt + 1],
            MOD.mul(
                Math.max(digit - targetDigit - 1, 0),
                way[t][0][cnt]));
      }
      // free
      for (int cnt = 0; cnt <= n; ++cnt) if (way[t][1][cnt] > 0) {
        way[t ^ 1][1][cnt] = MOD.add(
            way[t ^ 1][1][cnt],
            MOD.mul(
                targetDigit + 1,
                way[t][1][cnt]));
        way[t ^ 1][1][cnt + 1] = MOD.add(
            way[t ^ 1][1][cnt + 1],
            MOD.mul(
                9 - targetDigit,
                way[t][1][cnt]));
      }
      t ^= 1;
    }
    int res = 0;
    for (int cnt = 0; cnt <= n; ++cnt) {
      res = MOD.add(
          res,
          MOD.mul(MOD.mul(
              ones[n - cnt],
              pow10[cnt]),
              MOD.add(way[t][0][cnt], way[t][1][cnt])));
    }
    return res;
  }

  void clearCnt(int t) {
    for (int free = 0; free < 2; ++free) {
      Arrays.fill(way[t][free], 0);
    }
  }
}
