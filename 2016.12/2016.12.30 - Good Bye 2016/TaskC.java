package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  static int INF = 1000000000;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] c = new int[n];
    int[] d = new int[n];
    for (int i = 0; i < n; ++i) {
      c[i] = in.nextInt();
      d[i] = in.nextInt();
    }
    Range range = new Range();
    range.div(d[0]);
    for (int i = 1; i < n; ++i) {
      range.update(c[i - 1]);
      range.div(d[i]);
    }
    range.update(c[n - 1]);
    out.println(range.result());
  }

  class Range {
    int lower, upper;

    public Range() {
      lower = -INF;
      upper = INF;
    }

    void div(int div) {
      if (div == 1) {
        merge(1900, INF);
      } else {
        merge(-INF, 1899);
      }
    }

    void update(int delta) {
      if (lower != -INF) {
        lower = Math.max(lower + delta, -INF);
      }
      if (upper != INF) {
        upper = Math.min(upper + delta, INF);
      }
    }

    String result() {
      if (lower > upper) return "Impossible";
      return upper == INF ? "Infinity" : "" + upper;
    }

    void merge(int lower, int upper) {
      this.lower = Math.max(this.lower, lower);
      this.upper = Math.min(this.upper, upper);
    }
  }
}
