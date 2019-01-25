package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA133 {
  final int MAXN = 20;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n, k, m;
    IntArrayList q = new IntArrayList(MAXN);
    while (true) {
      n = in.nextInt();
      k = in.nextInt();
      m = in.nextInt();
      if (n == 0 && k == 0 && m == 0) {
        break;
      }
      q.initRange(1, n + 1);
      for (int i = k - 1, j = -m; q.isNotEmpty(); i += k, j -= m) {
        i = fix(i, q.size);
        j = fix(j, q.size);
        if (i == j) {
          out.printf("%3d", q.get(i));
          q.remove(i);
        } else {
          out.printf("%3d%3d", q.get(i), q.get(j));
          if (i < j) {
            q.remove(j);
            q.remove(i);
            --j;
          } else {
            q.remove(i);
            q.remove(j);
            --i;
          }
        }
        --i;
        if (q.isNotEmpty()) {
          out.print(',');
        }
      }
      out.println();
    }
  }

  int fix(int x, int size) {
    if (x < 0) {
      x %= size;
      return x < 0 ? x + size : x;
    } else if (x >= size) {
      return x % size;
    } else {
      return x;
    }
  }
}
