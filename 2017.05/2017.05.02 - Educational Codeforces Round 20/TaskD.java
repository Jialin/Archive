package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int maxLines;
  int n;
  String line;
  int[] nxtLength;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    maxLines = in.nextInt();
    line = in.nextLine(true);
    n = line.length();
    nxtLength = new int[n];
    nxtLength[n - 1] = 1;
    for (int i = n - 2; i >= 0; --i) {
      char ch = line.charAt(i);
      if (ch == ' ' || ch == '-') {
        nxtLength[i] = 1;
      } else {
        nxtLength[i] = nxtLength[i + 1] + 1;
      }
    }
    int lower = 1, upper = n - 1, res = n;
    while (lower <= upper) {
      int medium = (lower + upper) >> 1;
      if (valid(medium)) {
        res = medium;
        upper = medium - 1;
      } else {
        lower = medium + 1;
      }
    }
    out.println(res);
  }

  boolean valid(int width) {
    int useLines = 0;
    for (int pos = 0; pos < n; ) {
      if (nxtLength[pos] > width) return false;
      int remWidth = width - nxtLength[pos];
      pos += nxtLength[pos];
      for ( ; pos < n && nxtLength[pos] <= remWidth; pos += nxtLength[pos]) {
        remWidth -= nxtLength[pos];
      }
      ++useLines;
    }
    return useLines <= maxLines;
  }
}
