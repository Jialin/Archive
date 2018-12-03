package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class UVA202 {
  final int MAXM = 3000;

  int[] idxs;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    idxs = new int[MAXM];
    int n;
    int m;
    boolean nl = false;
    while (true) {
      if (nl) {
        out.println();
      } else {
        nl = true;
      }
      n = in.nextIntOrDefault(-1);
      if (n < 0) {
        break;
      }
      m = in.nextInt();
      out.printf("%d/%d = %d.", n, m, n / m);
      n %= m;
      Arrays.fill(idxs, 0, m, -1);
      int startIdx;
      int length;
      int rem = n;
      for (int idx = 0; ; ++idx) {
        if (idxs[rem] == -1) {
          idxs[rem] = idx;
        } else {
          startIdx = idxs[rem];
          length = idx - idxs[rem];
          break;
        }
        rem = rem * 10 % m;
      }
      rem = n;
      for (int i = 0; i < startIdx; ++i) {
        rem *= 10;
        out.print(rem / m);
        rem %= m;
      }
      out.print('(');
      for (int i = 0; i < length && i < 50; ++i) {
        rem *= 10;
        out.print(rem / m);
        rem %= m;
      }
      if (length > 50) {
        out.print("...");
      }
      out.printf(")\n   %d = number of digits in repeating cycle\n", length);
    }
  }
}
