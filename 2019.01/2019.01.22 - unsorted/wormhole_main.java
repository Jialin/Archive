package main;

import template.array.IntArrayUtils;
import template.collections.dancinglink.DancingLink;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: wormhole
*/
public class wormhole_main {
  int n, nHalf, n2;
  int res;
  int[] xs, ys;
  int[] visited;
  int[] jump;
  DancingLink dl;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    nHalf = n >> 1;
    n2 = n << 1;
    xs = new int[n];
    ys = new int[n];
    in.nextInts(n, xs, ys);
    IntArrayUtils.sort(ys, xs);
    jump = new int[n];
    res = 0;
    dl = new DancingLink(n);
    visited = new int[n << 1];
    dfs(0);
    out.println(res);
  }

  void dfs(int depth) {
    if (depth == nHalf) {
      if (isValid()) {
        ++res;
      }
      return;
    }
    int first = dl.first;
    dl.cover(first);
    for (int x = dl.first; x >= 0; x = dl.next[x]) {
      dl.cover(x);
      jump[first] = x;
      jump[x] = first;
      dfs(depth + 1);
      dl.uncover(x);
    }
    dl.uncover(first);
  }

  boolean isValid() {
    Arrays.fill(visited, -1);
    int loop = 0;
    for (int i = 0; i < n2; ++i, ++loop) {
      if (visited[i] >= 0) {
        continue;
      }
      int u = i;
      while (u >= 0 && visited[u] == -1) {
        visited[u] = loop;
        if (IntUtils.isEven(u)) {
          u = (jump[u >> 1] << 1) | 1;
        } else if ((u >> 1) < n - 1 && ys[u >> 1] == ys[(u >> 1) + 1]) {
          ++u;
        } else {
          u = -1;
        }
      }
      if (u >= 0 && visited[u] == loop) {
        return true;
      }
    }
    return false;
  }
}
