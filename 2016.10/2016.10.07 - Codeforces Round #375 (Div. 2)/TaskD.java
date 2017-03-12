package main;

import template.graph.BidirectionalGraph;
import template.graph.order.BfsOrder;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskD {
  int n, m, nm, k;
  char[][] board;

  int waterCnt;
  int[][] idx;
  int[] x, y;
  int[] bfsIdx;
  boolean[] visited;

  BidirectionalGraph graph;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    nm = n * m;
    k = in.nextInt();
    board = new char[n][m];
    for (int i = 0; i < n; ++i) {
      in.next(board[i]);
    }
    idx = new int[n][m];
    x = new int[nm];
    y = new int[nm];
    waterCnt = 0;
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      if (board[i][j] == '*') {
        idx[i][j] = -1;
        continue;
      }
      x[waterCnt] = i;
      y[waterCnt] = j;
      idx[i][j] = waterCnt++;
    }
    graph = new BidirectionalGraph(waterCnt, waterCnt << 1);
    graph.init(waterCnt);
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) if (idx[i][j] >= 0) {
      if (i > 0 && idx[i - 1][j] >= 0) {
        graph.add(idx[i][j], idx[i - 1][j]);
      }
      if (j > 0 && idx[i][j - 1] >= 0) {
        graph.add(idx[i][j], idx[i][j - 1]);
      }
    }
    bfsIdx = new int[waterCnt];
    visited = new boolean[waterCnt];
    List<IntIntPair> lakes = new ArrayList<>(waterCnt);
    for (int i = 0; i < n; ++i) {
      int x = idx[i][0];
      if (x >= 0 && !visited[x]) BfsOrder.calc(graph, x, bfsIdx, visited);
      x = idx[i][m - 1];
      if (x >= 0 && !visited[x]) BfsOrder.calc(graph, x, bfsIdx, visited);
    }
    for (int j = 0; j < m; ++j) {
      int x = idx[0][j];
      if (x >= 0 && !visited[x]) BfsOrder.calc(graph, x, bfsIdx, visited);
      x = idx[n - 1][j];
      if (x >= 0 && !visited[x]) BfsOrder.calc(graph, x, bfsIdx, visited);
    }
    for (int i = 0; i < waterCnt; ++i) if (!visited[i]) {
      lakes.add(new IntIntPair(BfsOrder.calc(graph, i, bfsIdx, visited), i));
    }
    Collections.sort(lakes);
    Arrays.fill(visited, false);
    int res = 0;
    for (int i = 0, rem = lakes.size(); rem > k; ++i, --rem) {
      int cnt = BfsOrder.calc(graph, lakes.get(i).second, bfsIdx, visited);
      res += cnt;
      for (int j = 0; j < cnt; ++j) {
        int xx = x[bfsIdx[j]], yy = y[bfsIdx[j]];
        board[xx][yy] = '*';
      }
    }
    out.println(res);
    for (int i = 0; i < n; ++i) {
      out.println(board[i]);
    }
  }

  class IntIntPair implements Comparable<IntIntPair> {

    public final int first;
    public final int second;

    public IntIntPair(int first, int second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public int compareTo(IntIntPair o) {
      int value = Integer.compare(first, o.first);
      if (value != 0) return value;
      return Integer.compare(second, o.second);
    }
  }
}
