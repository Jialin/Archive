package main;

import template.collections.list.LongArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskE {
  int n;
  int[] values;

  long sumA;
  LongArrayList sumB;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    int m = in.nextInt();
    int q = in.nextInt();
    values = new int[m];
    in.nextInt(n, values);
    sumA = calcFirstSum();
    in.nextInt(m, values);
    sumB = new LongArrayList(m - n + 1);
    sumB.add(calcFirstSum());
    for (int i = n, j = 0; i < m; ++i, ++j) {
      sumB.add(values[j] - sumB.peekLast() + (IntUtils.isOdd(n) ? values[i] : -values[i]));
    }
    sumB.sort();
    out.println(calc());
    for (int i = 0; i < q; ++i) {
      int l = in.nextInt() - 1;
      int r = in.nextInt() - 1;
      int x = in.nextInt();
      if (IntUtils.isOdd(r - l + 1)) {
        sumA += IntUtils.isEven(l) ? x : -x;
      }
      out.println(calc());
    }
  }

  long calc() {
    int idx = sumB.lowerBound(sumA);
    long res = Long.MAX_VALUE;
    if (idx < sumB.size) {
      res = Math.min(res, sumB.get(idx) - sumA);
    }
    if (idx > 0) {
      res = Math.min(res, sumA - sumB.get(idx - 1));
    }
    return res;
  }

  long calcFirstSum() {
    long res = 0;
    for (int i = 0; i < n; ++i) {
      if (IntUtils.isEven(i)) {
        res += values[i];
      } else {
        res -= values[i];
      }
    }
    return res;
  }
}

// ci = ai - bi + j. Then f(j) = |c1 - c2 + c3 - c4... cn |

// (a0-bj)-(a1-b(j+1))+(a2-b(j+2))
//
// a0-a1+a2-a3...
// -(bj-b(j+1)+b(j+2)...)
