package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class Task2105 {
  int L, T, va, vb;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    L = in.nextInt();
    T = in.nextInt();
    va = in.nextInt();
    vb = in.nextInt();
    int S = (va + vb) * T;
    for (int i = in.nextInt(); i > 0; --i) {
      int type = in.nextInt();
      in.nextInt();
      int time = in.nextInt();
      if (type == 1) {
        S -= va * time;
      } else {
        S -= vb * time;
      }
    }
    out.println(S / L);
  }
}
