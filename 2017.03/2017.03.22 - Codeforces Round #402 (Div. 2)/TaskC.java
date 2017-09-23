package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC {
  int n, m;
  Item[] items;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    int[] a = in.nextInt(n);
    int[] b = in.nextInt(n);
    items = new Item[n];
    for (int i = 0; i < n; ++i) {
      items[i] = new Item(a[i], b[i]);
    }
    Arrays.sort(items);
    int res = 0;
    for (int i = 0; i < m; ++i) res += items[i].a;
    for (int i = m; i < n; ++i) res += Math.min(items[i].a, items[i].b);
    out.println(res);
  }

  class Item implements Comparable<Item> {
    final int a, b, save;

    public Item(int a, int b) {
      this.a = a;
      this.b = b;
      this.save = a - b;
    }

    @Override
    public int compareTo(Item o) {
      return save - o.save;
    }
  }
}
