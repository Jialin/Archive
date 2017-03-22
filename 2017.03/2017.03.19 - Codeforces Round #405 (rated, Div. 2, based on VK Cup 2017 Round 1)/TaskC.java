package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n, m;
  int[] nameIdx;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    nameIdx = new int[n];
    for (int i = 0; i + 1 < m; ++i) {
      nameIdx[i] = i;
    }
    for (int i = m - 1; i < n; ++i) {
      if (in.next().charAt(0) == 'N') {
        nameIdx[i] = nameIdx[i - m + 1];
      } else {
        nameIdx[i] = getName(i - m + 1, i);
      }
    }
    out.print(createName(nameIdx[0]));
    for (int i = 1; i < n; ++i) {
      out.print(' ');
      out.print(createName(nameIdx[i]));
    }
    out.println();
  }

  int getName(int fromIdx, int toIdx) {
    for (int i = 0; i <= n; ++i) {
      boolean valid = true;
      for (int j = fromIdx; j < toIdx; ++j) if (nameIdx[j] == i) {
        valid = false;
        break;
      }
      if (valid) return i;
    }
    throw new IllegalArgumentException();
  }

  String createName(int idx) {
    return new StringBuilder()
        .appendCodePoint('A' + idx / 26)
        .appendCodePoint('a' + idx % 26)
        .toString();
  }
}
