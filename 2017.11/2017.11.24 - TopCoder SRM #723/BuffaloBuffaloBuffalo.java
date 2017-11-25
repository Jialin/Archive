package main;

import template.numbertheory.number.IntModular;

import java.util.Map;
import java.util.TreeMap;

public class BuffaloBuffaloBuffalo {
  static IntModular MOD = new IntModular();

  int n;
  String pattern;
  Map<Integer, Integer> ways;

  public int count(String pattern) {
    this.pattern = pattern;
    n = pattern.length();
    if (n % 7 > 0) return 0;
    n /= 7;
    ways = new TreeMap<>();
    ways.put(calcMask(n, n, n << 1, n, n, n), 1);
    return calc(n, 0, 0, 0, 0, 0, 0, 0);
  }

  int calc(int n, int idx, int b, int u, int f, int a, int l, int o) {
    if (!isValid(n, b, u, f, a, l, o)) return 0;
    int mask = calcMask(b, u, f, a, l, o);
    if (ways.containsKey(mask)) return ways.get(mask);
    if (idx >= pattern.length()) return 0;
    int res = 0;
    switch (pattern.charAt(idx)) {
      case 'b':
        res = calc(n, idx + 1, b + 1, u, f, a, l, o);
        break;
      case 'u':
        res = calc(n, idx + 1, b, u + 1, f, a, l, o);
        break;
      case 'f':
        res = calc(n, idx + 1, b, u, f + 1, a, l, o);
        break;
      case 'a':
        res = calc(n, idx + 1, b, u, f, a + 1, l, o);
        break;
      case 'l':
        res = calc(n, idx + 1, b, u, f, a, l + 1, o);
        break;
      case 'o':
        res = calc(n, idx + 1, b, u, f, a, l, o + 1);
        break;
      case '?':
        res = MOD.add(MOD.add(MOD.add(MOD.add(MOD.add(
            calc(n, idx + 1, b + 1, u, f, a, l, o),
            calc(n, idx + 1, b, u + 1, f, a, l, o)),
            calc(n, idx + 1, b, u, f + 1, a, l, o)),
            calc(n, idx + 1, b, u, f, a + 1, l, o)),
            calc(n, idx + 1, b, u, f, a, l + 1, o)),
            calc(n, idx + 1, b, u, f, a, l, o + 1));
        break;
    }
    ways.put(mask, res);
    return res;
  }

  boolean isValid(int n, int b, int u, int f, int a, int l, int o) {
    return n >= b
        && b >= u
        && (u << 1) >= f
        && (f >> 1) >= a
        && a >= l
        && l >= o;
  }

  // B(4): 21
  // U(4): 17
  // F(5): 12
  // A(4): 8
  // L(4): 4
  // O(4): 0
  int calcMask(int b, int u, int f, int a, int l, int o) {
    return (b << 21)
        | (u << 17)
        | (f << 12)
        | (a << 8)
        | (l << 4)
        | o;
  }
}
