package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.min;

public class TaskE {
  int n;
  long k;
  int[] toIdx;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextLong();
    toIdx = new int[n];
    for (int i = 0; i < n; ++i) {
      toIdx[i] = in.nextInt();
    }
    int[][] currentPosition = new int[2][n];
    long[][] currentSum = new long[2][n];
    int[][] currentMin = new int[2][n];
    int currentT = 0;
    int[][] nextPosition = new int[2][n];
    long[][] nextSum = new long[2][n];
    int[][] nextMin = new int[2][n];
    int nextT = 0;
    for (int i = 0; i < n; ++i) {
      currentPosition[currentT][i] = i;
      currentSum[currentT][i] = 0;
      currentMin[currentT][i] = Integer.MAX_VALUE;
      int w = in.nextInt();
      nextPosition[nextT][i] = toIdx[i];
      nextSum[nextT][i] = w;
      nextMin[nextT][i] = w;
    }
    for (long bit = 1; bit <= k; bit <<= 1) {
      if ((k & bit) > 0) {
        for (int i = 0; i < n; ++i) {
          currentPosition[currentT ^ 1][i] = nextPosition[nextT][currentPosition[currentT][i]];
          currentSum[currentT ^ 1][i] = currentSum[currentT][i] + nextSum[nextT][currentPosition[currentT][i]];
          currentMin[currentT ^ 1][i] = min(currentMin[currentT][i], nextMin[nextT][currentPosition[currentT][i]]);
        }
        currentT ^= 1;
      }
      if ((bit << 1) <= k) {
        for (int i = 0; i < n; ++i) {
          nextPosition[nextT ^ 1][i] = nextPosition[nextT][nextPosition[nextT][i]];
          nextSum[nextT ^ 1][i] = nextSum[nextT][i] + nextSum[nextT][nextPosition[nextT][i]];
          nextMin[nextT ^ 1][i] = min(nextMin[nextT][i], nextMin[nextT][nextPosition[nextT][i]]);
        }
        nextT ^= 1;
      }
    }
    for (int i = 0; i < n; ++i) {
      out.printf("%d %d\n", currentSum[currentT][i], currentMin[currentT][i]);
    }
  }
}
