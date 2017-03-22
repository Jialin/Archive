package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n, k;
  int[] courses;
  IntArrayList[] deps;

  boolean[] visited, inStack;
  IntArrayList res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    courses = in.nextInt(k);
    IntArrayUtils.update(courses, -1);
    deps = new IntArrayList[n];
    for (int i = 0; i < n; ++i) {
      int m = in.nextInt();
      IntArrayList dep = new IntArrayList(m);
      for (int j = 0; j < m; ++j) dep.add(in.nextInt() - 1);
      deps[i] = dep;
    }
    visited = new boolean[n];
    inStack = new boolean[n];
    res = new IntArrayList();
    if (calc()) {
      out.println(res.size);
      res.update(1);
      out.println(res);
    } else {
      out.println(-1);
    }
  }

  boolean calc() {
    for (int i = 0; i < k; ++i) if (!dfs(courses[i])) {
      return false;
    }
    return true;
  }

  boolean dfs(int u) {
    if (visited[u]) return true;
    visited[u] = inStack[u] = true;
    for (int v : deps[u]) {
      if (inStack[v]) return false;
      if (!dfs(v)) return false;
    }
    res.add(u);
    inStack[u] = false;
    return true;
  }
}
