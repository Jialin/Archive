package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.string.LeastCircularShift;

public class UVA1584 {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    LeastCircularShift lcs = new LeastCircularShift(100);
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      String s = in.next();
      int idx = lcs.calc(s);
      out.println((s + s).substring(idx, idx + s.length()));
    }
  }
}
