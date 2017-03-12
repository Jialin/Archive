package main;

import template.array.IntArrayUtils;
import template.collections.binaryindexedtree.IntBinaryIndexedTree;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.operators.IntXorBinaryOperator;

import java.util.Arrays;

public class TaskD {

  int n;
  int[] a, disA, lastPosition;
  int[] partialXor;
  int[] answer;
  Query[] queries;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = new int[n];
    in.nextInt(n, a);
    disA = new int[n];
    System.arraycopy(a, 0, disA, 0, n);
    Arrays.sort(disA);
    int m = IntArrayUtils.unique(disA);
    partialXor = new int[n + 1];
    for (int i = 0; i < n; ++i) {
      partialXor[i + 1] = partialXor[i] ^ a[i];
      a[i] = Arrays.binarySearch(disA, 0, m, a[i]);
    }
    lastPosition = new int[m];
    Arrays.fill(lastPosition, -1);
    int queryCnt = in.nextInt();
    answer = new int[queryCnt];
    queries = new Query[queryCnt];
    for (int i = 0; i < queryCnt; ++i) {
      int l = in.nextInt() - 1;
      int r = in.nextInt();
      queries[i] = new Query(l, r, i);
    }
    Arrays.sort(queries);
    IntBinaryIndexedTree ibit = new IntBinaryIndexedTree(n + 1);
    ibit.init(n + 1, IntXorBinaryOperator.INSTANCE);
    int lastR = 0;
    for (Query query : queries) {
      for ( ; lastR < query.r; ++lastR) {
        int idx = a[lastR];
        if (lastPosition[idx] != -1) {
          ibit.update(lastPosition[idx] + 1, disA[idx]);
        }
        lastPosition[idx] = lastR;
        ibit.update(lastR + 1, disA[idx]);
      }
      int xor = partialXor[query.r] ^ partialXor[query.l];
      int distinctXor = ibit.calc(query.r) ^ ibit.calc(query.l);
      answer[query.idx] = xor ^ distinctXor;
    }
    out.print(answer, '\n');
  }

  class Query implements Comparable<Query> {

    int l, r, idx;

    public Query(int l, int r, int idx) {
      this.l = l;
      this.r = r;
      this.idx = idx;
    }

    @Override
    public int compareTo(Query o) {
      return r - o.r;
    }
  }
}
