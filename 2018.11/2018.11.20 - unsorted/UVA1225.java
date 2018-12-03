package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class UVA1225 {

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int[] cnt = new int[10];
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      Arrays.fill(cnt, 0);
      for (int n = in.nextInt(); n > 0; --n) {
        for (int m = n; m > 0; m /= 10) {
          ++cnt[m % 10];
        }
      }
      out.print(cnt[0]);
      for (int digit = 1; digit < 10; ++digit) {
        out.printf(" %d", cnt[digit]);
      }
      out.println();
    }
  }
}
