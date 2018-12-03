package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA1586 {
  final double[] WEIGHTS = new double[256];
  {
    WEIGHTS['C'] = 12.01;
    WEIGHTS['H'] = 1.008;
    WEIGHTS['O'] = 16;
    WEIGHTS['N'] = 14.01;
  }

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      double weight = 0;
      int cnt = 0;
      double res = 0;
      for (char c : in.next().toCharArray()) {
        if ('1' <= c && c <= '9') {
          cnt = cnt * 10 + c - '0';
        } else {
          res += weight * Math.max(cnt, 1);
          weight = WEIGHTS[c];
          cnt = 0;
        }
      }
      out.printf("%.3f\n", res + weight * Math.max(cnt, 1));
    }
  }
}
