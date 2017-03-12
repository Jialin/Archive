package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    Query[] queries = new Query[n];
    for (int i = 0; i < n; ++i) {
      int value = in.nextInt();
      queries[i]= new Query(value, i + 1);
    }
    Arrays.sort(queries);
    for (int i = 0, j = n - 1; i < j; ++i, --j) {
      out.printf("%d %d\n", queries[i].idx, queries[j].idx);
    }
  }

  class Query implements Comparable<Query> {
    int value, idx;

    public Query(int value, int idx) {
      this.value = value;
      this.idx = idx;
    }

    @Override
    public int compareTo(Query o) {
      return value - o.value;
    }
  }
}
