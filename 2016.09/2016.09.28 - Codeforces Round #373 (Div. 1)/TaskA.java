package main;

import template.array.CharArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n, t;
  char[] s;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    t = in.nextInt();
    s = new char[n + 1];
    in.next(s, 1);
    s[0] = '0';
    int decimalPoint = CharArrayUtils.find(s, '.');
    update(decimalPoint);
    out.println(s, s[0] == '0' ? 1 : 0);
  }

  void update(int decimalPoint) {
    if (t == 0) return;
    int pos = -1;
    for (int i = decimalPoint + 1; i <= n; ++i) if (s[i] >= '5') {
      pos = i;
      break;
    }
    if (pos == -1) return;
    int lastPos = n - 1;
    for (int i = pos; t > 0 && i > decimalPoint; --i) if (s[i] >= '5') {
      round(i);
      lastPos = i - 1;
      --t;
    }
    if (s[lastPos] == '.') s[lastPos] = 0;
  }

  void round(int pos) {
    s[pos] = 0;
    for (int i = pos - 1; i >= 0; --i) {
      if (s[i] == '.') continue;
      ++s[i];
      if (s[i] <= '9') break;
      s[i] = '0';
    }
  }
}
