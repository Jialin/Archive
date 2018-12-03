package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA1583 {
  final int MAXN = 100000;

  int[] answer;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    answer = new int[MAXN + 1];
    for (int n = 1; n <= MAXN; ++n) {
      int sum = n;
      for (int m = n; m > 0; m /= 10) {
        sum += m % 10;
      }
      if (sum <= MAXN && answer[sum] == 0) {
        answer[sum] = n;
      }
    }
    for (int taskIdx = in.nextInt(); taskIdx > 0; --taskIdx) {
      out.println(answer[in.nextInt()]);
    }
  }
}
