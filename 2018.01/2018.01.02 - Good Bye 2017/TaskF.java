package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskF {
  int n;
  int[] x;
  char[] color;
  int firstG, lastG;
  int firstB, lastB;
  int firstR, lastR;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    x = new int[n];
    color = new char[n];
    firstG = lastG = -1;
    firstB = lastB = -1;
    firstR = lastR = -1;
    for (int i = 0; i < n; ++i) {
      x[i] = in.nextInt();
      color[i] = (char) in.nextNonSpaceChar();
      switch (color[i]) {
        case 'G':
          if (firstG < 0) firstG = i;
          lastG = i;
          break;
        case 'B':
          if (firstB < 0) firstB = i;
          lastB = i;
          break;
        case 'R':
          if (firstR < 0) firstR = i;
          lastR = i;
          break;
      }
    }
    out.println(calc());
  }

  long calc() {
    if (firstG < 0) return calcNoGreen();
    long res = calcBegin() + calcEnd();
    for (int fromIdx = firstG; fromIdx < lastG; ) {
      int toIdx;
      for (toIdx = fromIdx + 1; color[toIdx] != 'G'; ++toIdx) {}
      res += calcMiddle(fromIdx, toIdx);
      fromIdx = toIdx;
    }
    return res;
  }

  long calcNoGreen() {
    long res = 0;
    if (firstR >= 0) res += x[lastR] - x[firstR];
    if (firstB >= 0) res += x[lastB] - x[firstB];
    return res;
  }

  long calcBegin() {
    long res = 0;
    if (firstR >= 0) res += Math.max(x[firstG] - x[firstR], 0);
    if (firstB >= 0) res += Math.max(x[firstG] - x[firstB], 0);
    return res;
  }

  long calcEnd() {
    long res = 0;
    if (lastR >= 0) res += Math.max(x[lastR] - x[lastG], 0);
    if (lastB >= 0) res += Math.max(x[lastB] - x[lastG], 0);
    return res;
  }

  long calcMiddle(int fromIdx, int toIdx) {
    int maxB, maxR;
    maxB = maxR = Integer.MIN_VALUE;
    int xB, xR;
    xB = xR = x[fromIdx];
    for (int i = fromIdx + 1; i < toIdx; ++i) {
      if (color[i] == 'R') {
        maxR = Math.max(maxR, x[i] - xR);
        xR = x[i];
      } else {
        maxB = Math.max(maxB, x[i] - xB);
        xB = x[i];
      }
    }
    maxR = Math.max(maxR, x[toIdx] - xR);
    maxB = Math.max(maxB, x[toIdx] - xB);
    long length = x[toIdx] - x[fromIdx];
    return Math.min(length << 1, length * 3 - maxR - maxB);
  }
}
