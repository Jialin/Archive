package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskE {
  static int MAXT = 2000 + 1;

  int n;
  Item[] items;

  int[][] maxPrice;
  boolean[][] used;
  IntArrayList solution;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    items = new Item[n];
    for (int i = 0; i < n; ++i) {
      items[i] = new Item(in.nextInt(), in.nextInt(), in.nextInt(), i + 1);
    }
    Arrays.sort(items);
    int res = calc();
    out.println(res);
    out.println(calcSolution(IntArrayUtils.find(maxPrice[n], res)));
    out.println(solution);
  }

  int calc() {
    maxPrice = new int[n + 1][MAXT];
    used = new boolean[n + 1][MAXT];
    Arrays.fill(maxPrice[0], Integer.MIN_VALUE);
    maxPrice[0][0] = 0;
    for (int i = 0; i < n; ++i) {
      for (int t = 0; t < MAXT; ++t) {
        maxPrice[i + 1][t] = maxPrice[i][t];
        used[i + 1][t] = false;
      }
      Item item = items[i];
      for (int t = item.burnT - 1 - item.saveT, st = item.burnT - 1; t >= 0; --t, --st) {
        if (maxPrice[i][t] < 0 || maxPrice[i + 1][st] >= maxPrice[i][t] + item.price) continue;
        maxPrice[i + 1][st] = maxPrice[i][t] + item.price;
        used[i + 1][st] = true;
      }
    }
    return IntArrayUtils.max(maxPrice[n]);
  }

  int calcSolution(int t) {
    solution = new IntArrayList(n);
    for (int i = n; i > 0; --i) if (used[i][t]) {
      Item item = items[i - 1];
      solution.add(item.idx);
      t -= item.saveT;
    }
    solution.reverse();
    return solution.size;
  }

  static class Item implements Comparable<Item> {
    final int saveT;
    final int burnT;
    final int price;
    final int idx;

    Item(int saveT, int burnT, int price, int idx) {
      this.saveT = saveT;
      this.burnT = burnT;
      this.price = price;
      this.idx = idx;
    }

    @Override
    public int compareTo(Item o) {
      return burnT - o.burnT;
    }
  }
}
