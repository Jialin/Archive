package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;
import java.util.Random;

public class TaskC {
  static Random RANDOM = new Random(1000000007);
  static int MAXN = 10000;

  int t;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    t = 0;
    preprocess();
    while (true) {
      int res = calcGuess();
      out.printf("%04d\n", res);
      out.flush();
      int a = in.nextInt();
      int b = in.nextInt();
      if (a == 4) break;
      guess[t ^ 1].clear();
      for (int i = 0; i < guess[t].size; ++i) {
        int nextRes = guess[t].get(i);
        int nextA = Long.bitCount(aMask[res] & aMask[nextRes]);
        if (nextA != a) continue;
        int nextB = Integer.bitCount(bMask[res] & bMask[nextRes]) - nextA;
        if (nextB == b) {
          guess[t ^ 1].add(nextRes);
        }
      }
      t ^= 1;
    }
  }

  long[] aMask;
  int[] bMask;
  IntArrayList[] guess;

  void preprocess() {
    aMask = new long[MAXN];
    bMask = new int[MAXN];
    guess = new IntArrayList[]{new IntArrayList(MAXN), new IntArrayList(MAXN)};
    for (int a = 0, i = 0; a < 10; ++a) for (int b = 0; b < 10; ++b) for (int c = 0; c < 10; ++c) for (int d = 0; d < 10; ++d) {
      aMask[i] = (1L << (a + 30)) | (1L << (b + 20)) | (1L << (c + 10)) | (1L << d);
      bMask[i] = (1 << a) | (1 << b) | (1 << c) | (1 << d);
      ++i;
    }
    for (int i = 0; i < MAXN; ++i) if (Integer.bitCount(bMask[i]) == 4) {
      guess[t].add(i);
    }
  }

  int[][] cnt;

  int calcGuess() {
    cnt = new int[5][5];
    int res = -1, resCnt = MAXN;
    for (int i = (guess[t].size > 1000 ? guess[t].size / 4 : guess[t].size) - 1, startIdx = 0; i >= 0; --i, ++startIdx) {
      int idx = RANDOM.nextInt(guess[t].size - startIdx) + startIdx;
      guess[t].swap(idx, startIdx);
      int x = guess[t].get(startIdx);
      for (int a = 0; a < cnt.length; ++a) Arrays.fill(cnt[a], 0);
      for (int j = 0; j < guess[t].size; ++j) if (i != j) {
        int y = guess[t].get(j);
        int a = Long.bitCount(aMask[x] & aMask[y]);
        int b = Integer.bitCount(bMask[x] & bMask[y]) - a;
        ++cnt[a][b];
      }
      int tmpCnt = 0;
      for (int a = 0; a < 5; ++a) for (int b = 0; b < 5; ++b) {
        tmpCnt = Math.max(tmpCnt, cnt[a][b]);
      }
      if (resCnt > tmpCnt) {
        res = x;
        resCnt = tmpCnt;
      }
    }
    if (res < 0) throw new IllegalArgumentException();
    return res;
  }
}
