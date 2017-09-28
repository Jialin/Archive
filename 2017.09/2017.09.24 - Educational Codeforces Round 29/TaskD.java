package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n, q, m;
  int[] a;
  int[] op, l, r;
  int[] b;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    q = in.nextInt();
    m = in.nextInt();
    a = in.nextInt(n);
    op = new int[q];
    l = new int[q];
    r = new int[q];
    for (int i = 0; i < q; ++i) {
      op[i] = in.nextInt();
      l[i] = in.nextInt();
      r[i] = in.nextInt();
    }
    b = in.nextInt(m);
    for (int i = q - 1; i >= 0; --i) {
      for (int j = 0; j < m; ++j) if (l[i] <= b[j] && b[j] <= r[i]) {
        if (op[i] == 1) {
          b[j] = b[j] == l[i] ? r[i] : b[j] - 1;
        } else {
          b[j] = l[i] + r[i] - b[j];
        }
      }
    }
    for (int i = 0; i < m; ++i) {
      out.print(a[b[i] - 1]);
      out.print(i + 1 == m ? '\n' : ' ');
    }
  }
}
