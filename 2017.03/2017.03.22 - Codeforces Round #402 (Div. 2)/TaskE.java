package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Map;
import java.util.TreeMap;

public class TaskE {
  static char[] token = new char[1024];

  int n, m;
  Expression[] expressions;
  Map<String, Integer> nameMap;
  int[][] cnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    nameMap = new TreeMap<>();
    nameMap.put("?", -1);
    n = in.nextInt();
    m = in.nextInt();
    expressions = new Expression[n];
    for (int i = 0; i < n; ++i) {
      String name = in.next();
      in.next(token);
      expressions[i] = new Expression(in);
      nameMap.put(name, i);
    }
    cnt = new int[2][m];
    values = new boolean[n];
    for (int value = 0; value < 2; ++value) for (int bit = 0; bit < m; ++bit) {
      cnt[value][bit] = calc(value > 0, bit);
    }
    for (int i = m - 1; i >= 0; --i) out.print(cnt[0][i] <= cnt[1][i] ? 0 : 1);
    out.println();
    for (int i = m - 1; i >= 0; --i) out.print(cnt[0][i] >= cnt[1][i] ? 0 : 1);
    out.println();
  }

  boolean[] values;

  int calc(boolean value, int bit) {
    int res = 0;
    for (int i = 0; i < n; ++i) {
      values[i] = expressions[i].getValue(bit, value);
      if (values[i]) ++res;
    }
    return res;
  }

  class Expression {
    final String value;
    final int leftIdx, rightIdx;
    final char op;

    Expression(QuickScanner in) {
      String s = in.next();
      if (s.charAt(0) == '0' || s.charAt(0) == '1') {
        value = s;
        leftIdx = rightIdx = -1;
        op = ' ';
      } else {
        value = null;
        leftIdx = nameMap.get(s);
        in.next(token);
        op = token[0];
        rightIdx = nameMap.get(in.next());
      }
    }

    boolean getValue(int bit, boolean peterValue) {
      if (value != null) return value.charAt(m - 1 - bit) == '1';
      boolean leftValue = leftIdx < 0 ? peterValue : values[leftIdx];
      boolean rightValue = rightIdx < 0 ? peterValue : values[rightIdx];
      switch (op) {
        case 'A':
          return leftValue & rightValue;
        case 'O':
          return leftValue | rightValue;
        case 'X':
          return leftValue ^ rightValue;
        default:
          throw new IllegalArgumentException();
      }
    }
  }
}
