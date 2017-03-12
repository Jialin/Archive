package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.abs;

public class TaskC {
  int n, m;
  int[] a, b;

  int resCnt;
  int[] res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    m = in.nextInt();
    b = in.nextInt(m);
    if (found()) {
      out.println("YES");
      for (int i = 0; i < n - m; ++i) {
        out.printf("%d %c\n", abs(res[i]), res[i] < 0 ? 'L' : 'R');
      }
    } else {
      out.println("NO");
    }
  }

  boolean found() {
    res = new int[n - m];
    resCnt = 0;
    int toIdx = n;
    for (int i = m - 1; i >= 0; --i) {
      int fromIdx;
      for (fromIdx = toIdx; fromIdx > 0 && b[i] > 0; ) {
        b[i] -= a[--fromIdx];
      }
      if (b[i] != 0) return false;
      if (!calc(fromIdx, toIdx)) return false;
      toIdx = fromIdx;
    }
    return toIdx == 0;
  }

  boolean calc(int fromIdx, int toIdx) {
    if (fromIdx + 1 == toIdx) return true;
    int minW = IntArrayUtils.min(a, fromIdx, toIdx);
    int maxW = IntArrayUtils.max(a, fromIdx, toIdx);
    if (minW == maxW) return false;
    int idx = fromIdx;
    for (int i = fromIdx; i < toIdx; ++i) if (a[i] == maxW) {
      if ((i == fromIdx || a[i - 1] == maxW)
          && (i + 1 == toIdx || a[i + 1] == maxW)) continue;
      idx = i;
      break;
    }
    int toLeft = idx - fromIdx;
    int toRight = toIdx - idx - 1;
    int floatIdx = idx;
    if (idx != fromIdx && a[idx - 1] != maxW) {
      res[resCnt++] = -(floatIdx + 1);
      --floatIdx;
      --toLeft;
    }
    if (idx + 1 != toIdx && a[idx + 1] != maxW) {
      res[resCnt++] = floatIdx + 1;
      --toRight;
    }
    for (int i = 0; i < toRight; ++i) {
      res[resCnt++] = floatIdx + 1;
    }
    for (int i = 0; i < toLeft; ++i) {
      res[resCnt++] = -(floatIdx + 1);
      --floatIdx;
    }
    return true;
  }
}
