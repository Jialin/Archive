package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

import java.util.Arrays;

public class TaskC {
  static final IntModular MOD = new IntModular();
  static final int INF = Integer.MAX_VALUE >> 1;

  int n;
  char[] s;
  int[] a;

  int longest;
  int[] way, cost;


  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = new char[n];
    in.next(s);
    a = in.nextInt(26);
    calc();
    out.println(way[n]);
    out.println(longest);
    out.println(cost[n]);
  }

  void calc() {
    longest = 0;
    way = new int[n + 1];
    cost = new int[n + 1];
    Arrays.fill(cost, INF);
    way[0] = 1;
    cost[0] = 0;
    for (int i = 0; i < n; ++i) {
      int length = INF;
      for (int j = i; j < n; ++j) {
        length = Math.min(length, a[s[j] - 'a']);
        if (j >= i + length) break;
        longest = Math.max(longest, j - i + 1);
        way[j + 1] = MOD.add(way[j + 1], way[i]);
        cost[j + 1] = Math.min(cost[j + 1], cost[i] + 1);
      }
    }
  }
}
