package main;

import template.graph.DirectedGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.BitSet;

public class TaskD {
  int n, m, q;
  DirectedGraph graph;
  int[] operation;
  int[] argument0, argument1;
  int[] shelfCnt;
  BitSet[] shelf;
  int[] answer;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    q = in.nextInt();
    operation = new int[q + 1];
    argument0 = new int[q + 1];
    argument1 = new int[q + 1];
    graph = new DirectedGraph(q + 1, q);
    graph.init(q + 1);
    for (int i = 1; i <= q; ++i) {
      operation[i] = in.nextInt();
      argument0[i] = in.nextInt();
      if (operation[i] <= 2) {
        argument1[i] = in.nextInt();
      }
      if (operation[i] == 4) {
        graph.add(argument0[i], i);
      } else {
        graph.add(i - 1, i);
        --argument0[i];
        --argument1[i];
      }
    }
    shelf = new BitSet[n];
    shelfCnt = new int[n];
    for (int i = 0; i < n; ++i) {
      shelf[i] = new BitSet(m);
    }
    answer = new int[q + 1];
    dfs(0, 0);
    for (int i = 1; i <= q; ++i) {
      out.println(answer[i]);
    }
  }

  void dfs(int u, int cnt) {
    answer[u] = cnt;
    for (int edgeIdx = graph.lastOut[u]; edgeIdx >= 0; edgeIdx = graph.nextOut[edgeIdx]) {
      int v = graph.toIdx[edgeIdx];
      int row = argument0[v], column = argument1[v];
      switch (operation[v]) {
        case 1:
          if (shelf[row].get(column)) {
            dfs(v, cnt);
          } else {
            shelf[row].set(column);
            ++shelfCnt[row];
            dfs(v, cnt + 1);
            shelf[row].clear(column);
            --shelfCnt[row];
          }
          break;
        case 2:
          if (shelf[row].get(column)) {
            shelf[row].clear(column);
            --shelfCnt[row];
            dfs(v, cnt - 1);
            shelf[row].set(column);
            ++shelfCnt[row];
          } else {
            dfs(v, cnt);
          }
          break;
        case 3:
          shelf[row].flip(0, m);
          shelfCnt[row] = m - shelfCnt[row];
          dfs(v, cnt - m + (shelfCnt[row] << 1));
          shelf[row].flip(0, m);
          shelfCnt[row] = m - shelfCnt[row];
          break;
        default:
          dfs(v, cnt);
          break;
      }
    }
  }
}
