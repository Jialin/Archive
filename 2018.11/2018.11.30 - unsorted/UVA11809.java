package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA11809 {

  final double LOG2_10 = 1 / Math.log10(2);
  final double LOG2 = Math.log(2);
  final double EPS = 1E-8;
  final int M = 10;
  final int E = 31;

  double[] POW;
  double[][] VALUES;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    POW = new double[E];
    POW[0] = 1;
    for (int i = 1; i < E; ++i) {
      POW[i] = POW[i - 1] * POW[i - 1] * 2;
    }
    VALUES = new double[M][E];
    VALUES[0][0] = 0.5;
    for (int i = 1; i < M; ++i) {
      VALUES[i][0] = VALUES[i - 1][0] / 2 + 0.5;
    }
    for (int i = 0; i < M; ++i) {
      for (int j = 1; j < E; ++j) {
        VALUES[i][j] = VALUES[i][0] * POW[j];
      }
    }
    while (true) {
      String s = in.next();
      if (s.length() == 3) {
        break;
      }
      // X * 10 ^ Y = A * 2 ^ B
      // log2(X) + Y * log2(10) = log2(A) + B
      // log2(A) = log2(X) + Y * log2(10) - B
      double target = Math.log(Double.valueOf(s.substring(0, 17))) / LOG2 + Double.valueOf(s.substring(18)) * LOG2_10;
      int e;
      for (e = 1; target >= Math.pow(2, e) - 1; ++e) {}
      double fraction = 1 - Math.pow(2, target - Math.pow(2, e) + 1);
      int m = computeM(fraction);
      out.println(m, e);
    }
  }

  int computeM(double fraction) {
    double diff = Math.abs(0.5 - fraction);
    int res = 0;
    double v = 0.25;
    for (int i = 1; i <= 9; ++i, v /= 2) {
      double subDiff = Math.abs(v - fraction);
      if (diff > subDiff) {
        diff = subDiff;
        res = i;
      }
    }
    return res;
  }
}
