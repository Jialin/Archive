package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;
import java.util.PriorityQueue;

import static java.lang.Math.min;

public class TaskC {
  private static final int MAX = 1000;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    char[] s = in.next().toCharArray();
    boolean[] appear = new boolean[MAX];
    int cCnt = 0;
    for (char c : s) {
      if (!appear[c]) {
        appear[c] = true;
        ++cCnt;
      }
    }
    int[] location = new int[MAX];
    Arrays.fill(location, Integer.MAX_VALUE);
    PriorityQueue<Location> heap = new PriorityQueue<>(MAX);
    Arrays.fill(appear, false);
    int res = n;
    for (int i = n - 1; i >= 0; i--) {
      char c = s[i];
      location[c] = i;
      heap.add(new Location(c, i));
      if (!appear[c]) {
        appear[c] = true;
        --cCnt;
      }
      if (cCnt > 0) continue;
      while (true) {
        Location l = heap.peek();
        if (location[l.c] == l.location) break;
        heap.poll();
      }
      res = min(res, heap.peek().location - i + 1);
    }
    out.println(res);
  }

  class Location implements Comparable<Location> {
    char c;
    int location;

    public Location(char c, int location) {
      this.c = c;
      this.location = location;
    }

    @Override
    public int compareTo(Location o) {
      return o.location - location;
    }
  }
}
